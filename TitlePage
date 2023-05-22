package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitlePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_page);

        Button sleepAlarmButton = findViewById(R.id.sleep_alarm_button);
        Button productivityTimerButton = findViewById(R.id.productivity_timer_button);
        Button storeButton = findViewById(R.id.store_button);

        sleepAlarmButton.setOnClickListener(new SleepAlarmButtonHandler());
        productivityTimerButton.setOnClickListener(new ProductivityTimerButtonHandler());
        storeButton.setOnClickListener(new StoreButtonHandler());
    }

    private class SleepAlarmButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), SleepTimer.class);
            startActivity(intent);
        }
    }

    private class ProductivityTimerButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), ProductivityTimer.class);
            startActivity(intent);
        }
    }

    private class StoreButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Store.class);
            startActivity(intent);
        }
    }
}
