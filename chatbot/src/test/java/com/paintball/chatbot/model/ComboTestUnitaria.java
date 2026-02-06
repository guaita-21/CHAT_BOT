package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ComboTestUnitaria {
    private Combo combo;

    @BeforeEach
    public void setup() {
        combo = new Combo(
                1,
                "Combo Plus",
                200,
                3,
                5,
                new BigDecimal("5.00")
        );
    }
    @Test
    public void testComboConstructor() {

        assertAll("Validar datos del Combo",
                () -> assertEquals(1, combo.getIdCombo()),
                () -> assertEquals("Combo Plus", combo.getNombre()),
                () -> assertEquals(200, combo.getIncluyeBalas()),
                () -> assertEquals(3, combo.getTiempo_min()),
                () -> assertEquals(5, combo.getJugadores()),
                () -> assertEquals(new BigDecimal("5.00"), combo.getPrecio())
        );
    }
    @Test
    public void testClienteSetters(){
        combo.setIdCombo(2);
        combo.setNombre("Combo Basico");
        combo.setIncluyeBalas(100);
        combo.setTiempo_min(1);
        combo.setJugadores(10);
        combo.setPrecio(new BigDecimal("13.00"));


        assertAll("Validar datos del combo con setters",
                () -> assertEquals(2, combo.getIdCombo()),
                () -> assertEquals("Combo Basico", combo.getNombre()),
                () -> assertEquals(100, combo.getIncluyeBalas()),
                () -> assertEquals(1, combo.getTiempo_min()),
                () -> assertEquals(10, combo.getJugadores()),
                () -> assertEquals(new BigDecimal("13.00"), combo.getPrecio())
        );
    }

    @Test
    public void testClienteToString (){

        String str = combo.toString();

        assertAll("Validar datos del combo en toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Combo Plus")),
                () -> assertTrue(str.contains("200")),
                () -> assertTrue(str.contains("3")),
                () -> assertTrue(str.contains("5")),
                () -> assertTrue(str.contains("5.00"))
        );
    }



}
