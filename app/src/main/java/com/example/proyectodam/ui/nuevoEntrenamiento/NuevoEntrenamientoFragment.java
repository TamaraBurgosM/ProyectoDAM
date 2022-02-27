package com.example.proyectodam.ui.nuevoEntrenamiento;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectodam.MainActivity;
import com.example.proyectodam.NuevoEjercicioActivity;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentNuevoejercicioBinding;
import com.example.proyectodam.databinding.FragmentNuevoentrenamientoBinding;

public class NuevoEntrenamientoFragment extends Fragment {
    private FragmentNuevoentrenamientoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nuevoentrenamiento, container, false);

        TextView tv = v.findViewById(R.id.textView);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}