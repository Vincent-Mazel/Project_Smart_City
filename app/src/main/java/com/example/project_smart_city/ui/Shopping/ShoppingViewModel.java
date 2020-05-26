package com.example.project_smart_city.ui.Shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShoppingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Search");
    }

    public LiveData<String> getText() {
        return mText;
    }
}