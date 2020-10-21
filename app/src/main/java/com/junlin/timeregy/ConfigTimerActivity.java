package com.junlin.timeregy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.ui.dialogs.TimeDialogFragment;

import java.util.List;

public class ConfigTimerActivity extends AppCompatActivity implements TimeDialogFragment.TimerDialogFragmentListener {
    // Tag for logging
    private static final String TAG = ConfigTimerActivity.class.getSimpleName();

    // database instance
    private TimeregyDatabase tDb;

    TextInputEditText timerNameInput;
    CheckBox intervalCheckBox;

    ConstraintLayout workCard;
    // Note that work time here in in minutes,should be converted to seconds to store in the database
    TextView workTimeMinText;
    TextView workTimeSecText;

    ConstraintLayout restCard;
    // Note that rest time here in in minutes,should be converted to seconds to store in the database
    TextView restTimeMinText;
    TextView restTimeSecText;

    ConstraintLayout roundCard;
    // TextView which holds the number of rounds
    TextView roundsText;

    RadioGroup tags;

    ConstraintLayout keepsRunningCard;
    ConstraintLayout pauseCard;
    ConstraintLayout cancelCard;

    TextInputEditText userRemarks;

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
        workCard = findViewById(R.id.work_time_constraint);
        restCard = findViewById(R.id.interval_time_constraint);
        intervalCheckBox = findViewById(R.id.interval_checkbox);
        workCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSetWorkTimeDialog(1,1,1,1);
            }
        });
        restCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalCheckBox.isChecked()) {
                }
            }
        });
    }

    private void openSetWorkTimeDialog(int hours, int minutes, int seconds, int id){
        TimeDialogFragment timeDialogFragment = new TimeDialogFragment();
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("hours", hours);
        bundle.putInt("minutes", minutes);
        bundle.putInt("seconds", seconds);
        bundle.putInt("id", id);
        timeDialogFragment.setArguments(bundle);
        timeDialogFragment.show(manager, "timeDialogFragment dialog");
    }

    private void onCreateFabPressed(){

        // Close the activity at last
        finish();
    }

    @Override
    public void setTexts(String minutes, String seconds) {

    }
}