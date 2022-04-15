package com.example.proyectodam.ui.verEntrenamiento;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.ListaAdaptador;
import com.example.proyectodam.model.Ejercicio;
import com.example.proyectodam.R;
import com.example.proyectodam.model.Entrenamiento;

import java.util.ArrayList;

public class VerEntrenamientoFragment extends Fragment {
    private ArrayList<Entrenamiento> alDatosEntrenamiento;
    private Context context;


    /**
     * Metodo de creación de la vista
     * @param inflater infla el layout
     * @param container contenedor de la vista
     * @param savedInstanceState bundle
     * @return vista
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verentrenamiento, container, false);

        context = v.getContext();

        setUpView();

        if(!alDatosEntrenamiento.isEmpty()) {
            //Rellenamos la lista
            ArrayList<Ejercicio> nuevoEjercicio  = new ArrayList<>();
            for (Entrenamiento entrenamiento : alDatosEntrenamiento) {

                boolean esPrimera = true;
                for (Ejercicio e : entrenamiento.getaEjercicio()) {
                    if(esPrimera) {
                        e.setFecha(entrenamiento.getiId());
                        esPrimera = false;
                    }
                    else{
                        e.setFecha("");
                    }
                    nuevoEjercicio.add(e);

                }
            }

            ListView lista = v.findViewById(R.id.lvLista);
            lista.setAdapter(new ListaAdaptador(getActivity(), R.layout.ver_entrenamiento_content, nuevoEjercicio) {
                @Override
                public void onEntrada(Object entrada, View view) {

                    if(!((Ejercicio) entrada).getFecha().isEmpty()) {
                        TextView tvFecha =  view.findViewById(R.id.tvFecha);
                        tvFecha.setText(((Ejercicio) entrada).getFecha());
                    }
                    LinearLayout layoutSwitch =  view.findViewById(R.id.llSwitch);
                    layoutSwitch.setVisibility(LinearLayout.GONE);

                    TextView tvNombre = view.findViewById(R.id.tvNombreEjercicio);
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

                    TextView tvTipo =  view.findViewById(R.id.tvTipoEjercicio);
                    tvTipo.setText(tipo);

                    TextView tvValor =   view.findViewById(R.id.tvCantidadEjercicio);
                    tvValor.setText(((Ejercicio) entrada).getsValor());

                }
            });

                }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Iniciamos los campos de la vista
     */
    private void setUpView() {

        GestionFicheros gestionFicheros = new GestionFicheros();
        alDatosEntrenamiento = new ArrayList<>();


        Ejercicio ejercicio;
        Entrenamiento entrenamiento;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroMisEntrenamientos), getActivity(), context);


        if(!texto.isEmpty()) {
            String[] splitFecha = texto.split("\\|");

            for (int k = 1; k < splitFecha.length; k++) {
                entrenamiento = new Entrenamiento();

                String x =splitFecha[k].substring(0,19);
                entrenamiento.setiId(x);

                String[] split = splitFecha[k].split(";");
                ArrayList<Ejercicio> alDatosEjercicio = new ArrayList<>();

                for (int i = 1; i < split.length; i = i + 3) {
                    ejercicio = new Ejercicio();
                    ejercicio.setsNombre(split[i]);
                    ejercicio.setcTipo(split[i +1].charAt(0));
                    ejercicio.setsValor(split[i + 2]);
                    alDatosEjercicio.add(ejercicio);

                }
                entrenamiento.setaEjercicio(alDatosEjercicio);
                alDatosEntrenamiento.add(entrenamiento);

            }

        }
    }

}