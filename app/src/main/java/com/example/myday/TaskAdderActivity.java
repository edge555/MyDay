package com.example.myday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TaskAdderActivity extends AppCompatActivity {
    private Button adderdate,addertime,adderset;
    private EditText addername;
    private String curdate="",curtime="",taskdate="",tasktime="";
    private TextView repeattv;
    private DatabaseReference db;
    private static int noww,repeat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_adder);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noww = extras.getInt("now");
        }
        adderset = findViewById(R.id.adderset);
        adderset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settask();
            }
        });
    }
    public void setdate(View view){
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE);
        String y = Integer.toString(year);
        String m = Integer.toString(month);
        String d = Integer.toString(date);
        if(m.length()!=2)
            y+="0";
        if(d.length()!=2)
            m+="0";
        curdate=y+m+d;
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
                taskdate=y+m+d;
            }
        },year,month,date);
        datePickerDialog.show();
    }
    public void settime(View view){
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
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
                tasktime = s;
            }
        },hour,min,true);
        timePickerDialog.show();
    }
    public void settask(){
        boolean flag=true;
        addername = findViewById(R.id.addername);
        if(taskdate.compareTo(curdate)<0){
            flag=false;
        }
        else if(taskdate.compareTo(curdate)==0){
            if(tasktime.compareTo(curtime)<0){
                flag=false;
            }
        }
        String task = addername.getText().toString();
        if(taskdate.isEmpty() || tasktime.isEmpty()){
            Toast.makeText(getApplicationContext(),"Choose Time and Date",Toast.LENGTH_LONG).show();
        }
        else if(task.isEmpty()){
            Toast.makeText(getApplicationContext(),"Task name is empty",Toast.LENGTH_LONG).show();
        }
        else if(!flag){
            Toast.makeText(getApplicationContext(),"You can't choose previous time and date",Toast.LENGTH_LONG).show();
        }
        else if(flag){
            Random rand = new Random();
            int rNum = 100 + rand.nextInt((999 - 100) + 1);
            String fin=taskdate+tasktime+Integer.toString(rNum);
            FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
            if(curuser!=null){
                String uid = curuser.getUid();
                if(noww==2){
                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder");
                }
                else{
                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
                }
                Map<String,Object>val = new TreeMap<>();
                Info info = new Info(task,"Null","Null",taskdate,tasktime,fin);
                val.put(fin,info);
                db.updateChildren(val);
            }
            String v = "false";
            Intent intent = new Intent(TaskAdderActivity.this,MainActivity.class);
            intent.putExtra("key",v);
            startActivity(intent);
        }
    }

    public void setrepeat(View view) {
        repeat=(repeat+1)%5;
        repeattv = findViewById(R.id.adderrepeat);
        switch (repeat){
            case 0:
                repeattv.setText("None");
                break;
            case 1:
                repeattv.setText("Daily");
                break;
            case 2:
                repeattv.setText("Weekly");
                break;
            case 3:
                repeattv.setText("Monthly");
                break;
            case 4:
                repeattv.setText("Yearly");
                break;
        }
    }
}
