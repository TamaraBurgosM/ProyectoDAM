package com.example.proyectodam.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proyectodam.R;
import com.example.proyectodam.interfaces.PassDataI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CronometroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CronometroFragment extends Fragment {
    PassDataI passData;

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_VALOR = "valor";
    private static final String ARG_PROGRESO = "progressBar";

    private String nombre;
    private String valor;
    private int progressBar;

    private Chronometer chCronometro;
    private boolean bRunning;
    private long lPauseOffset;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private ProgressBar pbProgress;

    public CronometroFragment(PassDataI passData) {
        this.passData = passData;

    }

    public CronometroFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre ARG_NOMBRE.
     * @param valor ARG_VALOR.
     * @param progressBar ARG_PROGRESO.
     * @return A new instance of fragment CronometroFragment.
     */
    public static CronometroFragment newInstance(String nombre, String valor, int progressBar) {
        CronometroFragment fragment = new CronometroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_VALOR, valor);
        args.putInt(ARG_PROGRESO, progressBar);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString(ARG_NOMBRE);
            valor = getArguments().getString(ARG_VALOR);
            progressBar = getArguments().getInt(ARG_PROGRESO);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvNombre =  view.findViewById(R.id.tvNombreEjercicio);
        tvNombre.setText(nombre);

        TextView tvPausa =  view.findViewById(R.id.tvPausa);
        tvPausa.setText(valor);

        pbProgress =  view.findViewById(R.id.pbProgress);
        pbProgress.setProgress(progressBar);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vCronometro = inflater.inflate(R.layout.fragment_cronometro, container, false);
        chCronometro =  vCronometro.findViewById(R.id.chCronometro);
        fbBotonCronometroInicia =   vCronometro.findViewById(R.id.fabBotonCronometroInicia);
        fbBotonCronometroPausa =  vCronometro.findViewById(R.id.fabBotonCronometroPausa);
        fbBotonCronometroStop =  vCronometro.findViewById(R.id.fabBotonCronometroStop);
        pbProgress = vCronometro.findViewById(R.id.pbProgress);

        pbProgress.setProgress(0);
        pbProgress.setMax(60000);

        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();
        chCronometro.stop();

        chCronometro.setFormat("Time: %");
        chCronometro.setBase(SystemClock.elapsedRealtime());


        chCronometro.setOnChronometerTickListener(chronometer -> {
            pbProgress.setProgress(pbProgress.getProgress() + 1000);
            if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 60000) {

                pbProgress = vCronometro.findViewById(R.id.pbProgress);
                pbProgress.setProgress(0);
                pbProgress.setMax(60000);
                lPauseOffset = 0;

            }
        });



        fbBotonCronometroInicia.setOnClickListener(v -> inicioCronometro());

        fbBotonCronometroPausa.setOnClickListener(v -> pausaCronometro());

        fbBotonCronometroStop.setOnClickListener(v -> finCronometro());

        return vCronometro;
    }


    /**
     * Metodo inicio Cronometro
     */
    public void inicioCronometro(){
        if(!bRunning) {
            chCronometro.setBase(SystemClock.elapsedRealtime() - lPauseOffset);
            chCronometro.start();
            fbBotonCronometroInicia.hide();
            fbBotonCronometroPausa.show();
            fbBotonCronometroStop.show();
            bRunning = true;

        }
    }

    /**
     * Metodo boton fin Cronometro
     */
    public void finCronometro(){

        //Paramos la aplicacion
        bRunning = false;
        chCronometro.stop();

        //Guardamos los datos
        lPauseOffset = SystemClock.elapsedRealtime() - chCronometro.getBase();
        passData.onDataRecived("C;"+traducirTiempo(lPauseOffset) );

        //Reiniciamos valores
        chCronometro.setBase(SystemClock.elapsedRealtime());
        lPauseOffset = 0;
        pbProgress.setProgress(0);

        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

    }

    /**
     * Metodo boton pausa Cronometro
     */
    public void pausaCronometro(){

        chCronometro.stop();

        lPauseOffset = SystemClock.elapsedRealtime() - chCronometro.getBase();
        bRunning = false;
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

        passData.onDataRecived("C;"+traducirTiempo(lPauseOffset) );


    }

    /**
     * Metodo de traduccion del tiempo a minutos y segundos
     * @param tiempo tiempo almacenado por el cronometro
     * @return texto con minutos y segundos separados por dos puntos
     */
    public String traducirTiempo (long tiempo){

        long minutos = tiempo / 60000L;
        long segundos = (tiempo % 60000L)/1000L;
        if(segundos<10)
            return minutos+":0"+segundos  ;

        return minutos+":"+segundos  ;

    }

}