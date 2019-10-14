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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    Button profsout,profdel,profichange,profupgrade;
    LinearLayout ll;
    FirebaseAuth mAuth;
    FirebaseUser curuser;
    DatabaseReference db;
    private Uri filepath;
    private final int PICK_IMAGE_REQ = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        curuser = mAuth.getCurrentUser();
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
}