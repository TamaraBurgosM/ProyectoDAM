package com.example.proyectodam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectodam.Model.Ejercicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddEntenamiento extends AppCompatActivity {

    GestionFicheros gestionFicheros;
    Context c = this;
    ArrayList<Ejercicio> alDatosEjercicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_nuevoentrenamiento);

        setUpView();

        if(!alDatosEjercicio.isEmpty()) {
            //Rellenamos la lista
            ListView lista = (ListView) findViewById(R.id.lvLista);
            lista.setAdapter(new ListaAdaptador(this, R.layout.content_lista_entrenamientos, alDatosEjercicio) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    TextView tvNombre = (TextView) view.findViewById(R.id.tvNombreEjercicio);
                    tvNombre.setText(((Ejercicio) entrada).getsNombre());

                    TextView tvTipo = (TextView) view.findViewById(R.id.tvTipoEjercicio);
                    tvTipo.setText(((Ejercicio) entrada).getcTipo() + "");

                    TextView tvValor = (TextView) view.findViewById(R.id.tvCantidadEjercicio);
                    tvValor.setText(((Ejercicio) entrada).getsValor());

                    SwitchCompat sElegir = (SwitchCompat) view.findViewById(R.id.sElegir);
                    sElegir.setVisibility(View.VISIBLE);

                }
            });
        }


    }

    public void onClickGuardarEntrenamiento(View view){
       // if(guardar()) {
            startActivity(new Intent(AddEntenamiento.this, MainActivity.class));
       // }
    }

    private void setUpView() {

        //TextView etRecuperarDatos = findViewById(R.id.text_home);
        gestionFicheros = new GestionFicheros();
        alDatosEjercicio = new ArrayList<Ejercicio>();
        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero("Ejercicios", this, c);
       // etRecuperarDatos.setText(texto);

        if(!texto.isEmpty()) {
            String[] split = texto.split(";");

            for (int i = 0; i < split.length; i = i + 4) {
                ejercicio = new Ejercicio();
                ejercicio.setcTipo(split[i + 1].charAt(0));
                ejercicio.setsNombre(split[i + 2]);
                ejercicio.setsValor(split[i + 3]);
                alDatosEjercicio.add(ejercicio);

            }
        }
    }
}