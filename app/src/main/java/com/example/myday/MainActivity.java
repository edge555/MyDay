package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button taskbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskbut=findViewById(R.id.taskbut);
        taskbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                //startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Do you want to exit?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

}
