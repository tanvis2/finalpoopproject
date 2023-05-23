package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmSounds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_sounds);

        Button AlarmSoundsBackButton = findViewById(R.id.alarmsounds_back_button);

        AlarmSoundsBackButton.setOnClickListener(new AlarmSoundsBackButtonHandler());
    }
    private class AlarmSoundsBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Store.class);
            startActivity(intent);
        }
    }
}
