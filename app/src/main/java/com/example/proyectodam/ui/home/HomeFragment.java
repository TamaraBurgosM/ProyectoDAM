package com.example.proyectodam.ui.home;

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

import com.example.proyectodam.EntrenamientoActivity;
import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.ListaAdaptador;
import com.example.proyectodam.model.Ejercicio;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private  Context context;
    private ArrayList<Ejercicio> alDatosEjercicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = root.getContext();

        FloatingActionButton start = (FloatingActionButton) root.findViewById(R.id.fabEmpezar);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EntrenamientoActivity.class);
                startActivity(intent);
                //startActivity(new Intent(MainActivity.this, EntrenamientoActivity.class));
            }
        });
        setUpView();

        if(!alDatosEjercicio.isEmpty()) {
            //Rellenamos la lista
            ListView lista = (ListView) root.findViewById(R.id.lvLista);
            lista.setAdapter(new ListaAdaptador(getActivity(), R.layout.content_lista_entrenamientos, alDatosEjercicio) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    TextView tvNombre = (TextView) view.findViewById(R.id.tvNombreEjercicio);
                    tvNombre.setText(((Ejercicio) entrada).getsNombre());

                    String tipo="";
                    switch (((Ejercicio) entrada).getcTipo()){
                        case 'C':
                            tipo = getString(R.string.cronometro);
                            break;
                        case 'A':
                            tipo = getString(R.string.cuantaAtras);
                            break;
                        case 'R':
                            tipo = getString(R.string.repeticiones);
                            break;

                    }

                    TextView tvTipo = (TextView) view.findViewById(R.id.tvTipoEjercicio);
                    tvTipo.setText(tipo);

                    TextView tvValor = (TextView) view.findViewById(R.id.tvCantidadEjercicio);
                    tvValor.setText(((Ejercicio) entrada).getsValor());

                }
            });
        }

       // final TextView textView = binding.textHome;
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpView(){

        // TextView etRecuperarDatos = findViewById(R.id.text_home);
        GestionFicheros gestionFicheros = new GestionFicheros();
        alDatosEjercicio = new ArrayList<Ejercicio>();
       /* Ejercicio ejercicio ;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroEntrenamiento),getActivity(), c);
        //  etRecuperarDatos.setText(texto);

        if(!texto.isEmpty()) {
            String[] split = texto.split(";");

            for (int i = 0; i < split.length; i = i + 4) {
                ejercicio = new Ejercicio();
                ejercicio.setcTipo(split[i + 1].charAt(0));
                ejercicio.setsNombre(split[i + 2]);
                ejercicio.setsValor(split[i + 3]);
                alDatosEjercicio.add(ejercicio);

            }
        }*/
        String sEntrenamiento =  gestionFicheros.leerFichero( getString(R.string.ficheroEntrenamiento),getActivity(), context);

        String[]sId= sEntrenamiento.split(";");

        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroEjercicios),getActivity(), context);

        if(!sEntrenamiento.isEmpty() && !texto.isEmpty()) {

            String[] split = texto.split(";");

            for (int i = 0; i < split.length; i = i + 4) {
                ejercicio = new Ejercicio();

                if (Arrays.asList(sId).contains(split[i])) {
                    ejercicio.setiId(Integer.parseInt(split[i]));
                    ejercicio.setcTipo(split[i + 1].charAt(0));
                    ejercicio.setsNombre(split[i + 2]);
                    ejercicio.setsValor(split[i + 3]);
                    alDatosEjercicio.add(ejercicio);
                }

            }

        }

    }
}