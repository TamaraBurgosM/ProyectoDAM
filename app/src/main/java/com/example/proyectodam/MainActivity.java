package com.example.proyectodam;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    GestionFicheros gestionFicheros;

    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpView();

            final Button bEntrar = findViewById(R.id.bEntrar);
        bEntrar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, NuevoEjercicioActivity.class));
                }

            });

        final Button bCrono = findViewById(R.id.bCrono);
        bCrono.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CuentaAtras.class));
            }

        });
        }

    private void setUpView(){


        TextView etRecuperarDatos = findViewById(R.id.tvTextoPrevio);
        gestionFicheros = new GestionFicheros();

        String texto = gestionFicheros.leerFichero("Ejercicios",this, c);
        etRecuperarDatos.setText(texto);
    }


}