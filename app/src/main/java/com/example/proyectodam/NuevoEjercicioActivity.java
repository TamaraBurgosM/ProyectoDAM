package com.example.proyectodam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class NuevoEjercicioActivity extends AppCompatActivity {


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/
    //private Ejercicio ejercicio;
    //private GestionFicheros gf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RadioGroup rgTipo = findViewById(R.id.rgTipo);
        RadioButton rbRepeticiones = findViewById(R.id.rbRepeticiones);
        RadioButton rbCuantaAtras = findViewById(R.id.rbCuantaAtras);
        RadioButton rbCronometro = findViewById(R.id.rbCronometro);

        setContentView(R.layout.activity_nuevo_ejercicio);
       String nombreEjercicio="";
        String datos="";

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

        //Boton guardado
        final Button button = findViewById(R.id.bGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText et1 = (EditText) v.findViewById(R.id.tNombreEjercicio);
               // ejercicio.setsNombre(et1.toString());
               // gf.guardarFichero("Ejercicios",ejercicio.getsNombre());
                startActivity(new Intent(NuevoEjercicioActivity.this, MainActivity.class));
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

}