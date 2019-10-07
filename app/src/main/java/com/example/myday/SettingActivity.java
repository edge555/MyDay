package com.example.myday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {

    private TextView tvVib,tvNot,tvRem;
    private Switch aSwitch;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


            

        aSwitch = findViewById(R.id.darkswitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = curuser.getUid();
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Darkmode");
                if(aSwitch.isChecked()){
                    db.setValue("True");
                }
                else{
                    db.setValue("False");
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
        resetDefault();
    }

    public void onAbout(View v){

    }
    public void onVib(View v){
        tvVib = findViewById(R.id.tvVib);
        String a = tvVib.getText().toString();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Vibration");
        if(a.equals("ON")){
            tvVib.setText("OFF");
            db.setValue("True");
        }
        else{
            tvVib.setText("ON");
            db.setValue("False");
        }
    }
    public void onNot(View v){
        tvNot = findViewById(R.id.tvNot);
        String a = tvNot.getText().toString();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Notification");
        if(a.equals("ON")){
            tvNot.setText("OFF");
            db.setValue("True");
        }
        else{
            tvNot.setText("ON");
            db.setValue("False");
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

    private void resetDefault() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
        dialog.setTitle("Reset");
        dialog.setMessage("Do you want to reset settings of default?");
        dialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = curuser.getUid();
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Darkmode");
                db.setValue("False");
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Vibration");
                db.setValue("True");
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Sound");
                db.setValue("True");
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Fullscreen");
                db.setValue("False");
                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Notification");
                db.setValue("True");
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}