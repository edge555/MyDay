package com.example.myday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.TreeMap;

public class RegisterActivity extends AppCompatActivity {
    private Button rb;
    String n,fn,sn,u,p;
    EditText efn,esn,eu,ep;
    CheckBox regpasschk;
    FirebaseAuth mAuth;
    private DatabaseReference db;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hidepass(); // Password hiding
        spannable1();// Spannable text
        rb = findViewById(R.id.regbut);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                efn=findViewById(R.id.regfn);
                esn=findViewById(R.id.regsn);
                eu=findViewById(R.id.regemail);
                ep=findViewById(R.id.regpass);
                fn=efn.getText().toString();
                sn=esn.getText().toString();
                n=fn+" "+sn;
                u=eu.getText().toString();
                p=ep.getText().toString();
                Checker chk = new Checker();
                mAuth = FirebaseAuth.getInstance();
                String s="";
                if(!chk.name(n)) {
                    if(!chk.name(fn)){
                        efn.setError("Enter a valid name");
                        efn.requestFocus();
                    }
                    else if(!chk.name(sn)){
                        esn.setError("Enter a valid name");
                        esn.requestFocus();
                    }
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
                                FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                                String uid  = curuser.getUid();
                                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
                                HashMap<String,String>abcd=new HashMap<>();
                                abcd.put("Name",n);
                                abcd.put("Email",u);
                                db.setValue(abcd);
                                TreeMap<String,Info>taskk = new TreeMap<String,Info>();
                                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
                                db.setValue(taskk);
                                Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(),"Email is already in use",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    public void hidepass(){
        regpasschk = findViewById(R.id.regpasschk);
        regpasschk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ep = findViewById(R.id.regpass);
                p = ep.getText().toString();
                if(b){
                    ep.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    ep.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    public void spannable1(){
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
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce) {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to go back", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}