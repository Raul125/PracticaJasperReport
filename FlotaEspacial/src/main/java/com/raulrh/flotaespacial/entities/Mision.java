package com.raulrh.flotaespacial.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "misiones")
public class Mision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mision", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_nave")
    private NaveEspacial idNave;

    @Lob
    @Column(name = "estado", nullable = false)
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public NaveEspacial getIdNave() {
        return idNave;
    }

    public void setIdNave(NaveEspacial idNave) {
        this.idNave = idNave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}