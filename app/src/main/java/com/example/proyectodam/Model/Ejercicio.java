package com.example.proyectodam.Model;

public class Ejercicio {

    public int iId=0;
    public String sNombre="";
    public char cTipo= (char) 0;
    public String sValor="";

    public Ejercicio() {
        this.iId = iId;
        this.sNombre = sNombre;
        this.cTipo = cTipo;
        this.sValor = sValor;
    }
    public Ejercicio(int iId, String sNombre, char cTipo, String sValor) {
        this.iId = iId;
        this.sNombre = sNombre;
        this.cTipo = cTipo;
        this.sValor = sValor;
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

    public char getcTipo() {
        return cTipo;
    }

    public void setcTipo(char cTipo) {
        this.cTipo = cTipo;
    }

    public String getsValor() {
        return sValor;
    }

    public void setsValor(String sValor) {
        this.sValor = sValor;
    }
}
