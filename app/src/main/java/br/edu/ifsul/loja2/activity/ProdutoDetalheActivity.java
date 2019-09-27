package br.edu.ifsul.loja2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.model.Produto;
import br.edu.ifsul.loja2.setup.AppSetup;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private TextView tvNome, tvValor, tvEstoque, tvDescricao;
    private ImageView imvFoto;
    private ProgressBar pbFoto;
    private EditText etQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);
        int position = getIntent().getExtras().getInt("position");
        Produto produto = AppSetup.listProdutos.get(position);

        tvNome = findViewById(R.id.tvNomeProduto);
        imvFoto = findViewById(R.id.imvFoto);
        pbFoto = findViewById(R.id.pb_foto_produto_detalhe);
        tvValor = findViewById(R.id.tvValorProduto);
        tvEstoque = findViewById(R.id.tvQuantidadeProduto);
        tvDescricao = findViewById(R.id.tvDerscricaoProduto);
        etQuantidade = findViewById(R.id.etQuantidade);

        findViewById(R.id.btComprarProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProdutoDetalheActivity.this, "Ok", Toast.LENGTH_SHORT).show();
            }
        });

        tvNome.setText(produto.getNome());
    }
}
