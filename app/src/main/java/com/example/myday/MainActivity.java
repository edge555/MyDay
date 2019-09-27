package com.example.myday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button logbut;
    private ImageButton taskbut,todaybut,tombut,nextbut;
    private LinearLayout lltoday,lltom,llnext;
    private TextView tv,tv2,profn;
    private List<CheckBox>items=new ArrayList<CheckBox>();
    private List<String>ids=new ArrayList<String>();
    Queue<String>delids=new LinkedList<>();
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MY DAY");
        setContentView(R.layout.activity_main);
        ///////
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        if(curuser!=null){
            String uid = curuser.getUid();
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lltoday=findViewById(R.id.maintoday);
                    for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                        if(childsnap.getKey().equals("0")){
                            if(childsnap.getValue()!=null) {
                                tv = findViewById(R.id.topname);
                                tv.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else if(childsnap.getKey().equals("1")){
                            if(childsnap.getValue()!=null) {
                                tv2 = findViewById(R.id.topemail);
                                tv2.setText((CharSequence) childsnap.getValue());
                            }
                        }
                        else{
                            String date=childsnap.getKey();
                            String task= (String) childsnap.getValue();
                            CheckBox cb=new CheckBox(getApplicationContext());
                            cb.setText(task);
                            items.add(cb);
                            ids.add(date);
                            lltoday.addView(cb);
                        }
                        //Log.d("mapvalue",childsnap.getKey()+" "+ childsnap.getValue());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //////////////
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt=0;
                for(CheckBox item : items){
                    if(item.isChecked()){
                        cnt++;
                    }
                }
                if(cnt>0) {
                    lltoday.removeAllViews();
                }
                FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                if(curuser!=null){
                    int i=0;
                    String uid = curuser.getUid();
                    for(CheckBox item : items){
                        if(item.isChecked()){
                            final String id=ids.get(i);
                            //delids.add(id);
                            db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                                        if(childsnap.getKey().equals(id)){
                                            if(childsnap.getValue()!=null) {
                                                childsnap.getRef().setValue(null);
                                                break;
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        i++;
                    }
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        taskbut=findViewById(R.id.taskbut);
        taskbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_prof) {
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

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