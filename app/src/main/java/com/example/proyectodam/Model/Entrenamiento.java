package com.example.proyectodam.model;

import java.util.ArrayList;
/**
 * Clase modelo Entrenamiento
 * */
public class Entrenamiento {

    public String iId;
    public String sNombre;
    public ArrayList <Ejercicio> aEjercicio;

    public Entrenamiento() {
        this.iId = iId;
        this.sNombre = sNombre;
        this.aEjercicio = aEjercicio;
    }

    public Entrenamiento(String iId, String sNombre, ArrayList<Ejercicio> aEjercicio) {
        this.iId = iId;
        this.sNombre = sNombre;
        this.aEjercicio = aEjercicio;
    }
    public Entrenamiento(String ejercicio) {

    }

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getsNombre() {
        return sNombre;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public ArrayList<Ejercicio> getaEjercicio() {
        return aEjercicio;
    }

    public void setaEjercicio(ArrayList<Ejercicio> aEjercicio) {
        this.aEjercicio = aEjercicio;
    }
}
