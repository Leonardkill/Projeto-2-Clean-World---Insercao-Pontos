package com.leonardo.inserirpontuacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText digitar_CPF , digitar_PONTOS;
    Button btn_INSERIR;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String  id = user.getUid();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Query cpf = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cpf");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        digitar_CPF = findViewById(R.id.digitar_cpf);
        digitar_PONTOS = findViewById(R.id.digitar_pontos);
        btn_INSERIR = findViewById(R.id.btn_inserir);


        btn_INSERIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ccpf =digitar_CPF.getText().toString();
                final int[] ppontos = {Integer.valueOf(digitar_PONTOS.getText().toString())};

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Users").child(id).child("cpf").exists()) {
                            ppontos[0] += Integer.valueOf(String.valueOf(dataSnapshot.child("Users").child(id).child("pontuacao").getValue()));
                            ref.child("Users").child(id).child("pontuacao").setValue(ppontos[0]);
                            Toast.makeText(getBaseContext(), "Pontos inseridos com sucesso", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getBaseContext(), "Erro na inseção de pontos", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



    }
}
