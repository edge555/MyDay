package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskActivity extends AppCompatActivity {
    private Button but1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        but1 = findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this,TaskAdderActivity.class);
                startActivity(intent);
            }
        });
    }
}
