package com.junlin.timeregy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.junlin.timeregy.ConfigTimerActivity;
import com.junlin.timeregy.R;
import com.junlin.timeregy.data.TempOption;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.utility.TimeConverter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TempOptionsListAdapter extends RecyclerView.Adapter<TempOptionsListAdapter.ViewHolder>{

    private int itemCount;
    private ArrayList<TempOption> tempOptionArrayList;

    public TempOptionsListAdapter(int itemCount, ArrayList<TempOption> arrayList) {
        this.itemCount = itemCount;
        this.tempOptionArrayList = arrayList;
    }

    @NonNull
    @Override
    public TempOptionsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.model_option_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempOptionsListAdapter.ViewHolder holder, int position) {
        TempOption tempOption = tempOptionArrayList.get(position);
        holder.bind(tempOption);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView descText;
        TextView sideNoteText;
        CircleImageView profileImage;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.temp_option_title);
            descText = itemView.findViewById(R.id.temp_option_desc);
            sideNoteText = itemView.findViewById(R.id.temp_option_sidenote);
            profileImage = itemView.findViewById(R.id.temp_option_profile);
            parentLayout = itemView.findViewById(R.id.option);

        }

        void bind(final TempOption tempOption) {
            titleText.setText(tempOption.titleRes);
            descText.setText(tempOption.descRes);
            if (tempOption.Interval) sideNoteText.setText(R.string.interval);
            else sideNoteText.setText(R.string.non_interval);
            profileImage.setImageResource(tempOption.imgRes);
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ConfigTimerActivity.class);
                    TimerTemplate timerTemplate = new TimerTemplate(
                            1, context.getResources().getString(tempOption.titleRes), tempOption.Interval,
                            TimeConverter.intToSeconds(tempOption.workMin, tempOption.workSec),
                            TimeConverter.intToSeconds(tempOption.restMin, tempOption.resSec),
                            tempOption.rounds, tempOption.tag, tempOption.interruptions, null, new Date());
                    intent.putExtra("Data", timerTemplate);
                    intent.putExtra("Option", 0);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}
