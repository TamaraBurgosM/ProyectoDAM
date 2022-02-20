package com.example.proyectodam;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CronometroActivity extends AppCompatActivity {

  private Chronometer chCronometro;
  private boolean bRunning;
  private long lPauseOffset;
  private FloatingActionButton fbBotonCronometroInicia;
  private FloatingActionButton fbBotonCronometroPausa;
  private FloatingActionButton fbBotonCronometroStop;
  private TextView etTiempoPrevio;
  private ProgressBar pbProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cronometro);
        super.onCreate(savedInstanceState);

        setUpView();

    }


    private void setUpView(){
          etTiempoPrevio = (TextView) findViewById(R.id.etTiempoPrevio);

        chCronometro = (Chronometer) findViewById(R.id.chCronometro);
        fbBotonCronometroInicia = (FloatingActionButton) findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) findViewById(R.id.fbBotonCronometroStop);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);

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