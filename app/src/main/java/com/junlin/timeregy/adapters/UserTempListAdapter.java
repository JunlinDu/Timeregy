package com.junlin.timeregy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.junlin.timeregy.R;
import com.junlin.timeregy.data.entity.TimerTemplate;
import com.junlin.timeregy.data.enums.Tags;

import java.util.List;

public class UserTempListAdapter extends RecyclerView.Adapter<UserTempListAdapter.ViewHolder> {

    public static final String TAG = UserTempListAdapter.class.getSimpleName();
    public static final String[] TAGSARRAY = new String[]{"None", "Study", "Workout", "Mindfulness"};

    private List<TimerTemplate> timerTemplates;

    public UserTempListAdapter() {
    }

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

    public void insertTemplates(List<TimerTemplate> timerTemplates) {
        this.timerTemplates = timerTemplates;
        notifyItemRangeInserted(0, this.timerTemplates.size());
    }

    public void deleteSingleTemplate(List<TimerTemplate> timerTemplates, int position) {
        this.timerTemplates = timerTemplates;
        notifyItemRemoved(position);
    }

    public void updateSingleTemplate(TimerTemplate template, int position) {
        this.timerTemplates.set(position, template);
        notifyItemChanged(position);
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
        Button startButton;
        Button viewButton;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profileImage = itemView.findViewById(R.id.temp_profile);
            this.title = itemView.findViewById(R.id.temp_title);
            this.tag = itemView.findViewById(R.id.tag_text);
            this.tagIcon = itemView.findViewById(R.id.tag_icon);
            this.startButton = itemView.findViewById(R.id.temp_start_button);
            this.viewButton = itemView.findViewById(R.id.temp_view_button);
            this.image = itemView.findViewById(R.id.template_tag_image);
        }

        void bind(TimerTemplate timerTemplate) {
            profileImage.setImageResource(R.drawable.profile);
            title.setText(timerTemplate.name);
            int tagValue = timerTemplate.tag.getValue();
            tag.setText(TAGSARRAY[tagValue]);
            switch (tagValue) {
                case 1:
                    tagIcon.setImageResource(R.drawable.tag_study);
                    image.setImageResource(R.drawable.study_img);
                    break;
                case 2:
                    tagIcon.setImageResource(R.drawable.tag_workout);
                    image.setImageResource(R.drawable.workout_img);
                    break;
                case 3:
                    tagIcon.setImageResource(R.drawable.tag_mindfulness);
                    image.setImageResource(R.drawable.mindfulness_img);
                    break;
            }

        }
    }
}
