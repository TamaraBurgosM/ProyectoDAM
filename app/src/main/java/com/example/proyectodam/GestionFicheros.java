package com.example.proyectodam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class GestionFicheros {


    public void leerFichero(String nombreFichero) {


        if (ArchivoExiste(nombreFichero)) {
            try {
                File fichero = new File (String.valueOf(R.string.ruta),nombreFichero);
                FileInputStream fIn = new FileInputStream(fichero);
                InputStreamReader archivo = new InputStreamReader(fIn);
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            crearFichero(nombreFichero);
        }
    }


    public void guardarFichero(String nombreFichero, String datos) {
        try {
            File fichero = new File (String.valueOf(R.string.ruta),nombreFichero);
            OutputStreamWriter archivo = new OutputStreamWriter(
                    new FileOutputStream(fichero));
            //OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nombreFichero));
            archivo.write(datos);
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
     * Metodo que crea el archivo si aun no existe
     * */
    private void crearFichero(String nombreArchivo) {

        File fichero = new File (String.valueOf(R.string.ruta),nombreArchivo);
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

        String archivos[] = {"",""};

        for (String nombre : archivos){
            if (nombreFichero.equals(nombre)) {
                return true;
            }

        }

        return false;

    }
}

