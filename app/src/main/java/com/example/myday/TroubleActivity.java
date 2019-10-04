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
        trresend = findViewById(R.id.troublesend);
        trresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmail();
            }
        });
    }
    public void forgotpass(){
        Intent intent = new Intent(TroubleActivity.this,ForgetPassAcitivity.class);
        startActivity(intent);
    }
    public void sendmail(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"myday@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Sign In/Up Problem");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }
}
