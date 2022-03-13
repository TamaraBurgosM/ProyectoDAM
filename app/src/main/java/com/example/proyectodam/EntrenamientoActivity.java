package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.proyectodam.Model.Ejercicio;
import com.example.proyectodam.ui.CronometroFragment;
import com.example.proyectodam.ui.CuentaAtrasFragment;
import com.example.proyectodam.ui.RepeticionesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrenamientoActivity extends AppCompatActivity  {
    private AppBarConfiguration mAppBarConfiguration;
    private GestionFicheros gestionFicheros;
    private ArrayList<Ejercicio> alEjercicio = new ArrayList<Ejercicio>();
    private final Context context = this;
    int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);
        gestionFicheros= new GestionFicheros();
        FloatingActionButton fbNext = (FloatingActionButton) findViewById(R.id.fbNext);

        //Cargamos el array List con los ejercicios del entrenamiento
        alEjercicio = getAlEntrenamiento();

        if(alEjercicio.isEmpty())
        {
            Toast.makeText(context, "No hay entrenamiento configurado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EntrenamientoActivity.this, MainActivity.class));

        }
        else {
            //Comprobamos el tipo del
            verTipo(alEjercicio.get(contador).getcTipo(),
                    alEjercicio.get(contador).getsNombre(),
                    alEjercicio.get(contador).getsValor());
        }


    }



    public void onClickNext(View v){

        //Cuando pulsamos el boton para el siguiente ejercicio incrementamos el contador
        //si aún quedan ejercicios para realizar obtenemos los datos del siguiente ejercicio
        //de lo contrario volvemos a la pantalla inicial
        contador ++;
        if (contador < alEjercicio.size()){
            verTipo(alEjercicio.get(contador).getcTipo(),
                    alEjercicio.get(contador).getsNombre(),
                    alEjercicio.get(contador).getsValor());
        }
        else{
            startActivity(new Intent(EntrenamientoActivity.this, MainActivity.class));
        }

    }

    public void verTipo (char tipo, String nombre, String valor){

        //Cargamos el nuevo ejercicio que corresponda según su tipo
        FragmentTransaction transition;
        Bundle bundle = new Bundle();


        switch(tipo) {
            //Cronometro
            case 'C':
                CronometroFragment cronometroFragment = new CronometroFragment();
                bundle.putString("nombre", nombre);
                bundle.putString("valor", valor);
                bundle.putInt("pbProgress", 0);
                cronometroFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.contenedor, cronometroFragment);
                transition.commit();

                break;

            //Cuenta atras
            case  'A':
                CuentaAtrasFragment cuentaAtrasFragment = new CuentaAtrasFragment();
                bundle.putString("nombre", nombre);
                bundle.putString("valor", valor);
                bundle.putInt("pbProgress", 0);
                cuentaAtrasFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.contenedor, cuentaAtrasFragment);
                transition.commit();

                break;


           //Repeticiones
            case  'R':

                RepeticionesFragment repeticionesFragment = new RepeticionesFragment();
                bundle.putString("nombre", nombre);
                bundle.putInt("valor", Integer.parseInt(valor));
                bundle.putInt("pbProgress", 0);
                repeticionesFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.contenedor, repeticionesFragment);
                transition.commit();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate el menu
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    public ArrayList<Ejercicio> getAlEntrenamiento() {

       String sEntrenamiento =  gestionFicheros.leerFichero( "Entrenamiento", this, context);

       String[]sId= sEntrenamiento.split(";");

        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero("Ejercicios", this, context);

        if(!sEntrenamiento.isEmpty() && !texto.isEmpty()) {
            String[] split = texto.split(";");

            for (int i = 0; i < split.length; i = i + 4) {
                ejercicio = new Ejercicio();

                if (Arrays.asList(sId).contains(split[i])) {
                    ejercicio.setiId(Integer.parseInt(split[i]));
                    ejercicio.setcTipo(split[i + 1].charAt(0));
                    ejercicio.setsNombre(split[i + 2]);
                    ejercicio.setsValor(split[i + 3]);
                    alEjercicio.add(ejercicio);
                }

            }

        }



        return alEjercicio;
    }


}