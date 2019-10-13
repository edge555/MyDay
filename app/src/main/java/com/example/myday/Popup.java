package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

public class Popup extends AppCompatActivity {
    Exampleitem e;
    String title="",date="",time="",repeat="",des="";
    TextView poptitle,poptime,popdate,poprepeat,popdes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Bundle e = getIntent().getExtras();
        if (e != null) {
            title = e.getString("title");
            time = e.getString("time");
            date = e.getString("date");
            repeat = e.getString("repeat");
            des = e.getString("des");
        }
        poptitle = findViewById(R.id.poptitle);
        popdate = findViewById(R.id.popdate);
        poptime = findViewById(R.id.poptime);
        poprepeat = findViewById(R.id.poprepeat);
        popdes = findViewById(R.id.popdes);
        poptitle.setText(title);
        popdate.setText(parsedate(date));
        poptime.setText(parsetime(time));
        poprepeat.setText(repeat);
        popdes.setText(des);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels*.8);
        int height = (int) (dm.heightPixels*.6);
        getWindow().setLayout(width,height);
    }
    public String parsedate(String d){
        String year = d.substring(0,4), month = d.substring(4,6), day = d.substring(6,8);
        return day+"/"+month+"/"+year;
    }
    public String parsetime(String d){
        String h = d.substring(0,2),m = d.substring(2,4);
        int hr = Integer.parseInt(h);
        Boolean pm = false;
        if(hr>=12){
            pm=true;
            hr%=12;
            if(hr==0)
                hr=12;
        }
        h = String.valueOf(hr);
        return h+":"+m+(pm?" PM":" AM");
    }
}
