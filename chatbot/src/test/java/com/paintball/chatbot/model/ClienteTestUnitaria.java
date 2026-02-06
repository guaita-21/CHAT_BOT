package com.paintball.chatbot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTestUnitaria {
    private Cliente cliente;

    @BeforeEach
    public void setup(){
        cliente = new Cliente(
                1,
                "Maria",
                "Vaca",
                "1720336435",
                "099863154",
                "mariav@correo.com",
                new Date()
        );
    }


    @Test
    public void testClienteConstructor() {

        assertAll("Validar datos del cliente constructor",
                () -> assertEquals(1, cliente.getIdCliente()),
                () -> assertEquals("Maria", cliente.getNombre()),
                () -> assertEquals("Vaca", cliente.getApellido()),
                () -> assertEquals("1720336435", cliente.getCedula()),
                () -> assertEquals("099863154", cliente.getTelefono()),
                () -> assertEquals("mariav@correo.com", cliente.getCorreo())
                //() -> assertEquals(new Date(), cliente.getFecharegistro())



        );
    }
    @Test
    public void testClienteSetters(){
        cliente.setIdCliente(2);
        cliente.setCedula("1750904137");
        cliente.setNombre("Julia");
        cliente.setApellido("Aguilar");
        cliente.setCorreo("julia@correo.com");
        cliente.setFecharegistro(new Date());

        assertAll("Validar datos del cliente con setters",
                () -> assertEquals(2, cliente.getIdCliente()),
                () -> assertEquals("1750904137", cliente.getCedula()),
                () -> assertEquals("Julia", cliente.getNombre()),
                () -> assertEquals("Aguilar", cliente.getApellido()),
                () -> assertEquals("julia@correo.com", cliente.getCorreo())
                //() -> assertEquals(new Date(),cliente.getFecharegistro())

        );
    }

    @Test
    public void testClienteToString (){

        String str = cliente.toString();

        assertAll("Validar datos del cliente en toString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("Maria")),
                () -> assertTrue(str.contains("Vaca")),
                () -> assertTrue(str.contains("1720336435")),
                () -> assertTrue(str.contains("099863154")),
                () -> assertTrue(str.contains("mariav@correo.com"))
        );
    }

}
