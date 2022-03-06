package com.example.proyectodam.ui.nuevoEntrenamiento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectodam.AddEntenamiento;
import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.ListaAdaptador;
import com.example.proyectodam.MainActivity;
import com.example.proyectodam.Model.Ejercicio;
import com.example.proyectodam.NuevoEjercicioActivity;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentNuevoejercicioBinding;
import com.example.proyectodam.databinding.FragmentNuevoentrenamientoBinding;

import java.util.ArrayList;

public class NuevoEntrenamientoFragment extends Fragment {
    private FragmentNuevoentrenamientoBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nuevoentrenamiento, container, false);



        Intent intent = new Intent(getActivity(), AddEntenamiento.class);
        startActivity(intent);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}