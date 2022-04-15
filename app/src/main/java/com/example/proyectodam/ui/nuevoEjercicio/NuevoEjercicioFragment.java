package com.example.proyectodam.ui.nuevoEjercicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyectodam.GestionFicheros;
import com.example.proyectodam.MainActivity;
import com.example.proyectodam.R;
import com.example.proyectodam.model.Ejercicio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fragmento opcion Nuevo Ejercicio
 */
public class NuevoEjercicioFragment extends Fragment {

    private final Ejercicio ejercicio = new Ejercicio();
    private final GestionFicheros gestionFicheros = new GestionFicheros();
    private RadioButton rbCuantaAtras;
    private RadioButton rbCronometro;
    private RadioButton rbRepeticiones;
    private EditText etCronometro;
    private EditText etCuentaAtras;
    private EditText etRepeticiones;
    private EditText tNombreEjercicio;
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

        View v = inflater.inflate(R.layout.fragment_nuevoejercicio, container, false);
        Button bGuardar = v.findViewById(R.id.bGuardarEjercicio);
        context = v.getContext();
        etCronometro =  v.findViewById(R.id.etCronometro);
        etRepeticiones =   v.findViewById(R.id.etRepeticiones);
        etCuentaAtras =   v.findViewById(R.id.etCuentaAtras);
        tNombreEjercicio=   v.findViewById(R.id.etNombreEjercicio);

        setRadioButton(v);

        bGuardar.setOnClickListener(view -> {
            if(guardar()) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Metodo para guardar los datos del nuevo ejercicio
     * Contiene las validaciones de campos
     * @return true si guardamos correctamente, false si hay problemas
     */
    private boolean guardar (){
        ejercicio.setiId(idGenerator());
        ejercicio.setsNombre(tNombreEjercicio.getText().toString());
        String sValor;
        if(etCronometro.getVisibility() == View.VISIBLE){
            sValor = etCronometro.getText().toString();
        }
        else if(etCuentaAtras.getVisibility() == View.VISIBLE){
            sValor = etCuentaAtras.getText().toString();
        }
        else if(etRepeticiones.getVisibility() == View.VISIBLE){
            sValor = etRepeticiones.getText().toString();
        }
        else{
            sValor ="";
        }
        ejercicio.setsValor(sValor);


        //Validaciones de campos
        if (ejercicio.getsNombre().isEmpty()) {
            Toast.makeText(context, R.string.introducir_nombre, Toast.LENGTH_SHORT).show();
        } else if (ejercicio.getcTipo() == 0) {
            Toast.makeText(context, R.string.insertar_tipo, Toast.LENGTH_SHORT).show();
        } else  if (ejercicio.getcTipo()!= 'C' && ejercicio.getsValor().isEmpty() || ejercicio.getsValor().equals("00:00ms")) {
            Toast.makeText(context, R.string.introducir_valor, Toast.LENGTH_SHORT).show();
        } else {
            if(ejercicio.getcTipo() == 'C' &&
                    (ejercicio.getsValor().isEmpty() || ejercicio.getsValor().equals("00:00ms"))){
                    ejercicio.setsValor("00:00");
                }
            else if(ejercicio.getcTipo() == 'A'){
                Pattern patron = Pattern.compile("[0-9]{1,2}:[0-9]{1,2}");
                Matcher mat = patron.matcher(ejercicio.getsValor());
                if(!mat.matches()){
                    Toast.makeText(context, R.string.formato_incorrecto, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            String sDatos = ejercicio.getiId() +";"+
                    ejercicio.getcTipo() +";"+
                    ejercicio.getsNombre() +";"+
                    ejercicio.getsValor()+";";

            gestionFicheros.guardarFichero(getString(R.string.ficheroEjercicios), sDatos, getActivity(), context);
            return true;
        }
        return false;

    }

    /**
     * Método de gestión de los Radio Button
     * @param v Vista
     */
    private void setRadioButton(View v){
        rbCuantaAtras =   v.findViewById(R.id.rbCuantaAtras);
        rbCronometro =   v.findViewById(R.id.rbCronometro);
        rbRepeticiones =   v.findViewById(R.id.rbRepeticiones);

        rbCuantaAtras.setOnClickListener(v13 -> {
            rbCuantaAtras.setChecked(true);
            rbCronometro.setChecked(false);
            rbRepeticiones.setChecked(false);

            ejercicio.setcTipo('A');

            etRepeticiones.setVisibility(View.INVISIBLE);
            etCronometro.setVisibility(View.INVISIBLE);
            etCuentaAtras.setVisibility(View.VISIBLE);

        });

        rbCronometro.setOnClickListener(v1 -> {
            rbCuantaAtras.setChecked(false);
            rbCronometro.setChecked(true);
            rbRepeticiones.setChecked(false);

            ejercicio.setcTipo('C');

            etRepeticiones.setVisibility(View.INVISIBLE);
            etCronometro.setVisibility(View.VISIBLE);
            etCuentaAtras.setVisibility(View.INVISIBLE);
        });

        rbRepeticiones.setOnClickListener(v12 -> {
            rbCuantaAtras.setChecked(false);
            rbCronometro.setChecked(false);
            rbRepeticiones.setChecked(true);

            ejercicio.setcTipo('R');

            etRepeticiones.setVisibility(View.VISIBLE);
            etCronometro.setVisibility(View.INVISIBLE);
            etCuentaAtras.setVisibility(View.INVISIBLE);

        });

    }

    /**
     * Generacion de los id
     * @return id del ejercicio
     */
    public int idGenerator(){
        int id;
        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroEjercicios),getActivity(), context);

        if(!texto.equals("")) {

            String[] split = texto.split(";");
            id = Integer.parseInt(split[split.length - 4]);

            return id +1;
        }
        return 0;
    }
}