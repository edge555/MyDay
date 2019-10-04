package com.example.myday;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private Exampleadapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Exampleitem>mexamplelist;
    private TextView tv,tv2;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MY DAY");
        setContentView(R.layout.activity_main);
        ///////
        mexamplelist = new ArrayList<>();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        if(curuser!=null){
            String uid = curuser.getUid();
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                        if(childsnap.getKey().equals("Name")){
                            if(childsnap.getValue()!=null) {
                                tv = findViewById(R.id.topname);
                                tv.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if(childsnap.getKey().equals("Email")){
                            if(childsnap.getValue()!=null) {
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
                    List<String> arr = new ArrayList<String>();
                    int k;
                    for(k=0;k<mexamplelist.size();k++){
                        arr.add(mexamplelist.get(k).getTitle());
                    }
                    k=0;
                    for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                        String date=childsnap.getKey();
                        HashMap<String,String>hmp;
                        hmp = (HashMap<String, String>) childsnap.getValue();
                        String name = hmp.get("title");
                        if(k==0){
                            String d = hmp.get("time");
                            String h = d.substring(0,2),m = d.substring(2,4);
                            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Date dat = new Date();
                            Calendar cal_alarm = Calendar.getInstance();
                            //Calendar cal_now = Calendar.getInstance();
                            //cal_now.setTime(dat);
                            cal_alarm.setTime(dat);
                            cal_alarm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(h));
                            cal_alarm.set(Calendar.MINUTE,Integer.parseInt(m));
                            cal_alarm.set(Calendar.SECOND,0);

                            Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

                            manager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(), pendingIntent);
                        }
                        k++;
                        Boolean exist=arr.contains(name);
                        if(exist==false){
                            mexamplelist.add(new Exampleitem(hmp.get("title"),hmp.get("time"),hmp.get("date"),date));
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
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
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

        //noinspection SimplifiableIfStatement
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
        }else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(MainActivity.this,InviteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}