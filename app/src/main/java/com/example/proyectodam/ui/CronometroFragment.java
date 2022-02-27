package com.example.proyectodam.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proyectodam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CronometroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CronometroFragment extends Fragment {


    private Chronometer chCronometro;
    private boolean bRunning;
    private long lPauseOffset;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private TextView etTiempoPrevio;
    private ProgressBar pbProgress;
    private View v;


    public CronometroFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CronometroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CronometroFragment newInstance(String param1, String param2) {
        CronometroFragment fragment = new CronometroFragment();
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

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cronometro, container, false);

        chCronometro = (Chronometer) v.findViewById(R.id.chCronometro);
        fbBotonCronometroInicia = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroStop);
        pbProgress = (ProgressBar) v.findViewById(R.id.pbProgress);


        pbProgress.setMax(60000);
        pbProgress.setProgress(0);

        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();

        chCronometro.setFormat("Time: %");
        chCronometro.setBase(SystemClock.elapsedRealtime());

        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                pbProgress.setProgress( pbProgress.getProgress() + 1000);
                if((SystemClock.elapsedRealtime() - chronometer.getBase())>= 60000){
                    // chronometer.setBase(SystemClock.elapsedRealtime());
                    pbProgress.setProgress(0);

                }
            }
        });

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
            chCronometro.setBase(SystemClock.elapsedRealtime() - lPauseOffset);
            chCronometro.start();
            fbBotonCronometroInicia.hide();
            fbBotonCronometroPausa.show();
            fbBotonCronometroStop.show();
            bRunning = true;


        }
    }
    public void finCronometro(View v){


        chCronometro.setBase(SystemClock.elapsedRealtime());
        lPauseOffset = 0;
        pbProgress.setProgress(0);
        bRunning = false;
        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

    }
    public void pausaCronometro(View v){
        if (bRunning){
            chCronometro.stop();
            lPauseOffset = SystemClock.elapsedRealtime() - chCronometro.getBase();
            bRunning = false;
            fbBotonCronometroInicia.show();
            fbBotonCronometroPausa.hide();

        }

    }


}