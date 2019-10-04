package br.edu.ifsul.loja2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";
    private ListView lvCarrinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);

        lvCarrinho = findViewById(R.id.lv_carrinho);

        lvCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CarrinhoActivity.this, "Clique curto.", Toast.LENGTH_SHORT).show();
            }
        });

        lvCarrinho.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(CarrinhoActivity.this, "Clique longo.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

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
                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuitem_cancelar_pedido:
                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show();
                break;
        };
        return true;
    }
    // Trata os eventos do menu

    // Excluir um item
    // Editar um item
    //
}
