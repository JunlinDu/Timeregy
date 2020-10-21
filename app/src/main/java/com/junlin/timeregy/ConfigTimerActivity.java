package com.junlin.timeregy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.data.enums.Interruptions;
import com.junlin.timeregy.ui.dialogs.TimeDialogFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    Interruptions currentInterruptionsMode = Interruptions.KEEPRUNNING;
    ArrayList<ConstraintLayout> interruptionsCards = new ArrayList<>();
    ArrayList<ImageView> intCardImages = new ArrayList<>();

    TextInputEditText userRemarks;
    FloatingActionButton createFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_timer);

        // getting a database instance
        tDb = TimeregyDatabase.getAppDatabase(getApplicationContext());

        // stop soft keyboard from showing automatically when focus is changed
        // reference: https://stackoverflow.com/questions/5221622/how-to-stop-soft-keyboard-showing-automatically-when-focus-is-changed-onstart-e/5484683
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // initiates all views
        createViews();
    }

    private void createViews() {
        workCard = findViewById(R.id.work_time_constraint);
        restCard = findViewById(R.id.interval_time_constraint);
        intervalCheckBox = findViewById(R.id.interval_checkbox);
        timerNameInput = findViewById(R.id.text_input_edit);
        roundCard = findViewById(R.id.round_constraint);
        userRemarks = findViewById(R.id.user_remarks_input);
        createFab = findViewById(R.id.fab);
        interruptionsCards.add((ConstraintLayout) findViewById(R.id.keeps_running_card));
        interruptionsCards.add((ConstraintLayout) findViewById(R.id.pause_card));
        interruptionsCards.add((ConstraintLayout) findViewById(R.id.cancel_card));
        intCardImages.add((ImageView) findViewById(R.id.keeps_running_image));
        intCardImages.add((ImageView) findViewById(R.id.pause_timer_image));
        intCardImages.add((ImageView) findViewById(R.id.cancel_timer_image));
        for (int i = 0; i < 3; i++) {
            setInterruptionCardListener(interruptionsCards.get(i), i);
            if (i != 0) intCardImages.get(i).setVisibility(View.INVISIBLE);
        }

        // Getting the selected index of a RadioGroup in Android:
        // https://stackoverflow.com/questions/6440259/how-to-get-the-selected-index-of-a-radiogroup-in-android
        tags = findViewById(R.id.tags_group);
        workCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSetWorkTimeDialog(1,1,1,0);
            }
        });
        restCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalCheckBox.isChecked()) {
                    openSetWorkTimeDialog(1,2,3,1);
                }
            }
        });
    }

    private void setInterruptionCardListener(final ConstraintLayout layout, final int id) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInterruptionsMode = Interruptions.toInteruptions(id);
                layout.setBackground(
                        ContextCompat.getDrawable(ConfigTimerActivity.this, R.drawable.card_layout_square_selected));
                intCardImages.get(id).setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i ++) {
                    if (i != id) {
                        interruptionsCards
                                .get(i)
                                .setBackground(ContextCompat.getDrawable(ConfigTimerActivity.this, R.drawable.card_layout_square));
                        intCardImages.get(i).setVisibility(View.INVISIBLE);
                    }
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