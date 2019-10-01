package br.edu.ifsul.loja2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);
    }
}
