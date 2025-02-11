package com.raulrh.flotaespacial.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "naves_espaciales")
public class NaveEspacial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nave", nullable = false)
    private Integer id;

    @Column(name = "nombre_nave", nullable = false, length = 100)
    private String nombreNave;

    @Lob
    @Column(name = "clase", nullable = false)
    private String clase;

    @Column(name = "capacidad_tripulacion", nullable = false)
    private Integer capacidadTripulacion;

    @OneToMany(mappedBy = "idNave")
    private Set<Mision> misiones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idNave")
    private Set<com.raulrh.flotaespacial.entities.Tripulante> tripulantes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreNave() {
        return nombreNave;
    }

    public void setNombreNave(String nombreNave) {
        this.nombreNave = nombreNave;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Integer getCapacidadTripulacion() {
        return capacidadTripulacion;
    }

    public void setCapacidadTripulacion(Integer capacidadTripulacion) {
        this.capacidadTripulacion = capacidadTripulacion;
    }

    public Set<Mision> getMisiones() {
        return misiones;
    }

    public void setMisiones(Set<Mision> misiones) {
        this.misiones = misiones;
    }

    public Set<com.raulrh.flotaespacial.entities.Tripulante> getTripulantes() {
        return tripulantes;
    }

    public void setTripulantes(Set<com.raulrh.flotaespacial.entities.Tripulante> tripulantes) {
        this.tripulantes = tripulantes;
    }

}