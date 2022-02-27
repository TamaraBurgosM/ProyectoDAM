package com.example.proyectodam.ui;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proyectodam.EntrenamientoActivity;
import com.example.proyectodam.MainActivity;
import com.example.proyectodam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaAtrasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaAtrasFragment extends Fragment {

    private boolean bRunning;
    private long lPauseOffset;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private TextView etTiempoPrevio;
    private ProgressBar pbProgress;
    private View v;
    private String sTiempo = "01:00";
    private long tiempoRestante =60000;
    private TextView tvCuentaAtras;
    private AlarmManager alarmManager;
    private CountDownTimer cdCuentaAtras;


    public CuentaAtrasFragment() {
        // Required empty public constructor
    }


    public static CuentaAtrasFragment newInstance(String param1, String param2) {
        CuentaAtrasFragment fragment = new CuentaAtrasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cuenta_atras, container, false);

      /*  Intent intent = new Intent(getActivity(), EntrenamientoActivity.class);
        startActivity(intent);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);*/


        tvCuentaAtras = (TextView) v.findViewById(R.id.tvCuentaAtras);
        tvCuentaAtras.setText(sTiempo);

        etTiempoPrevio = (TextView) v.findViewById(R.id.etTiempoPrevio) ;
        etTiempoPrevio.setVisibility(View.VISIBLE);
        etTiempoPrevio.setText(sTiempo);

        fbBotonCronometroInicia = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroStop);

        tiempoRestante = traducirTiempo(sTiempo);

        pbProgress = (ProgressBar) v.findViewById(R.id.pbProgress);

        pbProgress.setMax((int) tiempoRestante);
        pbProgress.setProgress(0);

        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();

        fbBotonCronometroInicia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inicioCronometro(v);
            }
        });

        fbBotonCronometroPausa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pausaCronometro(v);
            }
        });


        fbBotonCronometroStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finCronometro(v);
            }
        });
        return v;


    }
    public void inicioCronometro(View v){
        if(!bRunning) {
            cdCuentaAtras = new CountDownTimer(tiempoRestante, 1000) {

                public void onTick(long l) {

                    tiempoRestante = l;
                    actualizarTiempo();
                    pbProgress.setProgress(pbProgress.getProgress()+1000);
                }

                public void onFinish() {
                    // mTextField.setText("done!");
                    // MediaPlayer mp = MediaPlayer.create(getBaseContext(), Notification.DEFAULT_SOUND); //replace 'sound' by your music/sound
                    // mp.start();
                    //AlarmManager  alarmMgr = (AlarmManager)contexto.getSystemService(Context.ALARM_SERVICE);
                    // alarmMgr.setRepeating();
                }

            }.start();

            fbBotonCronometroInicia.hide();
            fbBotonCronometroPausa.show();
            fbBotonCronometroStop.show();
            bRunning = true;


        }
    }
    public void finCronometro(View v){

        cdCuentaAtras.cancel();
        tvCuentaAtras.setText(sTiempo);
        tiempoRestante = traducirTiempo(sTiempo);

        pbProgress.setProgress(0);
        bRunning = false;
        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

    }
    public void pausaCronometro(View v){
        if (bRunning){
            cdCuentaAtras.cancel();

            bRunning = false;
            fbBotonCronometroInicia.show();
            fbBotonCronometroPausa.hide();

        }

    }
    public void actualizarTiempo(){
        int minutos = (int) tiempoRestante /60000;
        int segundos = (int) tiempoRestante % 60000 /1000;
        String sTiempo= "" + minutos + ":" ;

        if (segundos <10) {
            sTiempo+="0";
        }

        sTiempo += segundos;
        tvCuentaAtras.setText(sTiempo);


    }

    public long traducirTiempo (String tiempo){

        String[] split = tiempo.split(":");
        int minutos = Integer.valueOf(split[0]) ;

        int segundos = Integer.valueOf(split[1]) ;

        return minutos* 60000 +segundos* 1000;

    }
}