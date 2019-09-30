package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InviteActivity extends AppCompatActivity {
    private Button bt;
    private TextView tv;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        bt = findViewById(R.id.invitecopy);
        tv = findViewById(R.id.invitelink);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
