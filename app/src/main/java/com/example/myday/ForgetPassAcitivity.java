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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassAcitivity extends AppCompatActivity {
    EditText forpassemail;
    Button forpassresetbut;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_acitivity);
        mAuth = FirebaseAuth.getInstance();
        forpassresetbut = findViewById(R.id.forpassresetbut);
        forpassresetbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpass();
            }
        });
    }
    public void resetpass(){
        forpassemail = findViewById(R.id.forpassmail);
        String email = forpassemail.getText().toString();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Reset mail sent. Check your inbox",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgetPassAcitivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}