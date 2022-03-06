package com.example.proyectodam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodam.Model.Ejercicio;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NuevoEjercicioActivity extends AppCompatActivity {


    private Ejercicio ejercicio = new Ejercicio();
    private GestionFicheros gf = new GestionFicheros();
    private RadioButton rbCuantaAtras;
    private RadioButton rbCronometro;
    private RadioButton rbRepeticiones;
    private String sValor;
    private EditText etCronometro;
    private EditText etCuentaAtras;
    private EditText etRepeticiones;
    private EditText tNombreEjercicio;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_nuevoejercicio);
        setRadioButton();

        setUpView();
    }
    private void setUpView(){

        etCronometro = (EditText) findViewById(R.id.etCronometro);
        etRepeticiones = (EditText) findViewById(R.id.etRepeticiones);
        etCuentaAtras = (EditText) findViewById(R.id.etCuentaAtras);
        tNombreEjercicio= (EditText) findViewById(R.id.tNombreEjercicio);

    }

    public void onClickGuardar(View view){

        if(guardar()) {
            startActivity(new Intent(NuevoEjercicioActivity.this, MainActivity.class));
        }
    }

    private boolean guardar (){
        ejercicio.setiId(idGenerator());
        ejercicio.setsNombre(tNombreEjercicio.getText().toString());
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
            sValor="";
        }
        ejercicio.setsValor(sValor);

        String sDatos = ejercicio.getiId() +";"+
                ejercicio.getcTipo() +";"+
                ejercicio.getsNombre() +";"+
                ejercicio.getsValor()+";";

        if (ejercicio.getsValor().isEmpty() || ejercicio.getsValor().equals("00:00ms")) {
            Toast.makeText(c, "Debe introducir un valor", Toast.LENGTH_SHORT).show();
        } else if (ejercicio.getcTipo() == 0) {
            Toast.makeText(c, "Debe introducir un tipo", Toast.LENGTH_SHORT).show();
        } else if (ejercicio.getsNombre().isEmpty()) {
            Toast.makeText(c, "Debe introducir un nombre", Toast.LENGTH_SHORT).show();
        } else {

            if(ejercicio.getcTipo() == 'C' || ejercicio.getcTipo() == 'A'){
                Pattern patron = Pattern.compile("[0-9]{1,2}:[0-9]{1,2}");
                Matcher mat = patron.matcher(ejercicio.getsValor());
                if(!mat.matches()){
                    Toast.makeText(c, "Formato incorrecto 00:00", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            gf.guardarFichero("Ejercicios", sDatos, this, c);
            return true;
        }
        return false;

    }

    private void setRadioButton(){
        rbCuantaAtras = (RadioButton) findViewById(R.id.rbCuantaAtras);
        rbCronometro = (RadioButton) findViewById(R.id.rbCronometro);
        rbRepeticiones = (RadioButton) findViewById(R.id.rbRepeticiones);


        rbCuantaAtras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbCuantaAtras.setChecked(true);
                rbCronometro.setChecked(false);
                rbRepeticiones.setChecked(false);

                ejercicio.setcTipo('A');

                etRepeticiones.setVisibility(View.INVISIBLE);
                etCronometro.setVisibility(View.INVISIBLE);
                etCuentaAtras.setVisibility(View.VISIBLE);

            }
        });

        rbCronometro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbCuantaAtras.setChecked(false);
                rbCronometro.setChecked(true);
                rbRepeticiones.setChecked(false);

                ejercicio.setcTipo('C');

                etRepeticiones.setVisibility(View.INVISIBLE);
                etCronometro.setVisibility(View.VISIBLE);
                etCuentaAtras.setVisibility(View.INVISIBLE);
            }
        });

        rbRepeticiones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbCuantaAtras.setChecked(false);
                rbCronometro.setChecked(false);
                rbRepeticiones.setChecked(true);

                ejercicio.setcTipo('R');

                etRepeticiones.setVisibility(View.VISIBLE);
                etCronometro.setVisibility(View.INVISIBLE);
                etCuentaAtras.setVisibility(View.INVISIBLE);

            }
        });

    }

    public int idGenerator(){
        int id=0;

        String texto = gf.leerFichero("Ejercicios",this, c);
        if(!texto.equals("")) {

            String[] split = texto.split(";");

            id = Integer.parseInt(split[split.length - 4]);



        return id +1;
        }
        return 0;
    }


    // private void setSupportActionBar(RelativeLayout toolbar) {
    // }

   /* public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Almacenamos el valor de la opción seleccionada
        switch(view.getId()) {
            case R.id.rbCronometro:
                if (checked)
                    ejercicio.setcTipo('C');
                    break;
            case R.id.rbCuantaAtras:
                if (checked)
                    ejercicio.setcTipo('A');
                    break;
            case R.id.rbRepeticiones:
                if (checked)
                    ejercicio.setcTipo('R');
                    break;
        }
    }*/



  /*  public String datos(){

        String linea = ejercicio.getsNombre() +
                    ";"+
                    ejercicio.getcTipo()+
                    ";"+
                    ejercicio.getsValor();


        return linea;
    }*/


    //Radio Group
        /*rgTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int idDeRadioButtonSeleccionado = rgTipo.getCheckedRadioButtonId();
                if (idDeRadioButtonSeleccionado == rbCronometro.getId()) {
                    ejercicio.setcTipo('C');
                } else if (idDeRadioButtonSeleccionado == rbCuantaAtras.getId()) {
                    ejercicio.setcTipo('A');
                } else if(idDeRadioButtonSeleccionado == rbRepeticiones.getId()){
                    ejercicio.setcTipo('R');
                }
            }
        });*/

}