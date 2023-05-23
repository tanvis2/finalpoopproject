package edu.ncssm.krishnakumar24r.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductivityTimer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productivity_timer);

        Button ProductivityTimerBackButton = findViewById(R.id.productivity_back_button);

        ProductivityTimerBackButton.setOnClickListener(new ProductivityTimerBackButtonHandler());

    }
    private class ProductivityTimerBackButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), TitlePage.class);
            startActivity(intent);
        }
    }
}
