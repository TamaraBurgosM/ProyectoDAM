package com.example.proyectodam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

public class NuevoEjercicio extends AppCompatActivity {


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);
        String nombreEjercicio="";


        //Boton guardado
        final Button button = findViewById(R.id.bGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText et1 = (EditText) v.findViewById(R.id.tNombreEjercicio);
                startActivity(new Intent(NuevoEjercicio.this, MainActivity.class));
            }

        });
    }

   // private void setSupportActionBar(RelativeLayout toolbar) {
   // }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String opcion ="";
        // Almacenamos el valor de la opción seleccionada
        switch(view.getId()) {
            case R.id.rbCronometro:
                if (checked)
                    opcion="C";
                    break;
            case R.id.rbCuantaAtras:
                if (checked)
                    opcion="A";
                    break;
            case R.id.rbRepeticiones:
                if (checked)
                    opcion="R";
                    break;
        }
    }



}