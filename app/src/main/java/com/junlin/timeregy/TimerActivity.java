package com.junlin.timeregy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.data.entity.Logs;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.enums.Interruptions;
import com.junlin.timeregy.data.enums.Tags;
import com.junlin.timeregy.data.enums.TimerState;
import com.junlin.timeregy.ui.dialogs.AlertDialog;

import java.util.Date;

public class TimerActivity extends AppCompatActivity implements AlertDialog.AlertDialogFragmentListener{

    public static final String TAG = TimerActivity.class.getSimpleName();

    private TextView countdownText;
    private TextView timerName;
    private TextView tag;
    private ImageView tagIcon;
    private TextView workOrRest;
    private ProgressBar wholeProgress;
    private Button startPauseButton;
    private TextView roundsDone;
    private TextView roundsLeft;
    private ImageView workRestIcon;

    private TimerTemplate template;

    private CountDownTimer countDownTimer;
    private TimerState currentState = TimerState.NOTSTARTED;

    private Long worktime;
    private Long resttime;
    private Long totalTime;
    private int roundsDoneCounter;
    private int roundsLeftCounter;
    private Tags timerTag;
    private Interruptions timerInterruptions;
    private TimeregyDatabase tDb;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        template = getIntent().getParcelableExtra("Data");

        countdownText = findViewById(R.id.timer_text);
        timerName = findViewById(R.id.timer_name);
        tag = findViewById(R.id.tag_name);
        tagIcon = findViewById(R.id.tag_img);
        workOrRest = findViewById(R.id.work_rest);
        wholeProgress = findViewById(R.id.whole_progress);
        startPauseButton = findViewById(R.id.start_pause_button);
        roundsDone = findViewById(R.id.rounds_done);
        roundsLeft = findViewById(R.id.rounds_left);
        workRestIcon = findViewById(R.id.work_rest_icon);
        wholeProgress = findViewById(R.id.whole_progress);
        tDb = TimeregyDatabase.getAppDatabase(getApplicationContext());
        manager = getSupportFragmentManager();
        setup();

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState == TimerState.NOTSTARTED) {
                    startPauseButton.setText(R.string.cancel_button);
                    startTimer();
                    setWorkRest();
                } else {
                    // TODO Opens a Dialog
                }
            }
        });
    }

    private void setup() {
        this.timerName.setText(template.name);
        Tags.setTag(Tags.toTag(template.tag), tagIcon, tag);
        setTime();

    }

    private void setTime() {
        worktime = (long) (template.workTimeInSec * 1000);
        resttime = (long) (template.restTimeInSec * 1000);
        this.roundsLeftCounter = template.rounds;
        totalTime = (worktime + resttime) * this.roundsLeftCounter;
        roundsDoneCounter = 0;
        updateIntervalProgress(0);
        updateRounds();
    }

    private void startTimer() {
        final long interval = 1000;
        countDownTimer = new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, String.valueOf(millisUntilFinished));
                updateIntervalProgress(interval);
                updateTotalProgress(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                currentState = TimerState.NOTSTARTED;
                updateIntervalProgress(0);
                final Logs logs = new Logs(1, timerName.getText().toString(), new Date());

                ThreadExecutor.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        tDb.logsDAO().insertLog(logs);
                    }
                });
                AlertDialog alertDialog = new AlertDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("message", R.string.count_down_finish_message);
                alertDialog.setArguments(bundle);
                alertDialog.show(manager, "Timer Complete Dialog");
                setup();
            }
        }.start();
        currentState = TimerState.WORKING;
    }

    private void updateIntervalProgress(long interval) {
        switch (currentState) {
            case RESTING:
                this.resttime -= interval;
                updateCounterText(this.resttime);
                if (this.resttime == 0) {
                    this.resttime = (long) template.restTimeInSec * 1000;
                    this.roundsDoneCounter ++;
                    this.roundsLeftCounter --;
                    updateRounds();
                    currentState = TimerState.WORKING;
                    setWorkRest();
                }
                break;
            case WORKING:
                this.worktime -= interval;
                updateCounterText(this.worktime);
                if (this.worktime == 0) {
                    this.worktime = (long) template.workTimeInSec * 1000;
                    currentState = TimerState.RESTING;
                    setWorkRest();
                }
                break;
            case NOTSTARTED:
                updateCounterText(this.worktime);
                break;
        }
    }

    private void roundProgress() {
    }

    private void setWorkRest () {
        if (currentState == TimerState.RESTING) {
            workOrRest.setText(R.string.resting);
            workRestIcon.setImageResource(R.drawable.resting);
        } else if (currentState == TimerState.WORKING) {
            workOrRest.setText(R.string.working);
            workRestIcon.setImageResource(R.drawable.working);
        }
    }

    private void updateRounds() {
        roundsDone.setText(String.valueOf(roundsDoneCounter));
        roundsLeft.setText(String.valueOf(roundsLeftCounter));
    }

    private void updateCounterText(long time) {
        long min = time / 60000;
        long sec = time % 60000 / 1000;
        StringBuilder builder = new StringBuilder();
        builder.append("");
        if (min < 10) builder.append(0);
        builder.append(min).append(" : ");
        if (sec < 10) builder.append(0);
        builder.append(sec);
        countdownText.setText(builder.toString());
    }

    private void updateTotalProgress(long timeLeftInMillSec) {
        double timeLeft = (double) timeLeftInMillSec;
        double total = (double) this.totalTime;

        wholeProgress.setProgress(100 - (int)((timeLeft/total) * 100));
    }

    @Override
    public void onBackPressed() {
        if (currentState == TimerState.WORKING || currentState == TimerState.RESTING) {
            // opens a dialog
        }
        super.onBackPressed();
    }

    @Override
    public void execute() {

    }
}