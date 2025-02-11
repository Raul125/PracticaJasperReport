package com.raulrh.flotaespacial.base.enums;

public enum Estado {
    COMPLETADA("Completada"),
    FALLIDA("Fallida"),
    EN_PROGRESO("En progreso");

    private final String contenido;

    Estado(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}