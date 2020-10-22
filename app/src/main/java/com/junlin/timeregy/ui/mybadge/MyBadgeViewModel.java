package com.junlin.timeregy.ui.mybadge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyBadgeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyBadgeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is My Badge fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}