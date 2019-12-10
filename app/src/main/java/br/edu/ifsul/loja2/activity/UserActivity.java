package br.edu.ifsul.loja2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.model.User;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "userActivity";
    private EditText etNome, etSobrenome, etEmail, etSenha;
    private Spinner etFuncao;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //mapeia os componenAuthenticationtes da UI
        etNome = findViewById(R.id.etNome_signup);
        etSobrenome = findViewById(R.id.etSobrenome_signup);
        etFuncao = findViewById(R.id.etFuncao_signup);
        etEmail = findViewById(R.id.etEmail_signup);
        etSenha = findViewById(R.id.etSenha_signup);

        //trata evento onclick do button
        findViewById(R.id.btCadastrar_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                String sobrenome = etSobrenome.getText().toString();
                String funcao = etFuncao.getSelectedItem().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                if(!email.isEmpty() && !senha.isEmpty() && !nome.isEmpty() && !sobrenome.isEmpty() && !funcao.isEmpty()){
                    signUp(nome, sobrenome, funcao, email, senha);
                }else{
                    Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });
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

    private void signUp(final String nome, final String sobrenome, final String funcao, String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            User usuario = new User();

                            usuario.setNome(nome);
                            usuario.setSobrenome(sobrenome);
                            usuario.setFuncao(funcao);
                            usuario.setEmail(user.getEmail());

                            FirebaseDatabase.getInstance().getReference().child("vendas").child("users").child(mAuth.getCurrentUser().getUid()).setValue(usuario);
                            Toast.makeText(UserActivity.this, "Usuário cadastrado com sucesso.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserActivity.this, LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}