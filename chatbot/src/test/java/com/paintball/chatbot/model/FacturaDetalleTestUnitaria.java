package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaDetalleTestUnitaria {

    private FacturaDetalle facturaDetalle;
    private Factura factura;

    @BeforeEach
    public void setup() {

        factura = new Factura();
        factura.setIdFactura(1);
        factura.setFechaEmision(new Date());
        factura.setSubtotal(new BigDecimal("100.00"));
        factura.setIva(new BigDecimal("12.00"));
        factura.setTotal(new BigDecimal("112.00"));
        factura.setEstado("Pendiente");

        facturaDetalle = new FacturaDetalle();
        facturaDetalle.setIdfacturaDetalle(1);
        facturaDetalle.setDescripcion("Combo Plus");
        facturaDetalle.setCantidad(2);
        facturaDetalle.setPrecioPaquete(new BigDecimal("17.00"));
        facturaDetalle.setTotalLinea(new BigDecimal("34.00"));


        facturaDetalle.setFactura(factura);
    }

    @Test
    public void testFacturaDetalleConstructor() {
        assertAll("Validar datos de FacturaDetalle",
                () -> assertEquals(1, facturaDetalle.getIdfacturaDetalle()),
                () -> assertEquals("Combo Plus", facturaDetalle.getDescripcion()),
                () -> assertEquals(2, facturaDetalle.getCantidad()),
                () -> assertEquals(new BigDecimal("17.00"), facturaDetalle.getPrecioPaquete()),
                () -> assertEquals(new BigDecimal("34.00"), facturaDetalle.getTotalLinea())
        );
    }

    @Test
    public void testFacturaDetalleToString() {
        String str = facturaDetalle.toString();

        assertAll("Validar contenido del toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Combo Plus")),
                () -> assertTrue(str.contains("2")),
                () -> assertTrue(str.contains("17.00")),
                () -> assertTrue(str.contains("34.00"))
        );
    }

    @Test
    public void testFacturaDetalleInyeccion() {
        assertAll("Validar inyección de Factura",
                () -> assertNotNull(facturaDetalle.getFactura()),
                () -> assertEquals(1, facturaDetalle.getFactura().getIdFactura()),
                () -> assertEquals("Pendiente", facturaDetalle.getFactura().getEstado())
        );
    }
}
