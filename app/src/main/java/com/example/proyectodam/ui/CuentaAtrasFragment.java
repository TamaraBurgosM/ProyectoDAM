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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaAtrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaAtrasFragment extends Fragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_VALOR = "valor";
    private static final String ARG_PROGRESO = "progressBar";

    private String nombre;
    private String valor;
    private int progressBar;

    private boolean bRunning;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private ProgressBar pbProgress;
    private long tiempoRestante = 60000;
    private TextView tvCuentaAtras;
    private CountDownTimer cdCuentaAtras;
    private  MediaPlayer mediaPlayer;


    public CuentaAtrasFragment() {
        // Required empty public constructor
    }


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
        TextView tvNombre = (TextView) view.findViewById(R.id.tvNombreEjercicio);
        tvNombre.setText(nombre);

        tvCuentaAtras = (TextView) view.findViewById(R.id.tvCuentaAtras);
        tvCuentaAtras.setText(valor);

        TextView etTiempoPrevio = (TextView) view.findViewById(R.id.etTiempoPrevio);
        etTiempoPrevio.setVisibility(View.VISIBLE);
        etTiempoPrevio.setText(valor);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vCuentaAtras = inflater.inflate(R.layout.fragment_cuenta_atras, container, false);
        View vEntrenamiento = inflater.inflate(R.layout.activity_entrenamiento, container, false);

        fbBotonCronometroInicia = (FloatingActionButton) vCuentaAtras.findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) vCuentaAtras.findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) vCuentaAtras.findViewById(R.id.fbBotonCronometroStop);
        FloatingActionButton fbNext = (FloatingActionButton) vEntrenamiento.findViewById(R.id.fbNext);

        tiempoRestante = traducirTiempo(valor);

        pbProgress = (ProgressBar) vCuentaAtras.findViewById(R.id.pbProgress);

        pbProgress.setMax((int) tiempoRestante);
        pbProgress.setProgress(progressBar);

        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();

         mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm);


        fbBotonCronometroInicia.setOnClickListener(v -> inicioCronometro());

        fbBotonCronometroPausa.setOnClickListener(v -> pausaCronometro());


        fbBotonCronometroStop.setOnClickListener(v -> finCronometro());
        return vCuentaAtras;


    }
    public void inicioCronometro(){
        if(!bRunning) {
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

            fbBotonCronometroInicia.hide();
            fbBotonCronometroPausa.show();
            fbBotonCronometroStop.show();
            bRunning = true;


        }
    }
    public void finCronometro(){

        cdCuentaAtras.cancel();
        tvCuentaAtras.setText(valor);
        tiempoRestante = traducirTiempo(valor);

        pbProgress.setProgress(0);
        bRunning = false;
        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

        mediaPlayer.stop();



    }
    public void pausaCronometro(){
        if (bRunning){
            cdCuentaAtras.cancel();

            bRunning = false;
            fbBotonCronometroInicia.show();
            fbBotonCronometroPausa.hide();
            mediaPlayer.stop();
        }


    }
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

    public long traducirTiempo (String tiempo){

        String[] split = tiempo.split(":");
        int minutos = Integer.parseInt(split[0]) ;

        int segundos = Integer.parseInt(split[1]) ;

        return minutos * 60000L + segundos * 1000L;

    }


}