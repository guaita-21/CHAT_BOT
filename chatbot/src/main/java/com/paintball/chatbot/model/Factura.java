package com.paintball.chatbot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "iva")
    private BigDecimal iva;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "estado")
    private String estado;

    @Column(name = "num_factura")
    private String num_factura;

    //@ManyToOne
    //@JoinColumn(name = "id_cliente", nullable = false)
    //private Cliente cliente;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = true)
    private Cliente cliente;

    //@ManyToOne
    //@JoinColumn(name = "id_reserva", nullable = false)

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = true)
    private Reservas reservas;

    public Factura() {
    }

    public Factura(int idFactura, Date fechaEmision, BigDecimal subtotal, BigDecimal iva, BigDecimal total, String estado, String num_factura, Cliente cliente, Reservas reservas) {
        this.idFactura = idFactura;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
        this.num_factura = num_factura;
        this.cliente = cliente;
        this.reservas = reservas;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(String num_factura) {
        this.num_factura = num_factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Reservas getReservas() {
        return reservas;
    }

    public void setReservas(Reservas reservas) {
        this.reservas = reservas;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", fechaEmision=" + fechaEmision +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                ", num_factura='" + num_factura + '\'' +
                ", cliente=" + cliente +
                ", reservas=" + reservas +
                '}';
    }
}