package com.junlin.timeregy.ui.temp_options;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junlin.timeregy.R;
import com.junlin.timeregy.adapters.TempOptionsListAdapter;
import com.junlin.timeregy.dataclasses.TempOption;

import java.util.ArrayList;

public class TempOptionsFragment extends Fragment {

    private ArrayList<TempOption> tempOptionArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_temp_options, container, false);

        tempOptionArrayList.add(new TempOption(R.string.title_pomodoro, R.string.desc_pomodoro, R.drawable.pomodoro, true));
        tempOptionArrayList.add(new TempOption(R.string.title_deep_focus, R.string.desc_deep_focus, R.drawable.deep_focus, false));
        tempOptionArrayList.add(new TempOption(R.string.title_hiit, R.string.desc_hiit, R.drawable.hiit, true));
        tempOptionArrayList.add(new TempOption(R.string.title_mindfulness, R.string.desc_mindfulness, R.drawable.mindfulness, false));
        tempOptionArrayList.add(new TempOption(R.string.title_custom, R.string.desc_custom, R.drawable.custom, true));

        RecyclerView recyclerView = root.findViewById(R.id.temp_options_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        TempOptionsListAdapter listAdapter = new TempOptionsListAdapter(tempOptionArrayList.size(), tempOptionArrayList);
        recyclerView.setAdapter(listAdapter);

        return root;
    }
}