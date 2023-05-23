package edu.ncssm.sharma24t.productivityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class ProductivityTimer extends AppCompatActivity implements SensorEventListener{

    private int coins = 100;

    private long accelStartTime;
    private EditText mEditTextInput;
    private String textInputValue;
    private TextView mTextViewCountDown;
    private TextView mTextViewCoins;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private Button mStoreButton;
    private int fullTime;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private SensorManager sensorManager;
    private Sensor gyroscope;

    private float gyroX = 0.0f;
    private int time;
    private float gyroY = 0.0f;
    private float gyroZ = 0.0f;



   /* private MainActivity.Accelerometer acc;

    private double accX;
    private double accY;
    private double accZ;
    //private SensorManager sensorManager;
    //private Sensor accelerometer;
    */

//    Button ProductivityTimerBackButton = findViewById(R.id.productivity_back_button);
//
//    private class ProductivityTimerBackButtonHandler implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getApplicationContext(), TitlePage.class);
//            startActivity(intent);
//        }
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e("Sensor", "Gyroscope not available!");
        }

        //ProductivityTimerBackButton.setOnClickListener(new ProductivityTimerBackButtonHandler());

        mEditTextInput = (EditText) findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mTextViewCoins = findViewById(R.id.coinText);
        mStoreButton = findViewById(R.id.store_button);

        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);

                intent.putExtra("coins", coins);
                startActivity(intent);
            }
        });

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                System.out.println(millisInput);
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                mTimeLeftInMillis = millisInput;

                textInputValue = input;
                fullTime = Integer.parseInt(input);


//                int col = textInputValue.indexOf(":");
//                int first = Integer.parseInt(textInputValue.substring(0,col))*60;
//                int second = Integer.parseInt(textInputValue.substring(col+1));
//                time = first+second;
//                setTime(millisInput);
//                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // We are not interested in changes in sensor accuracy in this example
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if( System.currentTimeMillis() < accelStartTime + 6000){
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroX = event.values[0];
            gyroY = event.values[1];
            gyroZ = event.values[2];

           //Log.i("Sensor", "x: " + gyroX + ", y: " + gyroY + ", z: " + gyroZ);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {

        accelStartTime = System.currentTimeMillis();
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
//    public class GameView extends SurfaceView {
//        public GameView(Context context) {
//            super(context);
//        }
//
//
//        public GameView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//
//            SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
//            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//            acc = new MainActivity.Accelerometer();
//            sensorManager.registerListener(acc, sensor, SensorManager.SENSOR_DELAY_GAME);
//        }
//    }


    final int initialSeconds = (int) (mTimeLeftInMillis / 1000) % 60;
    int secondCounter;
    private void updateCountDownText() {

        double threshold = 0.1;
        //double waitTime = 5;
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        //System.out.println(seconds);

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        if(Math.abs(gyroX) > threshold || Math.abs(gyroY) > threshold || Math.abs(gyroZ) > threshold) {
            //sound the alarm!
            //System.out.println(time);
            //System.out.println(seconds);
            System.out.println("Threshold passed");

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

        } else {
            secondCounter += 20; //should be +=1 for 1 coin per minute
            if(secondCounter == 60) {
                coins += 1;
                mTextViewCoins.setText("You Have " + Integer.toString(coins) + " coins!");
                secondCounter = 0;
            }
            System.out.println(coins);
        }

        mTextViewCountDown.setText(timeLeftFormatted);


    }

    private void updateWatchInterface(){
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }




}
