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
import com.junlin.timeregy.data.utility.TimeConverter;

public class TimeDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = TimeDialogFragment.class.getSimpleName();
    NumberPicker hoursPicker;
    NumberPicker minutesPicker;
    NumberPicker secondsPicker;

    private TimerDialogFragmentListener listener;

    Bundle bundle;
    int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        assert bundle != null;
        this.id = bundle.getInt("id");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog);

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // inflate(): the first parameter is the layout resource ID and the second parameter is a parent view for the layout.
        // reference: https://developer.android.com/guide/topics/ui/dialogs
        View view = inflater.inflate(R.layout.fragment_time_dialog, null);

        hoursPicker = view.findViewById(R.id.hours_picker);
        setNumberPicker(hoursPicker, 0, 3, TimerPicker.HOURS);
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
                        String minutes = TimeConverter.calculateTotalTimeToString(hoursPicker.getValue(), minutesPicker.getValue(), secondsPicker.getValue());
                        String seconds;
                        if (TimeConverter.calculateSec(secondsPicker.getValue()) == 0) seconds = String.valueOf(secondsPicker.getValue());
                        else seconds = "0";
                        listener.setTexts(minutes, seconds, id);
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
                picker.setValue(bundle.getInt("seconds"));
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
                picker.setValue(bundle.getInt("minutes"));
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (secondsPicker.getValue() + newVal + hoursPicker.getValue() == 0) secondsPicker.setValue(30);
                    }
                });
                break;
            case HOURS:
                picker.setValue(bundle.getInt("hours"));
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (secondsPicker.getValue() + minutesPicker.getValue() + newVal == 0) secondsPicker.setValue(30);
                    }
                });
                break;
        }
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
        void setTexts(String minutes, String seconds, int id);
    }
}