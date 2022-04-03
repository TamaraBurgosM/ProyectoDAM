package com.example.proyectodam.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
 * Use the {@link RepeticionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepeticionesFragment extends Fragment  {
    PassDataI passData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "nombre";
    private static final String ARG_PARAM2 = "valor";
    private static final String ARG_PARAM3 = "progressBar";

    // TODO: Rename and change types of parameters
    private String nombre;
    private int valor;
    private int progressBar;

    private FloatingActionButton fbBotonRepeticionesInicia;
    private FloatingActionButton fbBotonRepeticionesPausa;
    private FloatingActionButton fbBotonRepeticionesStop;
    private TextView tvRepeticiones;
    private ProgressBar pbProgress;
    private int iContadorRepeticiones =0;
    private MediaPlayer mediaPlayer;
    private boolean bRunning=false;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double accelerationPreviousValue;
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float  x = event.values[0];
            float  y = event.values[1];
            float  z = event.values[2];

            double accelerationCurrentValue = Math.sqrt((x * x + y * y + z * z));

            double changeInAccelleration = Math.abs(accelerationCurrentValue -accelerationPreviousValue);

            accelerationPreviousValue = accelerationCurrentValue;

            if (changeInAccelleration > 8 && bRunning)
            {
                iContadorRepeticiones++;
                tvRepeticiones.setText(String.valueOf(iContadorRepeticiones));
                pbProgress.setProgress(pbProgress.getProgress()+1);
                if(iContadorRepeticiones == valor ){
                    mediaPlayer.start();
                    pbProgress.setProgress(0);
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public RepeticionesFragment() {
        // Required empty public constructor

    }
    public RepeticionesFragment(PassDataI passData) {
        this.passData = passData;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre Parameter 1.
     * @param valor Parameter 2.
     * @return A new instance of fragment RepeticionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepeticionesFragment newInstance(String nombre, int valor, int progressBar) {
        RepeticionesFragment fragment = new RepeticionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nombre);
        args.putInt(ARG_PARAM2, valor);
        args.putInt(ARG_PARAM3, progressBar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombre = getArguments().getString(ARG_PARAM1);
            valor = getArguments().getInt(ARG_PARAM2);
            progressBar = getArguments().getInt(ARG_PARAM3);
        }

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        TextView tvNombre  = (TextView) view.findViewById(R.id.tvNombreEjercicio);
        tvNombre.setText(nombre);

        tvRepeticiones = (TextView) view.findViewById(R.id.tvRepeticiones);
        tvRepeticiones.setText(String.valueOf(valor));


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vRepeticiones = inflater.inflate(R.layout.fragment_repeticiones, container, false);
        View vEntrenamiento = inflater.inflate(R.layout.activity_entrenamiento, container, false);

        fbBotonRepeticionesInicia = (FloatingActionButton) vRepeticiones.findViewById(R.id.fabBotonRepeticionesInicia);
        fbBotonRepeticionesPausa = (FloatingActionButton) vRepeticiones.findViewById(R.id.fabBotonRepeticionesPausa);
        fbBotonRepeticionesStop = (FloatingActionButton) vRepeticiones.findViewById(R.id.fabBotonRepeticionesStop);

        pbProgress = (ProgressBar) vRepeticiones.findViewById(R.id.pbProgress);
        pbProgress.setMax(valor);
        pbProgress.setProgress(progressBar);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm);

        fbBotonRepeticionesInicia.setOnClickListener(v -> inicioRepeticiones());

        fbBotonRepeticionesPausa.setOnClickListener(v -> pausaRepeticiones());

        fbBotonRepeticionesStop.setOnClickListener(v -> finRepeticiones());


        fbBotonRepeticionesPausa.hide();
        fbBotonRepeticionesStop.hide();
        fbBotonRepeticionesInicia.show();

        return vRepeticiones;
    }

    public void inicioRepeticiones(){

        tvRepeticiones.setText(String.valueOf( iContadorRepeticiones));
        fbBotonRepeticionesPausa.show();
        fbBotonRepeticionesStop.show();
        fbBotonRepeticionesInicia.hide();

        bRunning= true;


    }
    public void finRepeticiones(){

        mediaPlayer.stop();
        iContadorRepeticiones = 0;

        pbProgress.setProgress(0);
        bRunning = false;
        fbBotonRepeticionesStop.hide();
        fbBotonRepeticionesInicia.show();
        fbBotonRepeticionesPausa.hide();

        fbBotonRepeticionesPausa.hide();
        fbBotonRepeticionesInicia.show();

        passData.onDataRecived("R;"+ iContadorRepeticiones);

    }

    public void pausaRepeticiones(){

        mediaPlayer.stop();

        bRunning= false;

        fbBotonRepeticionesPausa.hide();
        fbBotonRepeticionesInicia.show();

        passData.onDataRecived("R;"+ iContadorRepeticiones );


    }

    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

}