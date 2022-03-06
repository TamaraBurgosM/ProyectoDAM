package com.example.proyectodam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proyectodam.Model.Ejercicio;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodam.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    GestionFicheros gestionFicheros;
    Context c = this;
    ArrayList<Ejercicio> alDatosEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, EntrenamientoActivity.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_nuevoejercicio, R.id.nav_nuevoentrenamiento, R.id.nav_verEntrenamiento)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


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

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUpView(){

        TextView etRecuperarDatos = findViewById(R.id.text_home);
        gestionFicheros = new GestionFicheros();
        alDatosEjercicio= new ArrayList<Ejercicio>();
        Ejercicio ejercicio ;

        String texto = gestionFicheros.leerFichero("Ejercicios",this, c);
        etRecuperarDatos.setText(texto);

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