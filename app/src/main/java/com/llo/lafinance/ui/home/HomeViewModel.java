package com.llo.lafinance.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        LocalDateTime localDateTime = LocalDateTime.now();

        if (localDateTime.getHour() >= 0 && localDateTime.getHour() <= 11) {
            mText.setValue("Bom dia, Leonardo!");
        } else if (localDateTime.getHour() > 11 && localDateTime.getHour() < 18) {
            mText.setValue("Boa tarde, Leonardo!");
        } else if (localDateTime.getHour() >= 18) {
            mText.setValue("Boa noite, Leonardo!");
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}