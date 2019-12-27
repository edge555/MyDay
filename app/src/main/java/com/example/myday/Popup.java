package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Popup extends AppCompatActivity {
    Exampleitem e;
    String title = "", date = "", time = "", repeat = "", des = "", color = "";
    TextView poptitle, poptime, popdate, poprepeat, popdes;
    ImageView popmarker;

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
            color = e.getString("marker");
        }
        poptitle = findViewById(R.id.poptitle);
        popdate = findViewById(R.id.popdate);
        poptime = findViewById(R.id.poptime);
        poprepeat = findViewById(R.id.poprepeat);
        popdes = findViewById(R.id.popdes);
        popmarker = findViewById(R.id.popmarker);
        poptitle.setText(title);
        popdate.setText(parsedate(date));
        poptime.setText(parsetime(time));
        poprepeat.setText(repeat);
        popdes.setText(des);

        switch (color) {
            case "Black":
                popmarker.setImageResource(R.drawable.mblack);
                break;
            case "Red":
                popmarker.setImageResource(R.drawable.mred);
                break;
            case "Green":
                popmarker.setImageResource(R.drawable.mgreen);
                break;
            case "Yellow":
                popmarker.setImageResource(R.drawable.myellow);
                break;
            case "Purple":
                popmarker.setImageResource(R.drawable.mpurple);
                break;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * .8);
        int height = (int) (dm.heightPixels * .6);
        getWindow().setLayout(width, height);
    }

    public String parsedate(String d) {
        if (d.equals("---")) {
            return d;
        }
        String year = d.substring(0, 4), m = d.substring(4, 6), day = d.substring(6, 8);
        int month = Integer.parseInt(m) + 1;
        return day + "/" + month + "/" + year;
    }

    public String parsetime(String d) {
        if (d.equals("---")) {
            return d;
        }
        String h = d.substring(0, 2), m = d.substring(2, 4);
        int hr = Integer.parseInt(h);
        Boolean pm = false;
        if (hr >= 12) {
            pm = true;
            hr %= 12;
            if (hr == 0)
                hr = 12;
        }
        h = String.valueOf(hr);
        return h + ":" + m + (pm ? " PM" : " AM");
    }
}