package com.example.proyectodam.ui.verEntrenamiento;

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

import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.ListaAdaptador;
import com.example.proyectodam.MainActivity;
import com.example.proyectodam.Model.Ejercicio;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentVerentrenamientoBinding;

import java.util.ArrayList;

public class VerEntrenamientoFragment extends Fragment {
    private FragmentVerentrenamientoBinding binding;
    ArrayList<Ejercicio> alDatosEjercicio;
    GestionFicheros gestionFicheros;
    Context c;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verentrenamiento, container, false);

        TextView tv = v.findViewById(R.id.textView);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        c = v.getContext();
        //TODO alDatosEjercicio iniciar
        setUpView();

        if(!alDatosEjercicio.isEmpty()) {
            //Rellenamos la lista
            ListView lista = (ListView) v.findViewById(R.id.lvLista);
            lista.setAdapter(new ListaAdaptador(getActivity(), R.layout.content_lista_entrenamientos, alDatosEjercicio) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    TextView tvNombre = (TextView) view.findViewById(R.id.tvNombreEjercicio);
                    tvNombre.setText(((Ejercicio) entrada).getsNombre());

                    TextView tvTipo = (TextView) view.findViewById(R.id.tvTipoEjercicio);
                    tvTipo.setText(((Ejercicio) entrada).getcTipo() + "");

                    TextView tvValor = (TextView) view.findViewById(R.id.tvCantidadEjercicio);
                    tvValor.setText(((Ejercicio) entrada).getsValor());

                }
            });
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //TODO
    private void setUpView() {

        gestionFicheros = new GestionFicheros();
        alDatosEjercicio = new ArrayList<Ejercicio>();
        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero("Ejercicios", getActivity(), c);


        if(!texto.isEmpty()) {
            String[] split = texto.split(";");

            for (int i = 0; i < split.length; i = i + 4) {
                ejercicio = new Ejercicio();
                ejercicio.setiId(Integer.parseInt(split[i]));
                ejercicio.setcTipo(split[i + 1].charAt(0));
                ejercicio.setsNombre(split[i + 2]);
                ejercicio.setsValor(split[i + 3]);
                alDatosEjercicio.add(ejercicio);

            }
        }
    }
}