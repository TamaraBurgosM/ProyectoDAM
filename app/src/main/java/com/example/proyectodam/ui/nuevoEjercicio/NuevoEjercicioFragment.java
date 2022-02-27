package com.example.proyectodam.ui.nuevoEjercicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectodam.NuevoEjercicioActivity;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentNuevoejercicioBinding;

public class NuevoEjercicioFragment extends Fragment {

    private FragmentNuevoejercicioBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* NuevoEjercicioViewModel nuevoEjercicioViewModel =
                new ViewModelProvider(this).get(NuevoEjercicioViewModel.class);

        binding = FragmentNuevoejercicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        nuevoEjercicioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        Intent intent = new Intent(getActivity(), NuevoEjercicioActivity.class);
        getActivity().startActivity(intent);

        return root;*/
        View v = inflater.inflate(R.layout.fragment_nuevoejercicio, container, false);

        TextView tv = v.findViewById(R.id.textView);

        Intent intent = new Intent(getActivity(), NuevoEjercicioActivity.class);
        startActivity(intent);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}