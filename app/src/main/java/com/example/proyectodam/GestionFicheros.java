package com.example.proyectodam;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GestionFicheros {

    static String direccion = Environment.getExternalStorageDirectory().toString();

    private boolean validarPermisoEscritura(Activity activity){
        int permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para escribir.");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Se tiene permiso para  escribir!");
            return true;
        }
    }

    private boolean validarPermisoLectura(Activity activity){
        int permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
            return true;
        }
    }

    public String leerFichero(String nombreFichero, Activity activity, Context c) {

        StringBuilder datos= new StringBuilder("No hay datos");

        if (validarPermisoLectura(activity)) {

            try {
                FileInputStream fis = c.openFileInput(nombreFichero);
                InputStreamReader inputStreamReader =
                        new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    datos.append(line);
                    line = reader.readLine();
                }

                 //   datos= stringBuilder.toString();
                System.out.println("RUTA: "+ c.getPackageResourcePath());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return datos.toString();
    }


    public void guardarFichero(String nombreFichero, String datos, Activity activity, Context c) {


        if(validarPermisoEscritura(activity)) {
             try (FileOutputStream fos = c.openFileOutput(nombreFichero, Context.MODE_PRIVATE)) {
                    fos.write(datos.getBytes(StandardCharsets.UTF_8));

                } catch (IOException e) {
                    e.printStackTrace();
                }

           /* BufferedWriter out = null;

                try  {


                    out = new BufferedWriter(new OutputStreamWriter(c.openFileOutput(nombreFichero, Context.MODE_PRIVATE)));
                    out.write(datos);
                    //fos.write(datos.getBytes(StandardCharsets.UTF_8));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

        }


    }

    /*
     * Metodo que crea el archivo si aun no existe
     * */
    private void crearFichero(String nombreFichero) {

        File fichero = new File(direccion+"/"+nombreFichero);
        try {
            fichero.createNewFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
     * Metodo que comprueba que el archivo existe en nuesto directorio
     * */
    private boolean ArchivoExiste(String nombreFichero) {

        File fichero = new File(direccion+"/"+nombreFichero);

        //FileOutputStream fileOutputStream = null;
        if(!fichero.exists()){
            try {
                fichero.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

        /*String archivos[] = {"",""};

        for (String nombre : archivos){
            if (nombreFichero.equals(nombre)) {
                return true;
            }

        }

        return false;*/

    }


}

