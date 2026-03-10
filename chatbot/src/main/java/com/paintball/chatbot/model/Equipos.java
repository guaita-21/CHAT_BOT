package com.paintball.chatbot.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "equipos")
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private int idEquipos;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen_equipo")
    private String imagenEquipo;

    public Equipos() {
    }

    public Equipos(int idEquipos, String nombre, String tipo, String descripcion, String imagenEquipo) {
        this.idEquipos = idEquipos;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagenEquipo = imagenEquipo;
    }

    public int getIdEquipos() {
        return idEquipos;
    }

    public void setIdEquipos(int idEquipos) {
        this.idEquipos = idEquipos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenEquipo() {
        return imagenEquipo;
    }

    public void setImagenEquipo(String imagenEquipo) {
        this.imagenEquipo = imagenEquipo;
    }

    @Override
    public String toString() {
        return "Equipos{" +
                "idEquipos=" + idEquipos +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagenEquipo='" + imagenEquipo + '\'' +
                '}';
    }
}