package com.example.myday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer player;
    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "ALARM", Toast.LENGTH_LONG).show();
        if(player==null){
            player = MediaPlayer.create(context,R.raw.tone);

        }
        player.start();
    }
}