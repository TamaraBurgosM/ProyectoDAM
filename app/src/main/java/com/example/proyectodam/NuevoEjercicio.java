package com.example.proyectodam;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

public class NuevoEjercicio extends AppCompatActivity {


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);
      //  RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.main_content);
       // setSupportActionBar(toolbar);
    }

   // private void setSupportActionBar(RelativeLayout toolbar) {
   // }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_cuantaAtras:
                if (checked)

                    break;
            case R.id.radio_Repeticiones:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_cronometro:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}