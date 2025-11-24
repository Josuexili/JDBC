package com.esports.model;

public class Esport {

    private int cod;
    private String nombre;

    public Esport(String nombre) {
        this.nombre = nombre;
    }

    public Esport(int cod, String nombre) {
        this.cod = cod;
        this.nombre = nombre;
    }

    public int getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return cod + " - " + nombre;
    }
}
