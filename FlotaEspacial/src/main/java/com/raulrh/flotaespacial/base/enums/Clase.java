package com.raulrh.flotaespacial.base.enums;

public enum Clase {
    EXPLORACION("Exploración"),
    COMBATE("Combate"),
    TRANSPORTE("Transporte");

    private final String contenido;

    Clase(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}