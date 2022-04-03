package com.example.proyectodam.ui.nuevoEntrenamiento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.ListaAdaptador;
import com.example.proyectodam.MainActivity;
import com.example.proyectodam.model.Ejercicio;
import com.example.proyectodam.R;
import com.example.proyectodam.databinding.FragmentNuevoentrenamientoBinding;

import java.util.ArrayList;

public class NuevoEntrenamientoFragment extends Fragment {
    private FragmentNuevoentrenamientoBinding binding;
    private GestionFicheros gestionFicheros;
    private Context context;
    private ArrayList<Ejercicio> alDatosEjercicio;
    private SwitchCompat sElegir;
    private Button bGuardar;
    private ArrayList<Integer> alIdEjercicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nuevoentrenamiento, container, false);

        context = v.getContext();
        bGuardar = (Button) v.findViewById(R.id.bGuardar);
        alIdEjercicio = new ArrayList<>();

        setUpView();

        sElegir = (SwitchCompat) v.findViewById(R.id.swElegir);

        if(!alDatosEjercicio.isEmpty()) {
            //Rellenamos la lista
            ListView lista = (ListView) v.findViewById(R.id.lvLista);
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

                    SwitchCompat sElegir = (SwitchCompat) view.findViewById(R.id.swElegir);
                    sElegir.setVisibility(View.VISIBLE);
                    sElegir.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        int ejercicio = ((Ejercicio) entrada).getiId();
                        if(alIdEjercicio.contains(ejercicio)){
                            if(alIdEjercicio.size()>1) {
                                alIdEjercicio.remove(alIdEjercicio.indexOf(ejercicio));
                            }else{
                                alIdEjercicio.clear();
                            }
                        }
                        else {
                            alIdEjercicio.add(ejercicio);
                        }
                       /* StringBuilder texto= new StringBuilder();

                        for (int i = 0; i < alIdEjercicio.size(); i++) {
                            texto.append(alIdEjercicio.get(i)).append(", ");

                        }
                        Toast.makeText(context, texto.toString(), Toast.LENGTH_SHORT).show();*/

                    });
                }
            });
        }

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!alIdEjercicio.isEmpty()){
                    StringBuilder sbDatos= new StringBuilder();

                    for (int i = 0; i< alIdEjercicio.size(); i++) {
                        sbDatos.append(alIdEjercicio.get(i)).append(";");

                    }
                    gestionFicheros.guardarFicheroSimple(getString(R.string.ficheroEntrenamiento), sbDatos.toString(),getActivity(), context);

                }

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Intent intent = new Intent(getActivity(), AddEntenamiento.class);
        ///startActivity(intent);


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpView() {

        //TextView etRecuperarDatos = findViewById(R.id.text_home);
        gestionFicheros = new GestionFicheros();
        alDatosEjercicio = new ArrayList<Ejercicio>();
        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroEjercicios), getActivity(), context);
        // etRecuperarDatos.setText(texto);

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