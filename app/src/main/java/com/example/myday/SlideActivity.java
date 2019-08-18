package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SlideActivity extends AppCompatActivity {
    private ViewPager vp;
    private SlideAdapter sada;
    private Button gobut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        vp = findViewById(R.id.slidevp);
        sada = new SlideAdapter(this);

        vp.setAdapter(sada);
    }
}
