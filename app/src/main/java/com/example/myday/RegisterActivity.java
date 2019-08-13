package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Button rb;
    String n,u,p;
    EditText en,eu,ep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rb = findViewById(R.id.regbut);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                en=findViewById(R.id.fullname);
                eu=findViewById(R.id.username);
                ep=findViewById(R.id.password);
                n=en.getText().toString();
                u=eu.getText().toString();
                p=ep.getText().toString();
                Checker chk = new Checker();
                String s="";
                if(!chk.name(n))
                    s+="Invalid name\n";
                if(!chk.username(u))
                    s+="Invalid username\n";
                if(!chk.pass(p))
                    s+="Invalid password\n";
                if(s.isEmpty()){
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{

                    Toast.makeText(getApplicationContext(), s.substring(0, s.length() - 1), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
