package com.example.proyectodam;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CuentaAtras extends AppCompatActivity {

    private boolean bRunning;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private TextView etTiempoPrevio;
    private String sTiempo = "01:00";
    private long tiempoRestante =60000;
    private TextView tvCuentaAtras;
    AlarmManager alarmManager;
    Context contexto = this;


    private CountDownTimer cdCuentaAtras;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_atras);

        setUpView();

    }


    private void setUpView(){
        alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        tvCuentaAtras = (TextView) findViewById(R.id.tvCuentaAtras);
        tvCuentaAtras.setText(sTiempo);

        etTiempoPrevio = (TextView) findViewById(R.id.etTiempoPrevio) ;
        etTiempoPrevio.setVisibility(View.VISIBLE);
        etTiempoPrevio.setText(sTiempo);

        fbBotonCronometroInicia = (FloatingActionButton) findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) findViewById(R.id.fbBotonCronometroStop);

        tiempoRestante = traducirTiempo(sTiempo);

        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);

        pbProgress.setMax((int) tiempoRestante);
        pbProgress.setProgress(0);

        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();


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
            //tvCuentaAtras.setText(sTiempo);

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