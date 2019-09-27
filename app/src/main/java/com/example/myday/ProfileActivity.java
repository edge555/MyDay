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

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    Button profsout,profdel,profichange;
    ImageView imgv;
    LinearLayout ll;
    FirebaseAuth mAuth;
    FirebaseUser curuser;
    private Uri filepath;
    private final int PICK_IMAGE_REQ = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgv = findViewById(R.id.profimg);
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
        profichange = findViewById(R.id.profimagechange);
        profichange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                changeimage();
            }
        });
    }
    public void signout(){
        mAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void changeimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select picture"),PICK_IMAGE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imgv.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
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