package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudyMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_music);

        Button StudyMusicBackButton = findViewById(R.id.music_back_button);

        StudyMusicBackButton.setOnClickListener(new StudyMusicBackButtonHandler());

    }
    private class StudyMusicBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Store.class);
            startActivity(intent);
        }
    }
}
