package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class SedesTestUnitaria {

    private Sedes sedes;

    @BeforeEach
    public void setup() {
        sedes = new Sedes(
                1,
                "Sede Principal",
                "Av. Amazonas y Quito",
                "0999999999"
        );
    }

    @Test
    public void testSedesConstructor() {

        assertAll("Validar datos de la sede",
                () -> assertEquals(1, sedes.getIdSedes()),
                () -> assertEquals("Sede Principal", sedes.getNombre()),
                () -> assertEquals("Av. Amazonas y Quito", sedes.getDireccion()),
                () -> assertEquals("0999999999", sedes.getTelefono())
        );
    }

    @Test
    public void testSedesSetters() {
        sedes.setIdSedes(2);
        sedes.setNombre("Sede Norte");
        sedes.setDireccion("Av. Galo Plaza");
        sedes.setTelefono("022345678");

        assertAll("Validar datos de la sede con setters",
                () -> assertEquals(2, sedes.getIdSedes()),
                () -> assertEquals("Sede Norte", sedes.getNombre()),
                () -> assertEquals("Av. Galo Plaza", sedes.getDireccion()),
                () -> assertEquals("022345678", sedes.getTelefono())
        );
    }

    @Test
    public void testSedesToString() {

        String str = sedes.toString();

        assertAll("Validar datos en toString de la sede",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Sede Principal")),
                () -> assertTrue(str.contains("Av. Amazonas y Quito")),
                () -> assertTrue(str.contains("0999999999"))
        );
    }
}
