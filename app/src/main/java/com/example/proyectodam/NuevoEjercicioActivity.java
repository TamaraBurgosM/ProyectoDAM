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
    private EditText tValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nuevo_ejercicio);
        setRadioButton();


        setUpView();
    }
    private void setUpView(){


        final Button button = findViewById(R.id.bGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*TODO:
                    -Validacion de valores
                    -Validación de campos rellenos
                * */

            final EditText tNombreEjercicio = (EditText) findViewById(R.id.tNombreEjercicio);
           // String valor = tNombreEjercicio.getText().toString();
            ejercicio.setsNombre(tNombreEjercicio.getText().toString());
            ejercicio.setsValor(tValor.getText().toString());
            guardar();
            startActivity(new Intent(NuevoEjercicioActivity.this, MainActivity.class));
            }

        });
    }

    private void guardar (){

        String sDatos = ejercicio.getiId() +";"+
                ejercicio.getcTipo() +";"+
                ejercicio.getsNombre() +";"+
                ejercicio.getsValor();

        gf.guardarFichero("Ejercicios", sDatos, this,c);
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
                tValor = (EditText) findViewById(R.id.etCuentaAtras);

            }
        });

        rbCronometro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbCuantaAtras.setChecked(false);
                rbCronometro.setChecked(true);
                rbRepeticiones.setChecked(false);
                ejercicio.setcTipo('C');
                tValor = (EditText) findViewById(R.id.etCronometro);
            }
        });

        rbRepeticiones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbCuantaAtras.setChecked(false);
                rbCronometro.setChecked(false);
                rbRepeticiones.setChecked(true);
                ejercicio.setcTipo('R');
                tValor = (EditText) findViewById(R.id.etRepeticiones);
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