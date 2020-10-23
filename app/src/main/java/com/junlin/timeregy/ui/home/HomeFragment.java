package com.junlin.timeregy.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junlin.timeregy.R;
import com.junlin.timeregy.ThreadExecutor;
import com.junlin.timeregy.adapters.UserTempListAdapter;
import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.data.entity.TimerTemplate;

import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private TimeregyDatabase database;
    private NavController navController;
    private UserTempListAdapter tempListAdapter;


    FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Generate a database instance
        database = TimeregyDatabase.getAppDatabase(getActivity());

        // Setting up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.templates_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        tempListAdapter = new UserTempListAdapter();
        recyclerView.setAdapter(tempListAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        fab = view.findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav_home_to_temp_options);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Resumed");
        loadAllTemplates();
    }

    // This is learned from https://www.youtube.com/watch?time_continue=240&v=c43ruIIZAMg&feature=emb_logo
    // Opens a Disk IO thread to load all templates from the database and then in the UI Thread update each
    // of them to each of the corresponding items in the RecyclerView list.
    private void loadAllTemplates() {
        ThreadExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                // This has to be final so that the variable can be accessed form inside the UI thread
                final List<TimerTemplate> templates = database.timerTemplateDAO().retrieveAllTimerTemplates();
                // Reference: https://stackoverflow.com/questions/16425146/runonuithread-in-fragment
                //
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempListAdapter.insertTemplates(templates);
                    }
                });
            }
        });

    }

    public void setUpList() {
        String a  = database.timerTemplateDAO().retrieveAllTimerTemplates().get(0).name;
        Log.e(TAG, a);
    }
}