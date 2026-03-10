package com.paintball.chatbot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "factura_detalle")
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idfacturaDetalle;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "precio_paquete")
    private BigDecimal precioPaquete;
    @Column(name = "total_linea")
    private BigDecimal totalLinea;

    //Inyecciones
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "id_combo")
    private Combo combo;


    public FacturaDetalle(){}

    public FacturaDetalle(int idfacturaDetalle, String descripcion, int cantidad, BigDecimal precioPaquete, BigDecimal totalLinea, Factura factura, Combo combo) {
        this.idfacturaDetalle = idfacturaDetalle;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioPaquete = precioPaquete;
        this.totalLinea = totalLinea;
        this.factura = factura;
        this.combo = combo;
    }

    public int getIdfacturaDetalle() {
        return idfacturaDetalle;
    }

    public void setIdfacturaDetalle(int idfacturaDetalle) {
        this.idfacturaDetalle = idfacturaDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioPaquete() {
        return precioPaquete;
    }

    public void setPrecioPaquete(BigDecimal precioPaquete) {
        this.precioPaquete = precioPaquete;
    }

    public BigDecimal getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(BigDecimal totalLinea) {
        this.totalLinea = totalLinea;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    @Override
    public String toString() {
        return "FacturaDetalle{" +
                "idfacturaDetalle=" + idfacturaDetalle +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", precioPaquete=" + precioPaquete +
                ", totalLinea=" + totalLinea +
                ", factura=" + factura +
                ", combo=" + combo +
                '}';
    }
}
