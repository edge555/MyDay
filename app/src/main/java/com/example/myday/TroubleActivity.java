package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TroubleActivity extends AppCompatActivity {
    Button trforpass,trresend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble);
        trforpass = findViewById(R.id.troubleforpass);
        trforpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpass();
            }
        });

        trresend = findViewById(R.id.troubleresend);
        trresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendmail();
            }
        });
    }
    public void forgotpass(){
        Intent intent = new Intent(TroubleActivity.this,ForgetPassAcitivity.class);
        startActivity(intent);
    }
    public void resendmail(){
        Intent intent = new Intent(TroubleActivity.this,ResendMailActivity.class);
        startActivity(intent);
    }
}
