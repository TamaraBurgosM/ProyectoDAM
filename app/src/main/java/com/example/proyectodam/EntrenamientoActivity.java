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
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.proyectodam.Model.Ejercicio;
import com.example.proyectodam.Model.Entrenamiento;
import com.example.proyectodam.databinding.ActivityEntrenamientoBinding;
import com.example.proyectodam.ui.CronometroFragment;
import com.example.proyectodam.ui.CuentaAtrasFragment;
import com.example.proyectodam.ui.RepeticionesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EntrenamientoActivity extends AppCompatActivity  {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityEntrenamientoBinding binding;
    private GestionFicheros gestionFicheros;
    private Entrenamiento entrenamiento;
    private ArrayList<Ejercicio> alEjercicio = new ArrayList<Ejercicio>();
    private Context context = this;
    int contador =0;

    private CronometroFragment cronometroFragment;
    private CuentaAtrasFragment cuentaAtrasFragment;
    private RepeticionesFragment repeticionesFragment;
    private FloatingActionButton fbNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);
        fbNext = (FloatingActionButton) findViewById(R.id.fbNext);

        //Cargamos el array List con los ejercicios del entrenamiento
        alEjercicio = getAlEntrenamiento();

        //Comprobamos el tipo del
        verTipo(alEjercicio.get(contador).getcTipo(),
                alEjercicio.get(contador).getsNombre(),
                alEjercicio.get(contador).getsValor());


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
                cronometroFragment = new CronometroFragment();
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
                cuentaAtrasFragment = new CuentaAtrasFragment();
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.contenedor, cuentaAtrasFragment);
                transition.commit();

                break;


           //Repeticiones
            case  'R':

                repeticionesFragment= new RepeticionesFragment();
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

     //  String sEntrenamiento =  gestionFicheros.leerFichero( "Entrenamiento", this, context);

        ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio> ();
        Ejercicio ejercicio1 = new Ejercicio();
        ejercicio1.setsNombre("Sentadillas");
        ejercicio1.setcTipo('C');
        ejercicio1.setsValor("01:00");
        Ejercicio ejercicio2 = new Ejercicio();
        ejercicio2.setsNombre("Zancada");
        ejercicio2.setcTipo('A');
        ejercicio2.setsValor("01:20");
        Ejercicio ejercicio3 = new Ejercicio();
        ejercicio3.setsNombre("Abdominal");
        ejercicio3.setcTipo('R');
        ejercicio3.setsValor("25");
        Ejercicio ejercicio4 = new Ejercicio();
        ejercicio4.setsNombre("Plancha");
        ejercicio4.setcTipo('C');
        ejercicio4.setsValor("01:40");

        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);
        ejercicios.add(ejercicio3);
        ejercicios.add(ejercicio4);

        Entrenamiento e = new Entrenamiento();
        e.setaEjercicio(ejercicios);


        alEjercicio.add(ejercicio1);
        alEjercicio.add(ejercicio2);
        alEjercicio.add(ejercicio3);
        alEjercicio.add(ejercicio4);


        return alEjercicio;
    }


}