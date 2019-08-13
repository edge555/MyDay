package com.example.myday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterActivity extends AppCompatActivity {
    private Button rb;
    String n,u,p;
    EditText en,eu,ep;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        rb = findViewById(R.id.regbut);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                en=findViewById(R.id.fullname);
                eu=findViewById(R.id.reguser);
                ep=findViewById(R.id.regpass);
                n=en.getText().toString();
                u=eu.getText().toString();
                p=ep.getText().toString();
                Checker chk = new Checker();
                String s="";
                if(!chk.name(n))
                    s+="Invalid name\n";
                if(!chk.pass(p))
                    s+="Invalid password\n";
                if(s.isEmpty()){
                    mFirebaseAuth.createUserWithEmailAndPassword(u,p).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(),s.substring(0,s.length()-1),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
