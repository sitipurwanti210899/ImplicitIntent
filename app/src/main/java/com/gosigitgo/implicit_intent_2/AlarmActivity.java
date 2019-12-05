package com.gosigitgo.implicit_intent_2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.analog_clock)
    AnalogClock analogClock;
    @BindView(R.id.btn_set_alarm)
    Button btnSetAlarm;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }
    //panggil class
    private TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int i, int i1) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY,i);
            calSet.set(Calendar.MINUTE, i1);

            if (calSet.compareTo(calNow)<=0){
                calSet.add(Calendar.DATE, 0);
            }else if (calSet.compareTo(calNow)>0){
                Toast.makeText(AlarmActivity.this, "Alarm diset untuk :"+calSet.getTime(),Toast.LENGTH_SHORT).show();

            }
            tvAlarm.setText("alarm berbunyi pada :"+calSet.getTime());

            Intent intent=new Intent(AlarmActivity.this,AlarmReceiver.class );
            PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(), 1, intent,0);

            //panggil class alarm manager
            AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),pendingIntent);

        }
    };


    @OnClick( R.id.btn_set_alarm)
    public void onViewClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);

        timePickerDialog.show();
    }
}
