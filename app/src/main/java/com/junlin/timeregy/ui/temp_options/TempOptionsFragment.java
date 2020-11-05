package com.junlin.timeregy.ui.temp_options;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junlin.timeregy.R;
import com.junlin.timeregy.adapters.TempOptionsListAdapter;
import com.junlin.timeregy.data.TempOption;
import com.junlin.timeregy.data.enums.Interruptions;
import com.junlin.timeregy.data.enums.Tags;

import java.util.ArrayList;

public class TempOptionsFragment extends Fragment {

    private ArrayList<TempOption> tempOptionArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_temp_options, container, false);

        tempOptionArrayList.add(new TempOption(R.string.title_pomodoro, R.string.desc_pomodoro, R.drawable.pomodoro, true, 25, 0, 5, 0, 10, Tags.STUDY.getValue(), Interruptions.KEEPRUNNING.getValue()));
        tempOptionArrayList.add(new TempOption(R.string.title_deep_focus, R.string.desc_deep_focus, R.drawable.deep_focus, false, 120, 0, 0, 0, 1, Tags.STUDY.getValue(), Interruptions.CANCELTIMER.getValue()));
        tempOptionArrayList.add(new TempOption(R.string.title_hiit, R.string.desc_hiit, R.drawable.hiit, true, 5, 30, 2, 0, 15, Tags.WORKOUT.getValue(), Interruptions.PAUSETIMER.getValue()));
        tempOptionArrayList.add(new TempOption(R.string.title_mindfulness, R.string.desc_mindfulness, R.drawable.mindfulness, false, 30, 0, 5, 0, 2, Tags.MINDFULNESS.getValue(), Interruptions.PAUSETIMER.getValue()));
        tempOptionArrayList.add(new TempOption(R.string.title_custom, R.string.desc_custom, R.drawable.custom, true, 10, 0, 5, 0, 10, Tags.NONE.getValue(), Interruptions.KEEPRUNNING.getValue()));

        RecyclerView recyclerView = root.findViewById(R.id.temp_options_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        TempOptionsListAdapter listAdapter = new TempOptionsListAdapter(tempOptionArrayList.size(), tempOptionArrayList);
        recyclerView.setAdapter(listAdapter);

        return root;
    }
}