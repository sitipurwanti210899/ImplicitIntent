package com.gosigitgo.implicit_intent_2;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiActivity extends AppCompatActivity {

    @BindView(R.id.sw_wifi)
    Switch swWifi;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        wifiManager =(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()){
            swWifi.setChecked(true);
            tvStatus.setText("wifi belum aktif");
        }else {
            swWifi.setChecked(false);
            tvStatus.setText("wifi belum aktif");
        }
        swWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b){
                    wifiManager.setWifiEnabled(true);
                    tvStatus.setText("wifi aktif");
                }else {
                    wifiManager.setWifiEnabled(false);
                    tvStatus.setText("wifi tidak aktif");
                }

            }
        });
    }
}
