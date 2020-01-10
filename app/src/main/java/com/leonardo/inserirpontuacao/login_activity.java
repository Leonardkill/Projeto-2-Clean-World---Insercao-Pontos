package com.leonardo.inserirpontuacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class login_activity extends AppCompatActivity {
    EditText emailId, password;
    Button btnLogar;
    TextView tvRegistrar;
    FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mRootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_senha);
        btnLogar = findViewById(R.id.btn_entrar);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser != null) {
                    Toast.makeText(login_activity.this,"Você logou com sucesso!" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login_activity.this,MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(login_activity.this,"Falha no Login" , Toast.LENGTH_SHORT).show();
                }
            }
        };
            btnLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = emailId.getText().toString();
                    String pwd = password.getText().toString();
                    if(email.isEmpty()) {
                        emailId.setError("Por favor insira um e-mail");
                        emailId.requestFocus();
                    }

                    else if (pwd.isEmpty()){
                        password.setError("Digite uma senha");
                        password.requestFocus();
                    }

                    else if (email.isEmpty() && pwd.isEmpty()) {
                        Toast.makeText(login_activity.this , "Os campos estão vazio" , Toast.LENGTH_SHORT).show();
                    }

                    else if (!(email.isEmpty() && pwd.isEmpty())) {
                        mFirebaseAuth.signInWithEmailAndPassword(email , pwd).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(login_activity.this , "Erro de login , por favor tent novamente" , Toast.LENGTH_SHORT).show();
                                }
                                else  {
                                    Intent intToMain = new Intent(login_activity.this , MainActivity.class);
                                    startActivity(intToMain);
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(login_activity.this , "Ocorreu um erro!" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
