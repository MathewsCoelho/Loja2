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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Date;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.adapter.CarrinhoAdapter;
import br.edu.ifsul.loja2.model.ItemPedido;
import br.edu.ifsul.loja2.model.Pedido;
import br.edu.ifsul.loja2.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";
    private ListView lvCarrinho;
    private Double totalPedido = new Double(0.0);
    private TextView tvTotalPedidoCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        ((TextView) findViewById(R.id.tvClienteCarrinho)).setText(AppSetup.cliente.getNome() + " " + AppSetup.cliente.getSobrenome());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotalPedidoCarrinho = findViewById(R.id.tvTotalPedidoCarrinho);

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
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarView();
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
            case android.R.id.home:
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
                int size = AppSetup.carrinho.size();
                for (int i = 0; i < size; i++) {
                    atualizarEstoque(i);
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
                AppSetup.carrinho.remove(position);
                Log.d("item", "Item removido");
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
                AppSetup.carrinho.remove(position);
                Log.d("item", "Item removido");
                Intent intent = new Intent(CarrinhoActivity.this, ProdutoDetalheActivity.class);
                intent.putExtra("position", AppSetup.listProdutos.get(position).getIndex());
                startActivity(intent);
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

    public void atualizarEstoque(int position) {
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/produtos")
                .child(AppSetup.carrinho.get(position).getProduto().getKey()).child("quantidade");

        int quantidade = AppSetup.carrinho.get(position).getQuantidade() +
                AppSetup.carrinho.get(position).getProduto().getQuantidade();
        Log.d(TAG, "quantidade=" + quantidade);
        myRef.setValue(quantidade);

        Log.d("Removido", AppSetup.carrinho.get(position).toString());

        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);
        atualizarView();

        if(AppSetup.carrinho.isEmpty()){
            finish();
        }

        Toast.makeText(CarrinhoActivity.this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void atualizarView(){
        lvCarrinho.setAdapter(new CarrinhoAdapter(CarrinhoActivity.this, AppSetup.carrinho));
        totalPedido = 0d;
        for(ItemPedido i : AppSetup.carrinho){

            totalPedido += i.getTotalItem();

        }
        tvTotalPedidoCarrinho.setText(NumberFormat.getCurrencyInstance().format(totalPedido));
    }
}
