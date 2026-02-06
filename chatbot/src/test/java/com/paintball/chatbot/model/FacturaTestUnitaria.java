package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTestUnitaria {

    private Factura factura;
    private Cliente cliente;
    private Reservas reserva;

    @BeforeEach
    public void setup() {

        factura = new Factura();
        factura.setIdFactura(1);
        factura.setFechaEmision(new Date());
        factura.setSubtotal(new BigDecimal("100.00"));
        factura.setIva(new BigDecimal("12.00"));
        factura.setTotal(new BigDecimal("112.00"));
        factura.setEstado("Pendiente");

        cliente = new Cliente(
                1,
                "Juan",
                "Pérez",
                "1720336435",
                "0999999999",
                "juanperez@correo.com",
                new Date()
        );

        reserva = new Reservas();
        reserva.setIdReservas(1);
        reserva.setFechaReservas(new Date());
        reserva.setHoraRegistro(LocalTime.now());
        reserva.setEstado("Pendiente");
        reserva.setTotal(new BigDecimal("50.00"));

        // Inyecciones
        factura.setCliente(cliente);
        factura.setReservas(reserva);
    }

    @Test
    public void testFacturaConstructor() {
        assertAll("Validar datos de la factura",
                () -> assertEquals(1, factura.getIdFactura()),
                () -> assertNotNull(factura.getFechaEmision()),
                () -> assertEquals(new BigDecimal("100.00"), factura.getSubtotal()),
                () -> assertEquals(new BigDecimal("12.00"), factura.getIva()),
                () -> assertEquals(new BigDecimal("112.00"), factura.getTotal()),
                () -> assertEquals("Pendiente", factura.getEstado())
        );
    }

    @Test
    public void testFacturaToString() {
        String str = factura.toString();

        assertAll("Validar contenido del toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("100.00")),
                () -> assertTrue(str.contains("12.00")),
                () -> assertTrue(str.contains("112.00")),
                () -> assertTrue(str.contains("Pendiente")),
                () -> assertTrue(str.contains("Juan")),
                () -> assertTrue(str.contains("Pérez"))
        );
    }

    @Test
    public void testFacturaInyeccion() {
        assertAll("Validar inyección de Cliente y Reservas",
                () -> assertNotNull(factura.getCliente()),
                () -> assertNotNull(factura.getReservas()),
                () -> assertEquals("Juan", factura.getCliente().getNombre()),
                () -> assertEquals(1, factura.getReservas().getIdReservas())
        );
    }
}
