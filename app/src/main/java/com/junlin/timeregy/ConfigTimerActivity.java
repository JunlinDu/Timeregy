package com.junlin.timeregy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.enums.Interruptions;
import com.junlin.timeregy.data.enums.Tags;
import com.junlin.timeregy.data.TempOption;
import com.junlin.timeregy.data.utility.TimeConverter;
import com.junlin.timeregy.ui.dialogs.RoundsDialog;
import com.junlin.timeregy.ui.dialogs.TimeDialogFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ConfigTimerActivity extends AppCompatActivity implements TimeDialogFragment.TimerDialogFragmentListener, RoundsDialog.RoundsDialogFragmentListener {

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
    TextView durationText;

    RadioGroup tags;

    Interruptions currentInterruptionsMode = Interruptions.KEEPRUNNING;
    ArrayList<ConstraintLayout> interruptionsCards = new ArrayList<>();
    ArrayList<ImageView> intCardImages = new ArrayList<>();

    TextInputEditText userRemarks;
    FloatingActionButton createFab;

    TimerTemplate timerTemplate;
    FragmentManager manager;
    int opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_timer);
        manager = getSupportFragmentManager();
        // getting a database instance
        tDb = TimeregyDatabase.getAppDatabase(getApplicationContext());

        Intent intent = getIntent();
        timerTemplate = (TimerTemplate) intent.getParcelableExtra("Data");
        opt = intent.getIntExtra("Option", 0);

        // stop soft keyboard from showing automatically when focus is changed
        // reference: https://stackoverflow.com/questions/5221622/how-to-stop-soft-keyboard-showing-automatically-when-focus-is-changed-onstart-e/5484683
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // initiates all views
        createViews();
    }

    private void createViews() {
        timerNameInput = findViewById(R.id.text_input_edit);
        intervalCheckBox = findViewById(R.id.interval_checkbox);
        workTimeMinText = findViewById(R.id.work_time_minute_display);
        workTimeSecText = findViewById(R.id.work_time_second_dis);
        restTimeMinText = findViewById(R.id.rest_time_minute_display);
        restTimeSecText = findViewById(R.id.rest_time_second_dis);
        roundsText = findViewById(R.id.total_rounds);
        durationText = findViewById(R.id.duration);
        workCard = findViewById(R.id.work_time_constraint);
        restCard = findViewById(R.id.interval_time_constraint);
        roundCard = findViewById(R.id.round_constraint);
        tags = findViewById(R.id.tags_group);
        userRemarks = findViewById(R.id.user_remarks_input);
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


        if (timerTemplate != null) {
            timerNameInput.setText(timerTemplate.name);

            if (timerTemplate.interval) intervalCheckBox.setChecked(true);
            int workMin = TimeConverter.intToMin(timerTemplate.workTimeInSec);
            int workSec = TimeConverter.toRestSecond(timerTemplate.workTimeInSec);
            int restMin = TimeConverter.intToMin(timerTemplate.restTimeInSec);
            int restSec = TimeConverter.toRestSecond(timerTemplate.restTimeInSec);
            int duration = TimeConverter.toDuration(workMin, restMin, workSec, restSec, timerTemplate.rounds);

            workTimeMinText.setText(String.valueOf(workMin));
            workTimeSecText.setText(String.valueOf(workSec));

            restTimeMinText.setText(String.valueOf(restMin));
            restTimeSecText.setText(String.valueOf(restSec));

            roundsText.setText(String.valueOf(timerTemplate.rounds));
            durationText.setText(String.valueOf(duration));
            RadioButton rb = (RadioButton) tags.getChildAt(timerTemplate.tag);
            rb.setChecked(true);
            setInterrupationCard(interruptionsCards.get(timerTemplate.interruptions), timerTemplate.interruptions);
        }

        createFab = findViewById(R.id.fab);

        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateFabPressed();
            }
        });

        workCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int[] time = convertTime(workTimeMinText.getText().toString(), workTimeSecText.getText().toString());
                openSetWorkTimeDialog(time[0], time[1], time[2], 0);
            }
        });

        restCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalCheckBox.isChecked()) {
                    int[] time = convertTime(restTimeMinText.getText().toString(), restTimeSecText.getText().toString());
                    openSetWorkTimeDialog(time[0], time[1], time[2], 1);
                }
            }
        });

        intervalCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTimeMinText.setText("0");
                restTimeMinText.setText("0");
            }
        });

        roundCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoundsDialog roundsDialog = new RoundsDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("rounds", Integer.parseInt(roundsText.getText().toString()));
                roundsDialog.setArguments(bundle);
                roundsDialog.show(manager, "roundsDialog");
            }
        });
    }

    private int[] convertTime(String minutes, String seconds) {
        int[] timeArray = new int[3];
        int hrs = Integer.parseInt(minutes) / 60;
        int mins = Integer.parseInt(minutes) - hrs * 60;
        int secs = Integer.parseInt(seconds);
        timeArray[0] = hrs;
        timeArray[1] = mins;
        timeArray[2] = secs;
        return timeArray;
    }

    private void setInterruptionCardListener(final ConstraintLayout layout, final int id) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInterrupationCard(layout, id);
            }
        });
    }

    private void setInterrupationCard(ConstraintLayout layout, int id) {
        layout.setBackground(
                ContextCompat.getDrawable(ConfigTimerActivity.this, R.drawable.card_layout_square_selected));
        intCardImages.get(id).setVisibility(View.VISIBLE);
        currentInterruptionsMode = Interruptions.toInteruptions(id);
//        Log.i(TAG, currentInterruptionsMode.toString());
        for (int i = 0; i < 3; i ++) {
            if (i != id) {
                interruptionsCards
                        .get(i)
                        .setBackground(ContextCompat.getDrawable(ConfigTimerActivity.this, R.drawable.card_layout_square));
                intCardImages.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void openSetWorkTimeDialog(int hours, int minutes, int seconds, int id){
        TimeDialogFragment timeDialogFragment = new TimeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("hours", hours);
        bundle.putInt("minutes", minutes);
        bundle.putInt("seconds", seconds);
        bundle.putInt("id", id);
        timeDialogFragment.setArguments(bundle);
        timeDialogFragment.show(manager, "timeDialogFragment dialog");
    }

    private void onCreateFabPressed(){
        boolean test = true;
        if (Objects.requireNonNull(timerNameInput.getText()).toString().equals("")) {
            timerNameInput.setError(getResources().getString(R.string.empty_string_error));
            test = false;
        }

        if (timerNameInput.getText().toString().length() > 20) {
            timerNameInput.setError(getResources().getString(R.string.empty_string_error));
            timerNameInput.setError(getResources().getString(R.string.string_too_long_error));
            test = false;
        }

        if (test) {
            createRecord();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("back", 1);
            // Going two activities back without creating new activity instance on the stack,
            // Reference: https://stackoverflow.com/questions/6722109/going-two-activities-back
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            this.startActivity(i);
            // Close the activity at last
            finish();
        }
    }

    private void createRecord() {

        // UserId id set to 1 temporarily

        int workSeconds = TimeConverter.stringToSeconds(workTimeMinText.getText().toString(), workTimeSecText.getText().toString());
        int restSeconds = TimeConverter.stringToSeconds(restTimeMinText.getText().toString(), restTimeSecText.getText().toString());

        // Reference: https://stackoverflow.com/questions/6440259/how-to-get-the-selected-index-of-a-radiogroup-in-android
        int rId= tags.getCheckedRadioButtonId();
        View radioButton = tags.findViewById(rId);
        Tags tag = Tags.toTag(tags.indexOfChild(radioButton));
        Date date = new Date();
        setTimerTemplate(this.timerTemplate, workSeconds, restSeconds, tag, date);

        // This is referenced from https://www.youtube.com/watch?time_continue=223&v=c43ruIIZAMg&feature=emb_logo
        // this opens a diskIO thread for the database to
        ThreadExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (opt == 0) tDb.timerTemplateDAO().inserTemplate(timerTemplate);
                else tDb.timerTemplateDAO().updateTemplate(timerTemplate);
            }
        });
    }

    private TimerTemplate setTimerTemplate(TimerTemplate template, int workSeconds, int restSeconds, Tags tags, Date date) {
        template.name = Objects.requireNonNull(timerNameInput.getText()).toString();
        template.interval = intervalCheckBox.isChecked();
        template.workTimeInSec = workSeconds;
        template.restTimeInSec = restSeconds;
        template.rounds = Integer.parseInt(roundsText.getText().toString());
        template.tag = tags.getValue();
        template.interruptions = currentInterruptionsMode.getValue();
        template.remark = Objects.requireNonNull(userRemarks.getText()).toString();
        template.dateCreated = date;
        return template;
    }

    private void setDuration() {
        durationText.setText(
                TimeConverter.stringToDurationString(workTimeMinText.getText().toString(),
                        restTimeMinText.getText().toString(),
                        workTimeSecText.getText().toString(),
                        restTimeSecText.getText().toString(),
                        roundsText.getText().toString()));
    }

    @Override
    public void setTexts(String minutes, String seconds, int id) {
        if (id == 0) {
            workTimeMinText.setText(minutes);
            workTimeSecText.setText(seconds);
        } else {
            restTimeMinText.setText(minutes);
            restTimeSecText.setText(seconds);
        }
        setDuration();
    }

    @Override
    public void setRoundTexts(int rounds) {
        roundsText.setText(String.valueOf(rounds));
        setDuration();
    }
}