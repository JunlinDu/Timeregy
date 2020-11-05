package com.junlin.timeregy.ui.home;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junlin.timeregy.R;
import com.junlin.timeregy.ThreadExecutor;
import com.junlin.timeregy.adapters.UserTempListAdapter;
import com.junlin.timeregy.data.TimeregyDatabase;
import com.junlin.timeregy.data.entity.TimerTemplate;

import java.util.List;
import java.util.Objects;

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

        // Swipe a list item to left to delete the item from the database and from the screen ---DELETE
        // Reference: https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ItemTouchHelper.SimpleCallback
        // and https://www.youtube.com/watch?v=0xbyIuwbyTs&feature=emb_logo
        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                database.timerTemplateDAO().deleteTemplate(tempListAdapter.getTemplate(viewHolder.getAdapterPosition()));
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
        });

         mIth.attachToRecyclerView(recyclerView);
         loadAllTemplates();
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

    // Loads all templates from the database to the screen.
    // LiveData is used in here because it observes changes on the database
    // (observer pattern) and calls the onChanged callback methods every time
    // changes are bring observed.
    // Reference: https://www.youtube.com/watch?v=d--4zlipdxU&feature=emb_logo
    private void loadAllTemplates() {
        final LiveData<List<TimerTemplate>> templates = database.timerTemplateDAO().retrieveAllTimerTemplates();
        templates.observe(getViewLifecycleOwner(), new Observer<List<TimerTemplate>>() {
            @Override
            public void onChanged(List<TimerTemplate> timerTemplates) {
                tempListAdapter.updateTemplates(timerTemplates);
            }
        });
    }
}