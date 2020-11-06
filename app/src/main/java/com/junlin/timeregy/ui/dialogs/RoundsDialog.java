package com.junlin.timeregy.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.junlin.timeregy.R;

public class RoundsDialog extends AppCompatDialogFragment {

    NumberPicker roundsPicker;
    Bundle bundle;
    private RoundsDialogFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity(), R.style.Dialog);

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_rounds_dialog, null);

        roundsPicker = view.findViewById(R.id.rounds_picker);
        roundsPicker.setMinValue(0);
        roundsPicker.setMaxValue(20);
        assert bundle!= null;
        roundsPicker.setValue(bundle.getInt("rounds"));

        builder.setView(view)
                .setTitle("Rounds")
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.setRoundTexts(roundsPicker.getValue());
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (RoundsDialog.RoundsDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement " + context.toString());
        }
    }

    public interface RoundsDialogFragmentListener{
        void setRoundTexts(int rounds);
    }
}
