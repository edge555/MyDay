package com.example.myday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    Button profsout,profdel,profupgrade,profupdate;
    private TextView profmail;
    private EditText profname,profprofession,profmobile;
    LinearLayout ll;
    FirebaseAuth mAuth;
    FirebaseUser curuser;
    DatabaseReference db;
    static boolean nameEnable = false,professionEnabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setInfo();
        seteditextsfalse();
        mAuth=FirebaseAuth.getInstance();
        curuser = mAuth.getCurrentUser();
        setonclicklisteners();
    }

    private void setonclicklisteners() {
        profdel = findViewById(R.id.profdelete);
        profdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteprofile();
            }
        });
        profsout = findViewById(R.id.profsignout);
        profsout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });
        profupgrade = findViewById(R.id.profupgrade);
        profupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradetopremium();
            }
        });
        profupdate = findViewById(R.id.profupdate);
        profupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateinfo();
            }
        });
    }

    private void updateinfo() {
        profname = findViewById(R.id.profname);
        profmail = findViewById(R.id.profmail);
        profprofession = findViewById(R.id.profprofession);
        profmobile = findViewById(R.id.profmobile);
        String name = profname.getText().toString();
        String profession = profprofession.getText().toString();
        String mobile = profmobile.getText().toString();
        String mail = profmail.getText().toString();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        if(curuser!=null) {
            String uid = curuser.getUid();
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
            HashMap<String,String> info=new HashMap<>();
            info.put("Name",name);
            info.put("Email",mail);
            info.put("Profession",profession);
            info.put("Mobile",mobile);
            db.setValue(info);
        }
    }

    private void seteditextsfalse() {
        profname = findViewById(R.id.profname);
        toggleedit(false,profname);
        profprofession = findViewById(R.id.profprofession);
        toggleedit(false,profprofession);
        profmobile = findViewById(R.id.profmobile);
        toggleedit(false,profmobile);
    }

    private void toggleedit(boolean isEnabled, EditText editText) {
        editText.setFocusable(isEnabled);
        editText.setFocusableInTouchMode(isEnabled) ;
        editText.setClickable(isEnabled);
        editText.setLongClickable(isEnabled);
        editText.setCursorVisible(isEnabled);

    }

    private void setInfo() {
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        if(curuser!=null) {
            String uid = curuser.getUid();
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                        if (childsnap.getKey().equals("Name")) {
                            if (childsnap.getValue() != null) {
                                profname = findViewById(R.id.profname);
                                profname.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if (childsnap.getKey().equals("Email")) {
                            if (childsnap.getValue() != null) {
                                profmail = findViewById(R.id.profmail);
                                profmail.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if (childsnap.getKey().equals("Profession")) {
                            if (childsnap.getValue() != null) {
                                profprofession = findViewById(R.id.profprofession);
                                profprofession.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if (childsnap.getKey().equals("Mobile")) {
                            if (childsnap.getValue() != null) {
                                profmobile = findViewById(R.id.profmobile);
                                profmobile.setText((CharSequence) childsnap.getValue());
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void signout(){
        mAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void upgradetopremium(){
        Intent intent = new Intent(ProfileActivity.this,UpPremiumActivity.class);
        startActivity(intent);
    }

    public void showsnackbar1(){
        ll = findViewById(R.id.profile_layout);
        Snackbar sb = Snackbar.make(ll,"Account deleted successfully",Snackbar.LENGTH_LONG);
        sb.show();
    }
    public void showsnackbar2(){
        ll = findViewById(R.id.profile_layout);
        Snackbar sb = Snackbar.make(ll,"Account not deleted",Snackbar.LENGTH_LONG);
        sb.show();
    }

    public void updatename(View view) {
        profname = findViewById(R.id.profname);
        if(nameEnable){
            toggleedit(false,profname);
            nameEnable = false;
        }
        else{
            toggleedit(true,profname);
            nameEnable = true;
        }
    }

    public void updateprofession(View view) {
        profprofession = findViewById(R.id.profprofession);
        if(professionEnabled){
            toggleedit(false,profprofession);
            professionEnabled = false;
        }
        else{
            toggleedit(true, profprofession);
            professionEnabled = true;
        }
    }
    public void deleteprofile(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
        dialog.setTitle("Are you sure?");
        dialog.setMessage("This action can't be reversed");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                curuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            String uid = curuser.getUid();
                            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            db.setValue(null);
                            showsnackbar1();
                            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            showsnackbar2();
                        }
                    }
                });
            }
        });
        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void updatemobile(View view) {
        profmobile = findViewById(R.id.profmobile);
        if(nameEnable){
            toggleedit(false,profmobile);
            nameEnable = false;
        }
        else{
            toggleedit(true,profmobile);
            nameEnable = true;
        }
    }
}