package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class ReservasTestIntegracion {

    @Autowired
    public ReservasDAO reservasDAO;

    @Autowired
    public SedesDAO sedesDAO;

    @Autowired
    public ClienteDAO clienteDAO;

    @Autowired
    public ComboDAO comboDAO;

    @Autowired
    public EquiposDAO equiposDAO;

    @Test
    public void findAll(){
        List<Reservas> reservas = reservasDAO.findAll();
        assertNotNull(reservas);
        assertTrue(reservas.size() > 0 );
        for (Reservas item: reservas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Reservas> reservas = reservasDAO.findById(151);
        assertTrue(reservas.isPresent(),"La reserva con el ID=151 si existe");
        System.out.println(reservas.toString());
    }

    @Test
    public void save(){
        Cliente cliente = clienteDAO.findById(1).orElseThrow();
        Sedes sede = sedesDAO.findById(1).orElseThrow();
        Combo combo = comboDAO.findById(1).orElseThrow();
        Equipos equipos = equiposDAO.findById(1).orElseThrow();

        Reservas reservas = new Reservas();
        reservas.setFechaReservas(new Date());
        reservas.setEstado("Emitida");
        reservas.setTotal(new BigDecimal("50.00"));
        reservas.setHoraRegistro(LocalTime.of(5, 25));

        reservas.setCliente(cliente);
        reservas.setSedes(sede);
        reservas.setCombo(combo);
        reservas.setEquipos(equipos);

        Reservas guardada = reservasDAO.save(reservas);
        assertNotNull(guardada);
    }

    @Test
    public void update(){
        Optional<Reservas> reservas = reservasDAO.findById(203);
        reservas.orElse(null).setTotal(new BigDecimal("90.00"));
        reservas.orElse(null).setFechaReservas(new Date());
        reservas.orElse(null).setEstado("No emitida");
    }
    @Test
    public void delete(){
        if (reservasDAO.existsById(203)){
            reservasDAO.deleteById(203);
        }
        assertFalse(reservasDAO.existsById(203),"La reserva fue eliminada correctamente");
    }
    }

