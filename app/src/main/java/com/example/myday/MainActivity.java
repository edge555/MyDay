package com.example.myday;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private Exampleadapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Exampleitem>mexamplelist;
    private TextView tv,tv2;
    private final String Channel_ID = "My_Channel";
    private final int Notification_ID = 001;
    boolean notification,vibration;
    ProgressDialog progressDialog;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseReference db,dbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MY DAY");
        setContentView(R.layout.activity_main);

        String firstrun = "true";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            firstrun = extras.getString("key");
        }
        if(firstrun.equals("true")){
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Getting your tasks...");
            progressDialog.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            };
            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 5000);
        }

        mexamplelist = new ArrayList<>();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        if(curuser!=null) {
            String uid = curuser.getUid();
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                        if (childsnap.getKey().equals("Name")) {
                            if (childsnap.getValue() != null) {
                                tv = findViewById(R.id.topname);
                                tv.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if (childsnap.getKey().equals("Email")) {
                            if (childsnap.getValue() != null) {
                                tv2 = findViewById(R.id.topemail);
                                tv2.setText((CharSequence) childsnap.getValue());
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
            db.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String>arr = new ArrayList<String>();
                    int k;
                    for(k=0;k<mexamplelist.size();k++){
                        arr.add(mexamplelist.get(k).getFull());
                    }
                    k=0;
                    for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                        String date=childsnap.getKey();
                        HashMap<String,String>hmp;
                        hmp = (HashMap<String, String>) childsnap.getValue();
                        Boolean exist=arr.contains(date);
                        if(exist==false){
                            k++;
                            mexamplelist.add(new Exampleitem(hmp.get("title"),hmp.get("date"),hmp.get("time"),date));
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        buildrecylerview();
        refreshTask();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,TaskAdderActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void refreshTask()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String s = getTimeMethod("dd-MMM-yy-hh-mm-ss a");
                        if(s.substring(16,18).equals("00")){
                            final String curtime = process(s);
                            mexamplelist = new ArrayList<>();
                            FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                            final String uid = curuser.getUid();
                            if(curuser!=null){
                                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        List<String>arr = new ArrayList<String>();
                                        int k;
                                        for(k=0;k<mexamplelist.size();k++){
                                            arr.add(mexamplelist.get(k).getFull());
                                        }
                                        k=0;
                                        for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                                            String date=childsnap.getKey();
                                            HashMap<String,String>hmp;
                                            hmp = (HashMap<String, String>) childsnap.getValue();
                                            String tasktime = date.substring(0,12);
                                            if(curtime.compareTo(tasktime)==0){
                                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                                    showNotification1();
                                                }
                                                else{
                                                    showNotification2();
                                                }
                                            }
                                            else if(curtime.compareTo(tasktime)>0){

                                                dbb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Pasttask");
                                                Map<String,Object> val = new TreeMap<>();
                                                Info info = new Info(hmp.get("title"),"Null","Null",hmp.get("date"),hmp.get("time"),date);
                                                val.put(date,info);
                                                dbb.updateChildren(val);
                                                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task").child(date);
                                                db.setValue(null);
                                                continue;
                                            }
                                            Boolean exist=arr.contains(date);
                                            if(exist==false){
                                                k++;
                                                mexamplelist.add(new Exampleitem(hmp.get("title"),hmp.get("date"),hmp.get("time"),date));
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            buildrecylerview();
                        }
                    };
                });
            }
        }, 0, 1000);
    }
    private void showNotification1() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,Channel_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("My Day");
        builder.setContentText("You have task now");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(Notification_ID,builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification2() {
        String id = "main_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        CharSequence name = "Channel Name";
        String description = "Channel Description";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(id,name,importance);
        notificationChannel.setDescription(description);
        notificationChannel.enableVibration(true);
        if(notificationManager!=null){
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("My Day");
        builder.setContentText("You have task now");
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1000,builder.build());
    }
    private String process(String s) {
        String f = "20"+s.substring(7,9);
        String month = s.substring(3,6);
        if(month.equals("Jan")){
            f+="00";
        }
        else if(month.equals("Feb")){
            f+="01";
        }
        else if(month.equals("Mar")){
            f+="02";
        }
        else if(month.equals("Apr")){
            f+="03";
        }
        else if(month.equals("May")){
            f+="04";
        }
        else if(month.equals("Jun")){
            f+="05";
        }
        else if(month.equals("Jul")){
            f+="06";
        }
        else if(month.equals("Aug")){
            f+="07";
        }
        else if(month.equals("Sep")){
            f+="08";
        }
        else if(month.equals("Oct")){
            f+="09";
        }
        else if(month.equals("Nov")){
            f+="10";
        }
        else{
            f+="11";
        }
        f+=s.substring(0,2);
        String h = s.substring(10,12);
        int hr = Integer.parseInt(h);
        if(s.charAt(19)=='P'){
            hr += 12;
        }
        String hour = String.valueOf(hr);
        if(hour.length()==1){
            f+="0";
        }
        f+=hour;
        f+=s.substring(13,15);
        return f;
    }

    private String getTimeMethod(String formate)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    public void removeitem(int position){
        Exampleitem curitem = mexamplelist.get(position);
        String delid = curitem.getFull();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task").child(delid);
        db.setValue(null);
        mexamplelist.remove(position);
        mAdapter.notifyDataSetChanged();
    }
    public void buildrecylerview() {
        mRecyclerView = findViewById(R.id.mainll);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Exampleadapter(mexamplelist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Exampleadapter.OnItemClickListener() {
            @Override
            public void onitemclick(int position) {
                Intent intent = new Intent(MainActivity.this,Popup.class);
                startActivity(intent);
            }
            @Override
            public void ondelete(int position) {
                removeitem(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            if(doubleBackToExitPressedOnce) {
                finish();
                moveTaskToBack(true);
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 1500);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionsettings) {
            Intent intent = new Intent(this.getApplicationContext(),SettingActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.actionaboutus){

        }
        else if(id == R.id.actionupdate){
            Intent intent = new Intent(this.getApplicationContext(),UpdateActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_prof) {
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_past) {
            Intent intent = new Intent(MainActivity.this,PastTaskActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this,InviteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            finish();
            moveTaskToBack(true);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
