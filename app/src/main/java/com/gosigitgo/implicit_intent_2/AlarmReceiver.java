package com.gosigitgo.implicit_intent_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm berbunyi", Toast.LENGTH_SHORT).show();
        Log.d("Alarmcek", "jalan");
        MediaPlayer player=MediaPlayer.create(context, R.raw.alarm);
        player.start();
    }
}
