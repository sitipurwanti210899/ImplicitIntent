package com.gosigitgo.implicit_intent_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_audiomanager)
    Button btnAudiomanager;
    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_wifi)
    Button btnWifi;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_sms)
    Button btnSms;
    @BindView(R.id.btn_tlp)
    Button btnTlp;
    @BindView(R.id.btn_kamera)
    Button btnKamera;
    @BindView(R.id.btn_browser)
    Button btnBrowser;
    @BindView(R.id.btn_alarm)
    Button btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_audiomanager, R.id.btn_notification, R.id.btn_wifi, R.id.btn_email, R.id.btn_sms, R.id.btn_tlp, R.id.btn_kamera, R.id.btn_browser, R.id.btn_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_audiomanager:
                startActivity(new Intent(this, AudioManagerActivity.class));
                break;
            case R.id.btn_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_wifi:
                startActivity(new Intent(this, WifiActivity.class));
                break;
            case R.id.btn_email:
                startActivity(new Intent(this, EmailActivity.class));
                break;
            case R.id.btn_sms:
                startActivity(new Intent(this, SMSActivity.class));
                break;
            case R.id.btn_tlp:
                startActivity(new Intent(this, TeleponActivity.class));
                break;
            case R.id.btn_kamera:
                startActivity(new Intent(this, KameraActivity.class));
                break;
            case R.id.btn_browser:
                break;
            case R.id.btn_alarm:
                startActivity(new Intent(this, AlarmActivity.class));
                break;
        }
    }
}
