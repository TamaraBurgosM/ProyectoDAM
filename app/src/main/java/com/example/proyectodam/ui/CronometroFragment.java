package com.example.proyectodam.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.preference.PreferenceManager;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "nombre";
    private static final String ARG_PARAM2 = "valor";
    private static final String ARG_PARAM3 = "progressBar";

    // TODO: Rename and change types of parameters
    private String nombre;
    private String valor;
    private int progressBar;



    private Chronometer chCronometro;
    private boolean bRunning;
    private long lPauseOffset;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private FloatingActionButton fbNext;
    private TextView tvPausa;
    private ProgressBar pbProgress;
    private View v;
    private View view;
    private TextView tvNombre;

    public CronometroFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre Parameter 1.
     * @param valor Parameter 2.
     * @return A new instance of fragment CronometroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CronometroFragment newInstance(String nombre, String valor, int progressBar) {
        CronometroFragment fragment = new CronometroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nombre);
        args.putString(ARG_PARAM2, valor);
        args.putInt(ARG_PARAM3, progressBar);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString(ARG_PARAM1);
            valor = getArguments().getString(ARG_PARAM2);
            progressBar = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNombre  = (TextView) view.findViewById(R.id.tvNombreEjercicio);
        tvNombre.setText(nombre);

        tvPausa = (TextView) view.findViewById(R.id.tvPausa);
        tvPausa.setText(valor);

        pbProgress = (ProgressBar) view.findViewById(R.id.pbProgress);
        pbProgress.setProgress(progressBar);

       /* chCronometro.setBase(SystemClock.elapsedRealtime());
        lPauseOffset = 0;
        pbProgress.setMin(0);
        bRunning = false;
        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cronometro, container, false);
        view = inflater.inflate(R.layout.activity_entrenamiento, container, false);
        chCronometro = (Chronometer) v.findViewById(R.id.chCronometro);
        fbBotonCronometroInicia = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroStop);
        fbNext = (FloatingActionButton) view.findViewById(R.id.fbNext);
        pbProgress = (ProgressBar) v.findViewById(R.id.pbProgress);



        pbProgress.setProgress(0);
        pbProgress.setMax(60000);
        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();
        chCronometro.stop();

        chCronometro.setFormat("Time: %");
        chCronometro.setBase(SystemClock.elapsedRealtime());

        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                pbProgress.setProgress( pbProgress.getProgress() + 1000);
                if((SystemClock.elapsedRealtime() - chronometer.getBase())>= 60000){

                    pbProgress.setProgress(0);
                    lPauseOffset = 0;

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
            fbNext.hide();
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

        fbNext.show();

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