package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquiposTestUnitaria {

    private Equipos equipo;

    @BeforeEach
    public void setup() {
        equipo = new Equipos(
                1,
                "Marcadora",
                "Arma",
                "Marcadora semiautomática calibre .68"
        );
    }

    @Test
    public void testEquiposConstructor() {

        assertAll("Validar datos del equipo",
                () -> assertEquals(1, equipo.getIdEquipos()),
                () -> assertEquals("Marcadora", equipo.getNombre()),
                () -> assertEquals("Arma", equipo.getTipo()),
                () -> assertEquals("Marcadora semiautomática calibre .68", equipo.getDescripcion())
        );
    }

    @Test
    public void testEquiposSetters() {
        equipo.setIdEquipos(2);
        equipo.setNombre("Chaleco");
        equipo.setTipo("Protección");
        equipo.setDescripcion("Chaleco protector para torso");

        assertAll("Validar datos del equipo con setters",
                () -> assertEquals(2, equipo.getIdEquipos()),
                () -> assertEquals("Chaleco", equipo.getNombre()),
                () -> assertEquals("Protección", equipo.getTipo()),
                () -> assertEquals("Chaleco protector para torso", equipo.getDescripcion())
        );
    }

    @Test
    public void testEquiposToString() {

        String str = equipo.toString();

        assertAll("Validar datos en toString del equipo",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Marcadora")),
                () -> assertTrue(str.contains("Arma")),
                () -> assertTrue(str.contains("Marcadora semiautomática calibre .68"))
        );
    }
}
