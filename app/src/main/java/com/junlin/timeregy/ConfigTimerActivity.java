package com.junlin.timeregy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.junlin.timeregy.data.TimeregyDatabase;

public class ConfigTimerActivity extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = ConfigTimerActivity.class.getSimpleName();

    // database instance
    private TimeregyDatabase tDb;

    TextInputEditText timerNameInput;
    CheckBox intervalCheckBox;

    // Note that work time here in in minutes,should be converted to seconds to store in the database
    TextView workTimeText;
    // Note that rest time here in in minutes,should be converted to seconds to store in the database
    TextView restTimeText;

    // TextView which holds the number of rounds
    TextView roundsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_timer);

        // getting a database instance
        tDb = TimeregyDatabase.getAppDatabase(getApplicationContext());

        // initiates all views
        createViews();


    }

    private void createViews() {

    }

    private void onCreateFabPressed(){

        // Close the activity at last
        finish();
    }
}