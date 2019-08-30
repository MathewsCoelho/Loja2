package br.edu.ifsul.loja2.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
//        AppSetup.user.getEmail();
    }
}
