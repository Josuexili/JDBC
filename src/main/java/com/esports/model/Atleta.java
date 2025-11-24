package com.esports.model;

public class Atleta {

    private int cod;
    private String nombre;
    private int codDeporte;

    public Atleta(String nombre) {
        this.nombre = nombre;
    }

    public Atleta(int cod, String nombre, int codDeporte) {
        this.cod = cod;
        this.nombre = nombre;
        this.codDeporte = codDeporte;
    }

    public Atleta(int cod, String nombre) {
        this.cod = cod;
        this.nombre = nombre;
    }

    public int getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodDeporte() {
        return codDeporte;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodDeporte(int codDeporte) {
        this.codDeporte = codDeporte;
    }

    @Override
    public String toString() {
        return cod + " - " + nombre;
    }
}
