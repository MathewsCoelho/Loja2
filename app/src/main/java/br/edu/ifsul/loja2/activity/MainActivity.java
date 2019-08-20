package br.edu.ifsul.loja2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsul.loja2.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "texto";
    private EditText etEmail, etSenha;
    private Button btLogar;
    private TextView tvEsqueceuSenha;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail_login);
        etSenha = findViewById(R.id.etSenha_login);
        btLogar = findViewById(R.id.btLogar_login);
        tvEsqueceuSenha = findViewById(R.id.tvesqueceuSenha_login);



        btLogar.setOnClickListener(new View.OnClickListener(){
            final String email = etEmail.getText().toString();
            final String senha = etSenha.getText().toString();
            @Override
            public void onClick(View v){
                if(!email.isEmpty() && !senha.isEmpty()){
                    signIn(email, senha);
                }else{
                    Toast.makeText(MainActivity.this, R.string.toast_preencher_todos_campos, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener(){
            final String email = etEmail.getText().toString();
            final String senha = etSenha.getText().toString();

            @Override
            public void onClick(View v){
                if(!email.isEmpty() && !senha.isEmpty()){
                    resetarSenha(email, senha);
                }else{
                    Toast.makeText(MainActivity.this, R.string.toast_preencher_todos_campos, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void resetarSenha(String email, String senha) {
    }

    private void signIn(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    ;
}

