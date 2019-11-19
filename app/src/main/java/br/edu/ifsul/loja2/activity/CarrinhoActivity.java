package br.edu.ifsul.loja2.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.adapter.CarrinhoAdapter;
import br.edu.ifsul.loja2.model.ItemPedido;
import br.edu.ifsul.loja2.model.Pedido;
import br.edu.ifsul.loja2.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";
    private ListView lvCarrinho;
    private double totalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvTotalPedidoCarrinho = findViewById(R.id.tvTotalPedidoCarrinho);

        lvCarrinho = findViewById(R.id.lv_carrinho);

        lvCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editarItem(i);
            }
        });

        lvCarrinho.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int i, long id) {
                removerItem(i);
                return false;
            }
        });

        atualizarView();

        tvTotalPedidoCarrinho.setText(String.valueOf(totalPedido));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // Inflar o menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_carrinho, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem_salvar_pedido:
                Confirmar();
                break;
            case R.id.menuitem_cancelar_pedido:
                Cancelar();
                break;
            case R.id.home:
                finish();
                break;
        };
        return true;
    }

    private void Cancelar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar");
        builder.setMessage("Confirmar");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                for(ItemPedido item : AppSetup.carrinho){
                    DatabaseReference dbRef = database.getReference("vendas/produtos")
                            .child(item.getProduto().getKey()).child("quantidade");
                    dbRef.setValue(item.getQuantidade() + item.getProduto().getQuantidade());
                    Log.d("Removido com sucesso!" , item.toString());
                }

                AppSetup.carrinho.clear();
                AppSetup.cliente = null;
                finish();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void Confirmar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar");
        builder.setMessage("Confirmar");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(AppSetup.carrinho.isEmpty()){
                    Toast.makeText(CarrinhoActivity.this, "Carrinho vazio", Toast.LENGTH_SHORT).show();
                } else {
                    Date timestamp = new Date();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = database.getReference("vendas/pedidos");

                    String key = dbRef.push().getKey();

                    Pedido pedido = new Pedido();

                    pedido.setCliente(AppSetup.cliente);
                    pedido.setDataCriacao(timestamp);
                    pedido.setDataModificacao(timestamp);
                    pedido.setEstado("Aberto");
                    pedido.setFormaDePagamento("Cartão");
                    pedido.setItens(AppSetup.carrinho);
                    pedido.setSituacao(true);
                    pedido.setTotalPedido(totalPedido);

                    dbRef.child(key).setValue(pedido);

                    AppSetup.cliente = null;
                    AppSetup.carrinho.clear();
                    AppSetup.pedido = null;
                    finish();
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void removerItem(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar");
        builder.setMessage("Deseja retirar este produto do carrinho?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                atualizarEstoque(position);
                atualizarView();
                Toast.makeText(CarrinhoActivity.this, "Produto removido com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void editarItem(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Você tem certeza?");
        builder.setMessage("Este produto será retirado do carrinho");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                atualizarEstoque(position);
                Intent intent = new Intent(CarrinhoActivity.this, ProdutoDetalheActivity.class);
                intent.putExtra("position", AppSetup.listProdutos.get(position).getIndex());
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void atualizarEstoque(int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/produtos")
                .child(AppSetup.carrinho.get(position).getProduto().getKey()).child("quantidade");

        myRef.setValue(AppSetup.carrinho.get(position).getQuantidade() +
                AppSetup.carrinho.get(position).getProduto().getQuantidade());

        Log.d("Removido", AppSetup.carrinho.get(position).toString());
        AppSetup.carrinho.remove(position);
        Log.d("item", "Item removido");

        atualizarView();

        if(AppSetup.carrinho.isEmpty()){
            finish();
        }

        Toast.makeText(CarrinhoActivity.this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void atualizarView(){
        lvCarrinho.setAdapter(new CarrinhoAdapter(CarrinhoActivity.this, AppSetup.carrinho));
        for(ItemPedido itemPedido: AppSetup.carrinho){
            totalPedido = totalPedido + itemPedido.getTotalItem();
        }
    }
}
