package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Night_Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_mode);

        Button NightModeBackButton= findViewById(R.id.nightmode_back_button);

        NightModeBackButton.setOnClickListener(new NightModeBackButtonHandler());
    }
    private class NightModeBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Store.class);
            startActivity(intent);
        }
    }
}
