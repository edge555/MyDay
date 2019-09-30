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

public class ResendMailActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    EditText rsmail,rspass;
    private Button rsbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_mail);
        rsbt = findViewById(R.id.resendbut);
        rsbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rsmail = findViewById(R.id.resendemail);
                rspass = findViewById(R.id.resendpass);
                String u = rsmail.getText().toString(),p=rspass.getText().toString();
                rsbt = findViewById(R.id.resendbut);
                mAuth.signInWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(!mAuth.getCurrentUser().isEmailVerified()){
                                mAuth.getCurrentUser().sendEmailVerification();
                                Toast.makeText(getApplicationContext(),"Verification email resent.",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email already verified",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
