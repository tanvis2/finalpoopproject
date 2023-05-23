package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.ncssm.krishnakumar24r.finalproject.databinding.ActivityMainBinding;
import edu.ncssm.krishnakumar24r.finalproject.databinding.ActivitySleepTimerBinding;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;


public class SleepTimer extends AppCompatActivity {

    private ActivitySleepTimerBinding binding;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);
        binding = ActivitySleepTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Alarm Time")
                        .build();
                timePicker.show(getSupportFragmentManager(), "androidknowledge");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (timePicker.getHour() > 12){
                            binding.selectTime.setText(
                                    String.format("%02d",(timePicker.getHour()-12)) +":"+ String.format("%02d", timePicker.getMinute())+"PM"
                            );
                        } else {
                            binding.selectTime.setText(timePicker.getHour()+":" + timePicker.getMinute()+ "AM");
                        }
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                });
            }
        });

        binding.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time;
                Toast.makeText(SleepTimer.this, "ALARM ON", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();

                // calendar is called to get current time in hour and minute
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                // using intent i have class AlarmReceiver class which inherits
                // BroadcastReceiver
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                // we call broadcast using pendingIntent
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                if (System.currentTimeMillis() > time) {
                    // setting time as AM and PM
                    if (Calendar.AM_PM == 0)
                        time = time + (1000 * 60 * 60 * 12);
                    else
                        time = time + (1000 * 60 * 60 * 24);
                }
                // Alarm rings continuously until toggle button is turned off
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
                // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);


//                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
//                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//                Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
            }
        });
        binding.cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
//                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                new RingtoneManager(getApplicationContext()).stopPreviousRingtone();
                if (alarmManager == null) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
            }
        });





        Button sleepTimerBackButton = findViewById(R.id.sleep_timer_back_button);
        Button pedometerButton = findViewById(R.id.pedometerButton);
        sleepTimerBackButton.setOnClickListener(new SleepTimerBackButtonHandler());
        pedometerButton.setOnClickListener(new PedometerButtonHandler());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "akchannel";
            String desc = "Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("androidknowledge", name, imp);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private class SleepTimerBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), TitlePage.class);
            startActivity(intent);
        }
    }

    private class PedometerButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Pedometer.class);
            startActivity(intent);
        }
    }

}
