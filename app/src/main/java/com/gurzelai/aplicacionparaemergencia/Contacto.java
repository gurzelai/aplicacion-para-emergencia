package com.gurzelai.aplicacionparaemergencia;

public class Contacto {
    int numero;
    String nombre;

    public Contacto(String nombre, int numero) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
}
