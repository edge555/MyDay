package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;

public class UpdateActivity extends AppCompatActivity {

    TextView updatetext, updatever;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        progressDialog = new ProgressDialog(UpdateActivity.this);
        progressDialog.setTitle("Update");
        progressDialog.setMessage("Checking for updates...");
        progressDialog.show();
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                updatetext = findViewById(R.id.updatetext);
                updatetext.setText("You are up to date! Check again later");
                updatever = findViewById(R.id.updatever);
                updatever.setText("Current version v1.0.1");
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2500);
    }
}
