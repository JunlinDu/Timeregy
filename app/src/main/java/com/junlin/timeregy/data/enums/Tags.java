package com.junlin.timeregy.data.enums;

import android.widget.ImageView;
import android.widget.TextView;

import com.junlin.timeregy.R;

import java.util.HashMap;

public enum Tags {
    NONE(0),
    STUDY(1),
    WORKOUT(2),
    MINDFULNESS(3),
    UNKNOWN(-1);

    private final Integer value;

    public static final HashMap<Integer, Tags> intToTags = new HashMap<>();
    static {
        for (Tags tags : Tags.values()) {
            intToTags.put(tags.value, tags);
        }
    }

    public static Tags toTag(Integer num) {
        Tags tags = intToTags.get(num);
        return tags == null ? UNKNOWN : tags;
    }

    Tags(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static void setTag(Tags tag, ImageView icon, TextView text) {
        switch (tag) {
            case STUDY:
                text.setText(R.string.tag_study);
                icon.setImageResource(R.drawable.tag_study);
                break;
            case WORKOUT:
                text.setText(R.string.tag_workout);
                icon.setImageResource(R.drawable.tag_workout);
                break;
            case MINDFULNESS:
                text.setText(R.string.tag_mindfulness);
                icon.setImageResource(R.drawable.tag_mindfulness);
                break;
        }
    }
}
