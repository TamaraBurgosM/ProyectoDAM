package com.example.proyectodam;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GestionFicheros {

    /**
     * Pedimos permisos al usuario de lectura/escritura
     * @param activity Activity
     */
    private void pedirPermiso(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,}, 2);
        }else {
            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,}, 2);
        }
    }

    /**
     * Validamos que el usuario tiene permiso de Lectura/escritura
     * @param activity Activity
     * @return true si tiene permiso
     */
    private boolean validarPermiso(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;

            } else {

                pedirPermiso(activity);
            }
        }
        return true;

    }

    /**
     * Leemos el fichero
     * @param nombreFichero nombre del fichero
     * @param activity Activity que rellenamos
     * @param context contexto
     * @return texto del fichero
     */
    public String leerFichero(String nombreFichero, Activity activity, Context context) {
        StringBuilder datos = new StringBuilder();
        boolean permiso = validarPermiso(activity);

            if (permiso) {
                try {
                    FileInputStream fis = context.openFileInput(nombreFichero);
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    datos = line != null ? datos.append("") : datos.append("No hay datos");

                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        datos.append(line);
                        line = reader.readLine();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        return datos.toString();
    }

    /**
     * Guerdar fichero
     * @param nombreFichero nombre del fichero a guardar
     * @param entrada datos para almacenar
     * @param activity Activity
     * @param context contexto
     */
    public void guardarFichero(String nombreFichero, String entrada, Activity activity, Context context) {
        String datos = leerFichero(nombreFichero,activity,context) + entrada  ;

       boolean permiso = validarPermiso(activity);
       if(permiso) {
           try (FileOutputStream fos = context.openFileOutput(nombreFichero, Context.MODE_PRIVATE)) {
               fos.write(datos.getBytes(StandardCharsets.UTF_8));

           } catch (IOException e) {
               e.printStackTrace();
           }
       }


    }

    /**
     * Guardar fichero simple, contendrá solo una linea de información
     * @param nombreFichero nombre del fichero
     * @param datos datos a guardar
     * @param activity Activity
     * @param context Contexto
     */
    public void guardarFicheroSimple(String nombreFichero, String datos, Activity activity, Context context) {

        boolean permiso = validarPermiso(activity);
        if(permiso) {
            try (FileOutputStream fos = context.openFileOutput(nombreFichero, Context.MODE_PRIVATE)) {
                fos.write(datos.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Limpieza de ficheros existentes
     * solo se ejecuta al instalar la aplicación
     * borrando datos previos que pudieran ser erroneos
     * @param context contexto
     */
    public void limpiarArchivos(Context context) {

        context.deleteFile(context.getString(R.string.ficheroEntrenamiento));
        context.deleteFile(context.getString(R.string.ficheroEjercicios));
        context.deleteFile(context.getString(R.string.ficheroMisEntrenamientos));

    }

}

