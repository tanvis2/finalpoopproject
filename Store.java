package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store2);

        Button StoreBackButton = findViewById(R.id.store_back_button);
        Button StudyMusicButton = findViewById(R.id.study_music_button);
        Button AlarmSoundsButton = findViewById(R.id.alarm_sound_button);
        Button BackgroundsButton = findViewById(R.id.backgrounds_button);
        Button NightModeButton = findViewById(R.id.nightmode_button);

        StoreBackButton.setOnClickListener(new StoreBackButtonHandler());
        StudyMusicButton.setOnClickListener(new StudyMusicButtonHandler());
        AlarmSoundsButton.setOnClickListener(new AlarmSoundsButtonHandler());
        BackgroundsButton.setOnClickListener(new BackgroundsButtonHandler());
        NightModeButton.setOnClickListener(new NightModeButtonHandler());
    }
    private class StoreBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), TitlePage.class);
            startActivity(intent);
        }
    }
    private class StudyMusicButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), StudyMusic.class);
            startActivity(intent);
        }
    }
    private class AlarmSoundsButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AlarmSounds.class);
            startActivity(intent);
        }
    }
    private class BackgroundsButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Backgrounds.class);
            startActivity(intent);
        }
    }
    private class NightModeButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Night_Mode.class);
            startActivity(intent);
        }
    }
}
