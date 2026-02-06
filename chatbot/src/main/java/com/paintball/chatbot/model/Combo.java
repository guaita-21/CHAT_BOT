package com.paintball.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "combos")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_combo")
    private int idCombo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "incluye_balas")
    @JsonProperty("incluyeBalas")
    private int incluyeBalas;

    @Column(name = "tiempo_min")
    private int tiempo_min;

    @Column(name = "jugadores")
    private int jugadores;

    @Column(name = "precio")
    private BigDecimal precio;


    public Combo() {}


    public Combo(int idCombo, String nombre, int incluyeBalas, int tiempo_min, int jugadores, BigDecimal precio) {
        this.idCombo = idCombo;
        this.nombre = nombre;
        this.incluyeBalas = incluyeBalas;
        this.tiempo_min = tiempo_min;
        this.jugadores = jugadores;
        this.precio = precio;
    }


    public int getIdCombo() { return idCombo; }
    public void setIdCombo(int idCombo) { this.idCombo = idCombo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIncluyeBalas() { return incluyeBalas; }
    public void setIncluyeBalas(int incluyeBalas) { this.incluyeBalas = incluyeBalas; }

    public int getTiempo_min() { return tiempo_min; }
    public void setTiempo_min(int tiempo_min) { this.tiempo_min = tiempo_min; }

    public int getJugadores() { return jugadores; }
    public void setJugadores(int jugadores) { this.jugadores = jugadores; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "Combo{" +
                "idCombo=" + idCombo +
                ", nombre='" + nombre + '\'' +
                ", incluyeBalas=" + incluyeBalas +
                ", tiempo_min=" + tiempo_min +
                ", jugadores=" + jugadores +
                ", precio=" + precio +
                '}';
    }
}
