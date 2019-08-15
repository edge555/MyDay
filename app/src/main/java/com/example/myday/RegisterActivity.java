package com.example.myday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
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
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterActivity extends AppCompatActivity {
    private Button rb;
    String n,u,p;
    EditText en,eu,ep;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Spannable text
        TextView textView = findViewById(R.id.logtext);
        String text = "Already a user?  Sign In";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1,17,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //
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
                mAuth = FirebaseAuth.getInstance();
                String s="";
                if(!chk.name(n)) {
                    en.setError("Enter a valid name");
                    en.requestFocus();
                }
                if(u.isEmpty()){
                    eu.setError("Enter an e-mail address");
                    eu.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(u).matches()) {
                    eu.setError("Enter a valid e-mail address");
                    eu.requestFocus();
                }
                else if(p.isEmpty()){
                    ep.setError("Enter a password");
                    ep.requestFocus();
                }
                else if(p.length()<6){
                    ep.setError("Password must be minumum of 6 characters");
                    ep.requestFocus();
                }
                else if(!chk.pass(p)){
                    ep.setError("Password should not contain any spaces");
                    ep.requestFocus();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
