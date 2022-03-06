package com.example.proyectodam.ui;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepeticionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepeticionesFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "nombre";
    private static final String ARG_PARAM2 = "valor";
    private static final String ARG_PARAM3 = "progressBar";

    // TODO: Rename and change types of parameters
    private String nombre;
    private int valor;
    private int progressBar;

    private boolean bRunning;
    private long lPauseOffset;
    private FloatingActionButton fbBotonCronometroInicia;
    private FloatingActionButton fbBotonCronometroPausa;
    private FloatingActionButton fbBotonCronometroStop;
    private TextView tvRepeticiones;
    private ProgressBar pbProgress;
    private View v;
    private View view;
    private TextView tvNombre;

    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    TriggerEventListener triggerEventListener;


    public RepeticionesFragment() {
        // Required empty public constructor

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
        tvNombre  = (TextView) view.findViewById(R.id.tvNombreEjercicio);
        tvNombre.setText(nombre);

        tvRepeticiones = (TextView) view.findViewById(R.id.tvRepeticiones);
        tvRepeticiones.setText(""+valor);

        pbProgress = (ProgressBar) view.findViewById(R.id.pbProgress);
        pbProgress.setProgress(progressBar);

        lPauseOffset = 0;
        fbBotonCronometroStop.hide();
        fbBotonCronometroInicia.show();
        fbBotonCronometroPausa.hide();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_repeticiones, container, false);
        view = inflater.inflate(R.layout.activity_entrenamiento, container, false);



        fbBotonCronometroInicia = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroInicia);
        fbBotonCronometroPausa = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroPausa);
        fbBotonCronometroStop = (FloatingActionButton) v.findViewById(R.id.fbBotonCronometroStop);

        pbProgress = (ProgressBar) v.findViewById(R.id.pbProgress);


        pbProgress.setMax(60000);
        fbBotonCronometroPausa.hide();
        fbBotonCronometroStop.hide();



        return v;
    }


}