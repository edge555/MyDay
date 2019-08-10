package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class OpeningActivity extends AppCompatActivity {
    private ProgressBar pgbar;
    private int prg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        pgbar = findViewById(R.id.progress);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dowork();
                startapp();
            }
        });
        thread.start();
    }

    private void dowork() {
        for (prg = 20; prg <= 100; prg += 100) {
            try {
                Thread.sleep(500);
                pgbar.setProgress(prg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void startapp(){
        Intent intent = new Intent(OpeningActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }

}