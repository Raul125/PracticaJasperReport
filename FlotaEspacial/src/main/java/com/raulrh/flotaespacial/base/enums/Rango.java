package com.raulrh.flotaespacial.base.enums;

public enum Rango {
    CAPITAN("Capitán"),
    INGENIERO("Ingeniero"),
    PILOTO("Piloto");

    private final String contenido;

    Rango(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}