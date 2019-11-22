package br.edu.ifsul.loja2.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.model.Cliente;
import br.edu.ifsul.loja2.model.ItemPedido;
import br.edu.ifsul.loja2.model.Produto;
import br.edu.ifsul.loja2.setup.AppSetup;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private TextView tvNome, tvValor, tvEstoque, tvDescricao;
    private ImageView imvFoto;
    private ProgressBar pbFoto;
    private EditText etQuantidade;
    private Produto produto;
    private FirebaseDatabase database;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        position= getIntent().getExtras().getInt("position");

        tvNome = findViewById(R.id.tvNomeProduto);
        imvFoto = findViewById(R.id.imvFoto);
        pbFoto = findViewById(R.id.pb_foto_produto_detalhe);
        tvValor = findViewById(R.id.tvValorProduto);
        tvEstoque = findViewById(R.id.tvQuantidadeProduto);
        tvDescricao = findViewById(R.id.tvDerscricaoProduto);
        etQuantidade = findViewById(R.id.etQuantidade);

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("vendas/produtos/" + AppSetup.listProdutos.get(position).getKey() + "/quantidade");

        findViewById(R.id.btComprarProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSetup.cliente = new Cliente();
                if(AppSetup.cliente == null){
                    startActivity(new Intent(ProdutoDetalheActivity.this, ClientesActivity.class));
                } else{
                    if(!etQuantidade.getText().toString().isEmpty()){
                        if(Integer.parseInt(etQuantidade.getText().toString()) > AppSetup.listProdutos.get(position).getQuantidade() || Integer.parseInt(etQuantidade.getText().toString()) <= 0){
                            Snackbar.make(findViewById(R.id.container_activity_detalhe_produto),
                                    R.string.snack_qde_insuficiente,
                                    Snackbar.LENGTH_LONG).show();
                        }else{
                            ItemPedido item = new ItemPedido();
                            item.setProduto(AppSetup.listProdutos.get(position));
                            item.setQuantidade(Integer.parseInt(etQuantidade.getText().toString()));
                            item.setTotalItem(Integer.parseInt(etQuantidade.getText().toString()) * AppSetup.listProdutos.get(position).getValor());
                            item.setSituacao(true);
                            AppSetup.carrinho.add(item);
                            startActivity(new Intent(ProdutoDetalheActivity.this, CarrinhoActivity.class));
                            int qtd = Integer.parseInt(etQuantidade.getText().toString());
                            atualizarEstoque(qtd);
                            finish();
                        }
                    } else{
                        Snackbar.make(findViewById(R.id.container_activity_detalhe_produto),
                                R.string.snack_insira_quantidade,
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        //bind nos componentes da view
        tvNome.setText(AppSetup.listProdutos.get(position).getNome());
        tvValor.setText(NumberFormat.getCurrencyInstance().format(AppSetup.listProdutos.get(position).getValor()));
        tvEstoque.setText(AppSetup.listProdutos.get(position).getQuantidade().toString());
        tvDescricao.setText(AppSetup.listProdutos.get(position).getDescricao());
        //ativar quando tiver a navegabilidade definida
        //tvVendedor.setText(AppSetup.user.getEmail());
        if(AppSetup.listProdutos.get(position).getUrl_foto().equals("")){
            pbFoto.setVisibility(View.INVISIBLE);
        }else{
            //carrega a foto aqui
        }

        // escutando banco para atualizar estoque
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvEstoque.setText(dataSnapshot.getValue(Integer.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarEstoque(Integer qtd){
        database.getInstance().getReference("vendas/produtos").child(AppSetup.listProdutos.get(position).getKey())
                .child("quantidade").setValue(AppSetup.listProdutos.get(position).getQuantidade() - qtd);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
