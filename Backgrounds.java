package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Backgrounds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backgrounds);

        Button BackgroundsBackButton= findViewById(R.id.backgrounds_back_button);

        BackgroundsBackButton.setOnClickListener(new BackgroundsBackButtonHandler());
    }
    private class BackgroundsBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Store.class);
            startActivity(intent);
        }
    }
}
