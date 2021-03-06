package com.junlin.timeregy.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.junlin.timeregy.R;

public class AlertDialog extends AppCompatDialogFragment {

    private AlertDialog.AlertDialogFragmentListener listener;
    Bundle bundle;

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
        View view = inflater.inflate(R.layout.fragment_alert_dialog, null);
        TextView message = view.findViewById(R.id.alert_Text);
        assert bundle!= null;
        message.setText(bundle.getInt("message"));

        builder.setView(view)
                .setTitle("Message")
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AlertDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement " + context.toString());
        }

    }

    public interface AlertDialogFragmentListener {
        void execute();
    }
}
