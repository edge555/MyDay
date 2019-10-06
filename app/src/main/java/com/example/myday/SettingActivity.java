package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private TextView tvShake,tvNot,tvRem;
    private Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        aSwitch = findViewById(R.id.darkswitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked()){
                    Toast.makeText(getApplicationContext(),"Checked",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Not Checked",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void onProfile(View v){
        Intent intent = new Intent(SettingActivity.this,ProfileActivity.class);
        startActivity(intent);
    }
    public void onFeed(View v){
        Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
        startActivity(intent);
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