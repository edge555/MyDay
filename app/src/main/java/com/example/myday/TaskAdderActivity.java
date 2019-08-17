package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TaskAdderActivity extends AppCompatActivity {
    private Button adderdate,addertime,adderset;
    private TextView addertask;
    private String curdate="",curtime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_adder);
        adderdate = findViewById(R.id.adderdate);
        adderdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdate();
            }
        });
        addertime = findViewById(R.id.addertime);
        addertime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settime();
            }
        });
        adderset = findViewById(R.id.adderset);
        adderset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settask();
            }
        });
    }
    public void setdate(){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String y = Integer.toString(year);
                String m = Integer.toString(month);
                String d = Integer.toString(date);
                if(m.length()!=2)
                    y+="0";
                if(d.length()!=2)
                    m+="0";
                curdate=y+m+d;
                //Log.d("nowdate",curdate);
            }
        },year,month,date);
        datePickerDialog.show();
    }
    public void settime(){
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String s="";
                String h = Integer.toString(hour);
                String m = Integer.toString(min);
                if(h.length()!=2)
                    s+="0";
                s+=h;
                if(m.length()!=2)
                    s+="0";
                s+=m;
                curtime = s;
                //Log.d("nowtime",s);
            }
        },hour,min,true);
        timePickerDialog.show();
    }
    public void settask(){
        if(curdate.isEmpty() || curtime.isEmpty()){
            Toast.makeText(getApplicationContext(),"Choose Time and Date",Toast.LENGTH_LONG).show();
        }
        else{
            String fin=curdate+curtime;
            Bundle bundle = new Bundle();
            bundle.putString("value",fin);
            //Log.d("finaltime",fin);
            Intent intent = new Intent(TaskAdderActivity.this,MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
