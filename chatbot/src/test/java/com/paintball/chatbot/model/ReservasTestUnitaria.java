package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ReservasTestUnitaria {

    private Reservas reserva;
    private Cliente cliente;
    private Sedes sede;
    private Equipos equipo;
    private Combo combo;

    @BeforeEach
    public void setup() {

        reserva = new Reservas();
        reserva.setIdReservas(1);
        reserva.setFechaReservas(new Date());
        reserva.setHoraRegistro(LocalTime.of(10, 30));
        reserva.setEstado("Confirmada");
        reserva.setTotal(new BigDecimal("150.00"));

        cliente = new Cliente(1,
                "Juan",
                "Pérez",
                "1720336435",
                "0999673411",
                "juan@correo.com",
                new Date());

        sede = new Sedes(1,
                "Sede Norte",
                "Av. Principal 123",
                "0987654321");


        equipo = new Equipos(1,
                "Equipo A",
                "Paintball",
                "Equipo de protección completo");


        combo = new Combo(1,
                "Combo Plus",
                200,
                3,
                5,
                new BigDecimal("50.00"));

        // Inyecciones
        reserva.setCliente(cliente);
        reserva.setSedes(sede);
        reserva.setEquipos(equipo);
        reserva.setCombo(combo);
    }

    @Test
    public void testReservasConstructor() {
        assertAll("Validar datos de la reserva",
                () -> assertEquals(1, reserva.getIdReservas()),
                () -> assertNotNull(reserva.getFechaReservas()),
                () -> assertEquals(LocalTime.of(10, 30), reserva.getHoraRegistro()),
                () -> assertEquals("Confirmada", reserva.getEstado()),
                () -> assertEquals(new BigDecimal("150.00"), reserva.getTotal())
        );
    }

    @Test
    public void testReservasToString() {
        String str = reserva.toString();
        assertAll("Validar contenido del toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Confirmada")),
                () -> assertTrue(str.contains("150.00"))
        );
    }

    @Test
    public void testReservasInyeccion() {
        assertAll("Validar inyecciones de Cliente, Sede, Equipo y Combo",
                () -> assertNotNull(reserva.getCliente()),
                () -> assertNotNull(reserva.getSedes()),
                () -> assertNotNull(reserva.getEquipos()),
                () -> assertNotNull(reserva.getCombo()),

                () -> assertEquals("Juan", reserva.getCliente().getNombre()),
                () -> assertEquals("Sede Norte", reserva.getSedes().getNombre()),
                () -> assertEquals("Equipo A", reserva.getEquipos().getNombre()),
                () -> assertEquals("Combo Plus", reserva.getCombo().getNombre())
        );
    }
}
