package com.raulrh.flotaespacial.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tripulantes")
public class Tripulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tripulante", nullable = false)
    private Integer id;

    @Column(name = "nombre_tripulante", nullable = false, length = 100)
    private String nombreTripulante;

    @Lob
    @Column(name = "rango", nullable = false)
    private String rango;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_nave")
    private NaveEspacial idNave;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreTripulante() {
        return nombreTripulante;
    }

    public void setNombreTripulante(String nombreTripulante) {
        this.nombreTripulante = nombreTripulante;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public NaveEspacial getIdNave() {
        return idNave;
    }

    public void setIdNave(NaveEspacial idNave) {
        this.idNave = idNave;
    }

}