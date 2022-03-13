package com.example.proyectodam;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

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

  /*  private boolean validarPermisoEscritura(Activity activity){
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
    }*/

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




        /*int permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
            return false;
        } else {
            return true;
        }*/
    }

    public String leerFichero(String nombreFichero, Activity activity, Context c) {
        StringBuilder datos = new StringBuilder("");
        boolean permiso = validarPermiso(activity);

            if (permiso) {
               // if(archivoExiste(nombreFichero)) {

                try {
                    FileInputStream fis = c.openFileInput(nombreFichero);
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

                    //   datos= stringBuilder.toString();
                    //System.out.println("RUTA: "+ c.getPackageResourcePath());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
      //  }
        return datos.toString();
    }


    public void guardarFichero(String nombreFichero, String entrada, Activity activity, Context c) {

        String datos = leerFichero(nombreFichero,activity,c) + entrada  ;

        boolean permiso = validarPermiso(activity);
       if(permiso) {
           try (FileOutputStream fos = c.openFileOutput(nombreFichero, Context.MODE_PRIVATE)) {
               fos.write(datos.getBytes(StandardCharsets.UTF_8));

           } catch (IOException e) {
               e.printStackTrace();
           }
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

       // }


    }
    public void guardarFicheroSimple(String nombreFichero, String datos, Activity activity, Context c) {

        boolean permiso = validarPermiso(activity);
        if(permiso) {
            try (FileOutputStream fos = c.openFileOutput(nombreFichero, Context.MODE_PRIVATE)) {
                fos.write(datos.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private boolean archivoExiste(String nombreFichero) {

        File fichero = new File(direccion+"/"+nombreFichero);
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

