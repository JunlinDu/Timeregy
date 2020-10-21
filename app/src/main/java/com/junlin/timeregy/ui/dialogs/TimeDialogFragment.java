package com.junlin.timeregy.ui.dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import com.junlin.timeregy.R;
import com.junlin.timeregy.data.enums.TimerPicker;

public class TimeDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = TimeDialogFragment.class.getSimpleName();
    NumberPicker hoursPicker;
    NumberPicker minutesPicker;
    NumberPicker secondsPicker;

    private TimerDialogFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        assert bundle != null;
        int a = bundle.getInt("seconds");
        Log.e(TAG, String.valueOf(a));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog);

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // inflate(): the first parameter is the layout resource ID and the second parameter is a parent view for the layout.
        // reference: https://developer.android.com/guide/topics/ui/dialogs
        View view = inflater.inflate(R.layout.fragment_time_dialog, null);

        hoursPicker = view.findViewById(R.id.hours_picker);
        setNumberPicker(hoursPicker, 0, 12, TimerPicker.HOURS);
        minutesPicker = view.findViewById(R.id.minutes_picker);
        setNumberPicker(minutesPicker, 0, 59, TimerPicker.MINUTES);
        secondsPicker = view.findViewById(R.id.seconds_picker);
        setNumberPicker(secondsPicker, 0, 60, TimerPicker.SECONDS);
        hoursPicker.getClass();

        builder.setView(view)
                .setTitle("Time")
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String minutes = calculateTotalTime(hoursPicker.getValue(), minutesPicker.getValue(), secondsPicker.getValue());
                        String seconds;
                        if (calculateSec(secondsPicker.getValue()) == 0) seconds = String.valueOf(secondsPicker.getValue());
                        else seconds = "0";
                        listener.setTexts(minutes, seconds);
                    }
                })
                .setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_dialog, container, false);
    }

    /**
     * Setting up the number picker */
    private void setNumberPicker(NumberPicker picker, final int min, final int max, final TimerPicker tp) {
        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        switch (tp) {
            case SECONDS:
                picker.setValue(15);
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (newVal + minutesPicker.getValue() + hoursPicker.getValue() == 0) picker.setValue(30);
                        // Setting step size of the NumberPicker
                        // https://stackoverflow.com/questions/12979643/change-the-step-size-of-a-numberpicker
                        picker.setValue((newVal < oldVal)?oldVal-15:oldVal+15);
                    }
                });
                break;
            case MINUTES:
                picker.setValue(5);
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (secondsPicker.getValue() + newVal + hoursPicker.getValue() == 0) secondsPicker.setValue(30);
                    }
                });
                break;
            case HOURS:
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (secondsPicker.getValue() + minutesPicker.getValue() + newVal == 0) secondsPicker.setValue(30);
                    }
                });
                break;
        }
    }

    private String calculateTotalTime(int hours, int minutes, int seconds) {
        return String.valueOf(hours * 60 + minutes + calculateSec(seconds));
    }

    private int calculateSec(int seconds) {
        return seconds == 60? 1:0;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (TimerDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement " + context.toString());
        }

    }

    public interface TimerDialogFragmentListener{
        void setTexts(String minutes, String seconds);
    }
}