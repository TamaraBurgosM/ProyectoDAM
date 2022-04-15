package com.example.proyectodam.ui;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proyectodam.R;
import com.example.proyectodam.interfaces.PassDataI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaAtrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaAtrasFragment extends Fragment {
    PassDataI passData;

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_VALOR = "valor";
    private static final String ARG_PROGRESO = "progressBar";

    private String nombre;
    private String valor;
    private int progressBar;

    private boolean bRunning;
    private FloatingActionButton fbBotonCuentaAtrasInicia;
    private FloatingActionButton fbBotonCuentaAtrasPausa;
    private FloatingActionButton fbBotonCuentaAtrasStop;
    private ProgressBar pbProgress;
    private long tiempoRestante = 60000;
    private TextView tvCuentaAtras;
    private CountDownTimer cdCuentaAtras;
    private  MediaPlayer mediaPlayer;


    public CuentaAtrasFragment() {
        // Required empty public constructor
    }

    public CuentaAtrasFragment(PassDataI passData) {
        this.passData = passData;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre ARG_NOMBRE.
     * @param valor ARG_VALOR.
     * @param progressBar ARG_PROGRESO.
     * @return A new instance of fragment CuentaAtrasFragment.
     */
    public static CuentaAtrasFragment newInstance(String nombre, String valor, int progressBar) {
        CuentaAtrasFragment fragment = new CuentaAtrasFragment();
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

        tvCuentaAtras =  view.findViewById(R.id.tvCuentaAtras);
        tvCuentaAtras.setText(valor);

        TextView etTiempoPrevio =  view.findViewById(R.id.tvTiempoPrevio);
        etTiempoPrevio.setVisibility(View.VISIBLE);
        etTiempoPrevio.setText(valor);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vCuentaAtras = inflater.inflate(R.layout.fragment_cuenta_atras, container, false);

        fbBotonCuentaAtrasInicia =   vCuentaAtras.findViewById(R.id.fabBotonCuentaAtrasInicia);
        fbBotonCuentaAtrasPausa =  vCuentaAtras.findViewById(R.id.fabBotonCuentaAtrasPausa);
        fbBotonCuentaAtrasStop =  vCuentaAtras.findViewById(R.id.fabBotonCuentaAtrasStop);

        tiempoRestante = traducirTiempo(valor);

        pbProgress =  vCuentaAtras.findViewById(R.id.pbProgress);

        pbProgress.setMax((int) tiempoRestante);
        pbProgress.setProgress(progressBar);

        fbBotonCuentaAtrasPausa.hide();
        fbBotonCuentaAtrasStop.hide();

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm);

        fbBotonCuentaAtrasInicia.setOnClickListener(v -> inicioCuentaAtras());

        fbBotonCuentaAtrasPausa.setOnClickListener(v -> pausaCuentaAtras());

        fbBotonCuentaAtrasStop.setOnClickListener(v -> finCuentaAtras());
        return vCuentaAtras;


    }

    /**
     * Metodo boton inicio
     */
    public void inicioCuentaAtras(){
        if(!bRunning) {
            bRunning = true;
            cdCuentaAtras = new CountDownTimer(tiempoRestante, 1000) {

                public void onTick(long l) {

                    tiempoRestante = l;
                    actualizarTiempo();
                    pbProgress.setProgress(pbProgress.getProgress()+1000);
                }

                public void onFinish() {

                    mediaPlayer.start();
                }

            }.start();

            fbBotonCuentaAtrasInicia.hide();
            fbBotonCuentaAtrasPausa.show();
            fbBotonCuentaAtrasStop.show();


        }
    }

    /**
     * Metodo boton fin
     */
    public void finCuentaAtras(){

        //Paramos la aplicacion
        bRunning = false;
        mediaPlayer.stop();
        cdCuentaAtras.cancel();

        //Guardamos los datos
        String tiempoAGuardar = traducirTiempo(traducirTiempo(valor)-(tiempoRestante-1000));
        passData.onDataRecived("A;"+tiempoAGuardar);

        //Reiniciamos datos
        tvCuentaAtras.setText(valor);
        tiempoRestante = traducirTiempo(valor);
        pbProgress.setProgress(0);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm);

        fbBotonCuentaAtrasStop.hide();
        fbBotonCuentaAtrasInicia.show();
        fbBotonCuentaAtrasPausa.hide();

    }

    /**
     * Metodo pausa
     */
    public void pausaCuentaAtras(){

        cdCuentaAtras.cancel();

        bRunning = false;
        fbBotonCuentaAtrasInicia.show();
        fbBotonCuentaAtrasPausa.hide();
        mediaPlayer.stop();
        String tiempoAGuardar = traducirTiempo(traducirTiempo(valor)-(tiempoRestante-1000));
        passData.onDataRecived("A;"+tiempoAGuardar);



    }

    /**
     * Metodo actualizar tiempo
     */
    public void actualizarTiempo(){
        int iMinutos = (int) tiempoRestante /60000;
        int iSegundos = (int) tiempoRestante % 60000 /1000;
        String sTiempo= "" + iMinutos + ":" ;

        if (iSegundos < 10) {
            sTiempo+="0";
        }

        sTiempo += iSegundos;
        tvCuentaAtras.setText(sTiempo);

    }

    /**
     * Metodo para traducir el tiempo de un texto minutos:segundos
     * a lectura por la maquina
     * @param tiempo tiempo en minutos y segundos
     * @return tiempo en formato long
     */
    public long traducirTiempo (String tiempo){

        String[] split = tiempo.split(":");
        int minutos = Integer.parseInt(split[0]) ;
        int segundos = Integer.parseInt(split[1]) ;
        return minutos * 60000L + segundos * 1000L;

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