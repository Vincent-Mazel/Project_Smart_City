package com.example.project_smart_city.ui.NetworkFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetworkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NetworkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Réseaux Sociaux");
    }

    public LiveData<String> getText() {
        return mText;
    }
}