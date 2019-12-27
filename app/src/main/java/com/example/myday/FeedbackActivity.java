package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    Button feedbut;
    EditText feedtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbut = findViewById(R.id.feedbackbut);
        feedtext = findViewById(R.id.feedbacktext);
        feedbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = feedtext.getText().toString();
                if (!text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Thanks for your feedback!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please write something", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
