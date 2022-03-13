package com.example.proyectodam.ui.nuevoEjercicio;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class NuevoEjercicioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NuevoEjercicioViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}