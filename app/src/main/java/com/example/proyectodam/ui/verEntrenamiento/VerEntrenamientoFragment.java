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
import com.example.proyectodam.databinding.FragmentVerentrenamientoBinding;
import com.example.proyectodam.model.Entrenamiento;

import java.util.ArrayList;

public class VerEntrenamientoFragment extends Fragment {
    private FragmentVerentrenamientoBinding binding;
    private ArrayList<Ejercicio> alDatosEjercicio;
    private ArrayList<Entrenamiento> alDatosEntrenamiento;
    private GestionFicheros gestionFicheros;
    private Context context;
    int contador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verentrenamiento, container, false);

        TextView tv = v.findViewById(R.id.tvTituloNombre);
         context = v.getContext();

        setUpView();

        if(!alDatosEntrenamiento.isEmpty()) {
            //Rellenamos la lista
//|20/03/2022 18:15:25;cronooo;C;0:05;fasda;C;0:01;
// |19/03/2022 18:15:25;cronooo;C;0:02;
// |18/03/2022 18:15:25;cronooo;C;0:01;

            ArrayList<Ejercicio> nuevoEjercicio  = new ArrayList<Ejercicio>();
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

            ListView lista = (ListView) v.findViewById(R.id.lvLista);
            lista.setAdapter(new ListaAdaptador(getActivity(), R.layout.ver_entrenamiento_content, nuevoEjercicio) {
                @Override
                public void onEntrada(Object entrada, View view) {

                    if(!((Ejercicio) entrada).getFecha().isEmpty()) {
                        TextView tvFecha = (TextView) view.findViewById(R.id.tvFecha);
                        tvFecha.setText(((Ejercicio) entrada).getFecha());
                    }
                    LinearLayout layoutSwitch = (LinearLayout) view.findViewById(R.id.llSwitch);
                    layoutSwitch.setVisibility(LinearLayout.GONE);

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





          /*  LinearLayout listaPadre = (LinearLayout) v.findViewById(R.id.listaPadre);
            for (Entrenamiento entrenamiento : alDatosEntrenamiento){

                View child = getLayoutInflater().inflate(R.layout.ver_entrenamiento_content, container, false);

                TextView tvFecha = (TextView) child.findViewById(R.id.tvFecha);
                tvFecha.setText("Fecha: " + entrenamiento.getiId());

                LinearLayout listaHija = (LinearLayout) v.findViewById(R.id.listaPadre);
                for (Ejercicio e : entrenamiento.getaEjercicio()) {


                    View child2 = getLayoutInflater().inflate(R.layout.content_lista_entrenamientos, container, false);

                    TextView tvNombre = (TextView) child2.findViewById(R.id.tvNombreEjercicio);
                    tvNombre.setText(e.getsNombre());

                    TextView tvTipo = (TextView) child2.findViewById(R.id.tvTipoEjercicio);
                    tvTipo.setText(e.getcTipo()+"");

                    TextView tvValor = (TextView) child2.findViewById(R.id.tvCantidadEjercicio);
                    tvValor.setText(e.getsValor());

                    listaHija.addView(child2);

                }
                listaPadre.addView(child);
            }
*/

           //

        //    lista.setAdapter(new ListaAdaptadorVerEntrenamiento(getActivity(), R.layout.ver_entrenamiento_content, alDatosEntrenamiento) );

              /*  lista.setAdapter(new ListaAdaptador(getActivity(), R.layout.ver_entrenamiento_content, alDatosEntrenamiento) {
                    @Override
                    public void onEntrada(Object entrada, View view) {


                        TextView tvFecha = (TextView) view.findViewById(R.id.tvFecha);
                        tvFecha.setText("Fecha: "+ ((Entrenamiento)entrada).getiId());


                        setUpView1(view, container, ((Entrenamiento) entrada).getiId());


                    }
                });*/

                }

          /*  HashMap<String, ArrayList<Ejercicio>> hmEntrenamiento = new  HashMap<String, ArrayList<Ejercicio>> ();
            for (Entrenamiento e:alDatosEntrenamiento ) {
                hmEntrenamiento.put(e.getiId(),e.getaEjercicio());
            }

            List<HashMap<String, ArrayList<Ejercicio>>> listItem = new ArrayList<>();
            SimpleAdapter adapter = new SimpleAdapter(c,listItem, R.layout.ver_entrenamiento_content,
                                                    new String[]{"linea1","linea2"},
                                                    new int[]{R.id.tvFecha,R.id.tvNombreEjercicio,} );

            for (Map.Entry<String, ArrayList<Ejercicio>> stringArrayListEntry : hmEntrenamiento.entrySet()) {
                HashMap<String, ArrayList<Ejercicio>> resultMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) stringArrayListEntry;
                resultMap.put("Linea1", pair.getKey().toString());
                resultMap.put("linea2", pair.getValue().toString());
                listItem.add(resultMap);

            }
            lista.setAdapter(adapter);*/

      //  }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void setUpView() {

        gestionFicheros = new GestionFicheros();
        alDatosEntrenamiento = new ArrayList<Entrenamiento>();
        contador = 0;

        Ejercicio ejercicio;
        Entrenamiento entrenamiento;

        //|20/03/2022 18:15:25;cronooo;C;0:05;fasda;C;0:01;|19/03/2022 18:15:25;cronooo;C;0:02;|18/03/2022 18:15:25;cronooo;C;0:01;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroMisEntrenamientos), getActivity(), context);


        if(!texto.isEmpty()) {
            String[] splitFecha = texto.split("\\|");  //18/03/2022 18:15:25


            for (int k = 1; k < splitFecha.length; k++) {
                entrenamiento = new Entrenamiento();

                String x =splitFecha[k].substring(0,19);
                entrenamiento.setiId(x);

                String[] split = splitFecha[k].split(";");
                alDatosEjercicio = new ArrayList<Ejercicio>();

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