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

    private TextView tvVib,tvRem;
    private Switch darkSwitch,fullSwitch,notSwitch,vibSwitch;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        darkSwitch = findViewById(R.id.darkswitch);
        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggledarkmode();
            }
        });
        fullSwitch = findViewById(R.id.fullswitch);
        fullSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglefullscreen();
            }
        });
        notSwitch = findViewById(R.id.notisoundswitch);
        notSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglesound();
            }
        });
        vibSwitch = findViewById(R.id.vibswitch);
        vibSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglevib();
            }
        });
    }

    private void togglevib() {
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Vibration");
        if(vibSwitch.isChecked()){
            db.setValue("True");
        }
        else{
            db.setValue("False");
        }
    }

    private void togglesound() {
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Sound");
        if(notSwitch.isChecked()){
            db.setValue("True");
        }
        else{
            db.setValue("False");
        }
    }

    private void togglefullscreen() {

    }

    private void toggledarkmode() {
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings").child("Darkmode");
        if(darkSwitch.isChecked()){
            db.setValue("True");
        }
        else{
            db.setValue("False");
        }
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