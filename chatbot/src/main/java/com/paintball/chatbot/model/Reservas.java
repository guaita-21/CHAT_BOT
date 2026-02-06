package com.paintball.chatbot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private int idReservas;
    @Column(name = "fecha_reserva")
    private Date fechaReservas;
    @Column(name = "hora_reserva")
    private LocalTime horaRegistro;
    @Column(name = "estado")
    private String estado;
    @Column(name = "total")
    private BigDecimal total;


    //Inyecciones
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_sede")
    private Sedes sedes;
    @ManyToOne
    @JoinColumn(name = "id_equipo")
    private Equipos equipos;
    @ManyToOne
    @JoinColumn(name = "id_combo")
    private Combo combo;

    //Constructor vacio
    public Reservas(){}

    public Reservas(int idReservas, Date fechaReservas, LocalTime horaRegistro, String estado, BigDecimal total) {
        this.idReservas = idReservas;
        this.fechaReservas = fechaReservas;
        this.horaRegistro = horaRegistro;
        this.estado = estado;
        this.total = total;
    }


    public int getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(int idReservas) {
        this.idReservas = idReservas;
    }

    public Date getFechaReservas() {
        return fechaReservas;
    }

    public void setFechaReservas(Date fechaReservas) {
        this.fechaReservas = fechaReservas;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // ✔ GETTERS Y SETTERS FALTANTES:

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Sedes getSedes() {
        return sedes;
    }

    public void setSedes(Sedes sedes) {
        this.sedes = sedes;
    }

    public Equipos getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipos equipos) {
        this.equipos = equipos;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }



    @Override
    public String toString() {
        return "Reservas{" +
                "idReservas=" + idReservas +
                ", fechaReservas=" + fechaReservas +
                ", horaRegistro=" + horaRegistro +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                '}';
    }
}
