package com.example.proyectodam.ui.nuevoEjercicio;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectodam.NuevoEjercicioActivity;

public class NuevoEjercicioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NuevoEjercicioViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is gallery fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}