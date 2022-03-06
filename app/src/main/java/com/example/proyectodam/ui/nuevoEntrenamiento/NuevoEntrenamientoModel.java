package com.example.proyectodam.ui.nuevoEntrenamiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NuevoEntrenamientoModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NuevoEntrenamientoModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {

        return mText;
    }
}