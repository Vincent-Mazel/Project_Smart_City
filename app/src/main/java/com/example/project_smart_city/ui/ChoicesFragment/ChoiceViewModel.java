package com.example.project_smart_city.ui.ChoicesFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChoiceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChoiceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Choix");

    }

    public LiveData<String> getText() {
        return mText;
    }
}