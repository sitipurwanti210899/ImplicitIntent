package com.gosigitgo.implicit_intent_2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.btn_pushnotification)
    Button btnPushnotification;

    private NotificationActivity notificationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_pushnotification)
    public void onViewClicked() {
        int idNotification=3;
        String channelId ="1";
        String channelName ="testing";

        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //kirim data ke audiomanager
        Intent intent = new Intent(this, AudioManagerActivity.class);
        //data yang dikirim
        intent.putExtra("NOTIF", "kirim data dari notifikasi");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                                                                            .setSmallIcon(R.mipmap.ic_launcher)
                                                                            .setContentTitle("Contoh Notif")
                                                                            .setContentText("Training Android Lanjutan 2")
                                                                            .setSubText("ImaStudio")
                                                                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                                                            .setContentIntent(pendingIntent)
                                                                            .setAutoCancel(true);
        //pengecekan diatas oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(idNotification, builder.build());
    }
}
