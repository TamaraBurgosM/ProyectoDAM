package com.example.proyectodam.Model;

import java.util.ArrayList;

public class Entrenamiento {

    public int iId;
    public String sNombre;
    public ArrayList <Ejercicio> aEjercicio;

    public Entrenamiento() {
        this.iId = iId;
        this.sNombre = sNombre;
        this.aEjercicio = aEjercicio;
    }

    public Entrenamiento(int iId, String sNombre, ArrayList<Ejercicio> aEjercicio) {
        this.iId = iId;
        this.sNombre = sNombre;
        this.aEjercicio = aEjercicio;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
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
