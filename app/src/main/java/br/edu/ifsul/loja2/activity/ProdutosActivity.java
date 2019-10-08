package br.edu.ifsul.loja2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.adapter.ProdutosAdapter;
import br.edu.ifsul.loja2.model.Produto;
import br.edu.ifsul.loja2.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity {

    private static final String TAG = "produtosActivity";
    private ListView lvProdutos;
    private List<Produto> produtosTemp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //mapeia o componente da view
        lvProdutos = findViewById(R.id.lv_produtos);

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (produtosTemp.isEmpty()) {
                    Intent intent = new Intent(ProdutosActivity.this, ProdutoDetalheActivity.class);
                    intent.putExtra("position", i);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ProdutosActivity.this, ProdutoDetalheActivity.class);
                    intent.putExtra("position", produtosTemp.get(i).getIndex());
                    startActivity(intent);
                }
            }
        });

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/produtos");
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                AppSetup.listProdutos = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d(TAG, "dataSnapshot=" + ds);
                    Produto produto = ds.getValue(Produto.class);
                    produto.setKey(ds.getKey());
                    produto.setIndex(AppSetup.listProdutos.size());
                    AppSetup.listProdutos.add(produto);
                }

                //faz o bindView
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, AppSetup.listProdutos));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_produtos, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint(getString(R.string.text_nome));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                produtosTemp.clear();
                for(Produto p : AppSetup.listProdutos){
                    if(p.getNome().contains(newText)){
                        produtosTemp.add(p);
                    }
                }

                //faz o bindView
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtosTemp));

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_barcode:
                Toast.makeText(this, "código de barras", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

}
