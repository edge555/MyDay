package com.example.myday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    Button profsout,profdel;
    LinearLayout ll;
    FirebaseAuth mAuth;
    FirebaseUser curuser;
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
    }
    public void signout(){
        mAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
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