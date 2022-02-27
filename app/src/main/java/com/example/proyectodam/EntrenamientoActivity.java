package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.example.proyectodam.databinding.ActivityEntrenamientoBinding;
import com.example.proyectodam.ui.CronometroFragment;
import com.example.proyectodam.ui.CuentaAtrasFragment;

public class EntrenamientoActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityEntrenamientoBinding binding;
    GestionFicheros gestionFicheros;
    Context c = this;

CronometroFragment cronometroFragment;
CuentaAtrasFragment cuentaAtrasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        cronometroFragment = new CronometroFragment();
        cuentaAtrasFragment = new CuentaAtrasFragment();

       //Elegimos que fragment se verá
        //Cargamos inicialmente cronometro
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, cronometroFragment);

        //Cambiamos a cuenta atras
     /*   FragmentTransaction transition  = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.contenedor, cuentaAtrasFragment);
        transition.commit();*/


        //Cambiamos a cuenta atras
        FragmentTransaction transition  = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.contenedor, cronometroFragment);
        transition.commit();



/*
        binding = ActivityEntrenamientoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/

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



}