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

import com.example.proyectodam.interfaces.PassDataI;
import com.example.proyectodam.model.Ejercicio;
import com.example.proyectodam.ui.CronometroFragment;
import com.example.proyectodam.ui.CuentaAtrasFragment;
import com.example.proyectodam.ui.RepeticionesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EntrenamientoActivity extends AppCompatActivity implements PassDataI {
    private AppBarConfiguration mAppBarConfiguration;
    private GestionFicheros gestionFicheros;
    private ArrayList<Ejercicio> alEjercicio = new ArrayList<Ejercicio>();
    private final Context context = this;
    private String datosGuardar ="";
    private String datosParcial ="";
    private FloatingActionButton fbNext ;
    private CronometroFragment cronometroFragment;
    private int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            cronometroFragment = (CronometroFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");

        }
        gestionFicheros= new GestionFicheros();
        fbNext = (FloatingActionButton) findViewById(R.id.fabSiguienteEjercicio);

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
  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "myFragmentName", cronometroFragment);

    }*/


    public void onClickNext(View v){

        //Cuando pulsamos el boton para el siguiente ejercicio incrementamos el contador
        //si aún quedan ejercicios para realizar obtenemos los datos del siguiente ejercicio
        //de lo contrario volvemos a la pantalla inicial
        contador ++;
        datosGuardar = datosGuardar + datosParcial;
        datosParcial = "";
        Toast.makeText(context, datosGuardar, Toast.LENGTH_SHORT).show();
        if (contador < alEjercicio.size()){
            verTipo(alEjercicio.get(contador).getcTipo(),
                    alEjercicio.get(contador).getsNombre(),
                    alEjercicio.get(contador).getsValor());
        }
        else{
             Date date = new Date();
            //Formateando la fecha:
            DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = formatoFecha.format(date) +" "+  formatoHora.format(date);
            datosGuardar ="|"+ fecha+";" + datosGuardar ;//|fecha;nombre;tipo;valor;nombre;....
           gestionFicheros.guardarFichero(getString(R.string.ficheroMisEntrenamientos),datosGuardar,this, this);
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
                 cronometroFragment = new CronometroFragment(EntrenamientoActivity.this);
                bundle.putString("nombre", nombre);
                bundle.putString("valor", valor);
                bundle.putInt("pbProgress", 0);
                cronometroFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.flContenedorEntrenamiento, cronometroFragment);
                transition.commit();

                break;

            //Cuenta atras
            case  'A':
                CuentaAtrasFragment cuentaAtrasFragment = new CuentaAtrasFragment(EntrenamientoActivity.this);
                bundle.putString("nombre", nombre);
                bundle.putString("valor", valor);
                bundle.putInt("pbProgress", 0);
                cuentaAtrasFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.flContenedorEntrenamiento, cuentaAtrasFragment);
                transition.commit();

                break;


           //Repeticiones
            case  'R':

                RepeticionesFragment repeticionesFragment = new RepeticionesFragment(EntrenamientoActivity.this);
                bundle.putString("nombre", nombre);
                bundle.putInt("valor", Integer.parseInt(valor));
                bundle.putInt("pbProgress", 0);
                repeticionesFragment.setArguments(bundle);
                transition  = getSupportFragmentManager().beginTransaction();
                transition.replace(R.id.flContenedorEntrenamiento, repeticionesFragment);
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

       String sEntrenamiento =  gestionFicheros.leerFichero( getString(R.string.ficheroEntrenamiento), this, context);

       String[]sId= sEntrenamiento.split(";");

        Ejercicio ejercicio;

        String texto = gestionFicheros.leerFichero(getString(R.string.ficheroEjercicios), this, context);

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


    @Override
    public void onDataRecived(String datos) {

        datosParcial = alEjercicio.get(contador).getsNombre() +";" + datos +";";

        Toast.makeText(context, datosParcial, Toast.LENGTH_SHORT).show();
    }
}