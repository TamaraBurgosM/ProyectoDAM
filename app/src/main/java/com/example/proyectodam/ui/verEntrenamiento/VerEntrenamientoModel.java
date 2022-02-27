package com.example.proyectodam.ui.verEntrenamiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VerEntrenamientoModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VerEntrenamientoModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
