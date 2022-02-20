package com.example.proyectodam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.proyectodam.Model.Ejercicio;


public class NuevoEjercicioActivity extends AppCompatActivity {

    Context c = this;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nuevo_ejercicio);
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
        // String valor = tNombreEjercicio.getText().toString();



        if(guardar()) {
            startActivity(new Intent(NuevoEjercicioActivity.this, MainActivity.class));
        }
    }

    private boolean guardar (){
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
                ejercicio.getsValor();

        if (ejercicio.getsValor().isEmpty() || ejercicio.getsValor()=="00:00ms") {
            Toast.makeText(c, "Debe introducir un valor", Toast.LENGTH_SHORT).show();
        } else if (ejercicio.getcTipo() == 0) {
            Toast.makeText(c, "Debe introducir un tipo", Toast.LENGTH_SHORT).show();
        } else if (ejercicio.getsNombre().isEmpty()) {
            Toast.makeText(c, "Debe introducir un nombre", Toast.LENGTH_SHORT).show();
        } else {
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