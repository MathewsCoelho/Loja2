package br.edu.ifsul.loja2.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.UsersAdapter;
import br.edu.ifsul.loja.model.User;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = "usersActivity";
    private ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        //mapeia o componente da view
        lvUsers = findViewById(R.id.lv_usuarios);

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/users");
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                List<User> listUsers = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "dataSnapshot=" + ds);
                    User user = ds.getValue(User.class);
                    user.setKey(ds.getKey());
                    user.setIndex(listUsers.size());
                    listUsers.add(user);
                }
                lvUsers.setAdapter(new UsersAdapter(UsersActivity.this, listUsers));

                //faz o bindView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}