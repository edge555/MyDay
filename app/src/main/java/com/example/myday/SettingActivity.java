package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private TextView tvShake,tvNot,tvRem;
    private ImageButton pink,black,blue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onProfile(View v){

    }
    public void onInvite(View v){

    }
    public void onFeed(View v){

    }
    public void onDef(View v){

    }
    public void onAbout(View v){

    }
    public void onWid(View v){

    }
    public void onShake(View v){
        tvShake = findViewById(R.id.tvShake);
        String a = tvShake.getText().toString();
        if(a.equals("ON")){
            tvShake.setText("OFF");
        }
        else{
            tvShake.setText("ON");
        }
    }
    public void onNot(View v){
        tvNot = findViewById(R.id.tvNot);
        String a = tvNot.getText().toString();
        if(a.equals("ON")){
            tvNot.setText("OFF");
        }
        else{
            tvNot.setText("ON");
        }
    }
    public void onRem(View v){
        tvRem = findViewById(R.id.tvRem);
        String a = tvRem.getText().toString();
        if(a.equals("ON")){
            tvRem.setText("OFF");
        }
        else{
            tvRem.setText("ON");
        }
    }

}