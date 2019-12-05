package com.gosigitgo.implicit_intent_2;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioManagerActivity extends AppCompatActivity {

    @BindView(R.id.btn_ring)
    Button btnRing;
    @BindView(R.id.btn_silent)
    Button btnSilent;
    @BindView(R.id.btn_vibrate)
    Button btnVibrate;

    private AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);
        ButterKnife.bind(this);

        //menampilkan hasil kirman data dari notification
        Intent intent=getIntent();
        if (intent.getStringExtra("NOTIF") != null) {
            String text = intent.getStringExtra("NOTIF");
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        }


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    }

    @OnClick({R.id.btn_ring, R.id.btn_silent, R.id.btn_vibrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ring:

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(this, "mode normal", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_silent:
                //diarahkan ke notification dulu
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //penecekan version hp
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()){
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }

                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(this, "mode silent", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_vibrate:

                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(this, "mode getar", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
