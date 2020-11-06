package com.junlin.timeregy.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TintTypedArray;
import androidx.recyclerview.widget.RecyclerView;

import com.junlin.timeregy.ConfigTimerActivity;
import com.junlin.timeregy.R;
import com.junlin.timeregy.TimerActivity;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.enums.Tags;

import java.util.List;

public class UserTempListAdapter extends RecyclerView.Adapter<UserTempListAdapter.ViewHolder> {

    public static final String TAG = UserTempListAdapter.class.getSimpleName();


    private List<TimerTemplate> timerTemplates;

    @NonNull
    @Override
    public UserTempListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.model_template_item, parent, false);
        return new UserTempListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTempListAdapter.ViewHolder holder, int position) {
        TimerTemplate template = timerTemplates.get(position);
        holder.bind(template);
    }

    public void updateTemplates(List<TimerTemplate> timerTemplates) {
        this.timerTemplates = timerTemplates;
        notifyDataSetChanged();
    }

    public TimerTemplate getTemplate(int position) {
        return this.timerTemplates.get(position);
    }

    @Override
    public int getItemCount() {
        return this.timerTemplates == null? 0:this.timerTemplates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView title;
        TextView tag;
        ImageView tagIcon;
        TextView round;
        Button startButton;
        Button editButton;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profileImage = itemView.findViewById(R.id.temp_profile);
            this.title = itemView.findViewById(R.id.temp_title);
            this.tag = itemView.findViewById(R.id.tag_text);
            this.tagIcon = itemView.findViewById(R.id.tag_icon);
            this.round = itemView.findViewById(R.id.minutes_text);
            this.startButton = itemView.findViewById(R.id.temp_start_button);
            this.image = itemView.findViewById(R.id.template_tag_image);
            this.editButton = itemView.findViewById(R.id.edit_button);
        }

        void bind(final TimerTemplate timerTemplate) {
            profileImage.setImageResource(R.drawable.profile);
            title.setText(timerTemplate.name);
            int tagValue = timerTemplate.tag;
            Tags.setTag(Tags.toTag(tagValue), tagIcon, tag);

            // This is set up temporarily so that image will be displayed for each item
            switch (tagValue) {
                case 1:
                    image.setImageResource(R.drawable.study_img);
                    break;
                case 2:
                    image.setImageResource(R.drawable.workout_img);
                    break;
                case 3:
                    image.setImageResource(R.drawable.mindfulness_img);
                    break;
            }

            String roundMin = calculateMinute(timerTemplate.workTimeInSec, timerTemplate.restTimeInSec, timerTemplate.rounds) + " mins";
            round.setText(roundMin);
            // rounds have not been setup
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), TimerActivity.class);
                    intent.putExtra("Data", timerTemplate);
                    itemView.getContext().startActivity(intent);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ConfigTimerActivity.class);
                    intent.putExtra("Data", timerTemplate);
                    intent.putExtra("Option", 1);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        int calculateMinute(int workSec, int restSec, int round) {
            return (workSec + restSec) * round / 60;
        }
    }
}
