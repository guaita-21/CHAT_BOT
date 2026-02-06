package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Cliente;
import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.Reservas;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaTestIntegracion {

    @Autowired
    public FacturaDAO facturaDAO;

    @Autowired
    public ClienteDAO clienteDAO;

    @Autowired
    public ReservasDAO reservasDAO;

    @Test
    public void finAll(){
        List<Factura> facturas = facturaDAO.findAll();
        assertNotNull(facturas);
        assertTrue(facturas.size() > 0);
        for (Factura item: facturas){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Factura> factura = facturaDAO.findById(65);
        assertTrue(factura.isPresent(),"La factura con ID=1 si existe");
        assertEquals(65,factura.orElse(null).getIdFactura());
        System.out.println(factura.toString());
    }

    @Test
    public void save(){
        // Buscar cliente existente
        Cliente cliente = clienteDAO.findById(1).orElseThrow(
                () -> new RuntimeException("Cliente no encontrado")
        );

        // Buscar reserva existente
        Reservas reserva = reservasDAO.findById(151).orElseThrow(
                () -> new RuntimeException("Reserva no encontrada")
        );

        // Crear factura
        Factura factura = new Factura();
        factura.setEstado("Emitida");
        factura.setSubtotal(new BigDecimal("185.00"));
        factura.setIva(new BigDecimal("15.00"));
        factura.setTotal(new BigDecimal("200.00"));
        factura.setFechaEmision(new Date());

        // 🔥 **Asignar relaciones obligatorias**
        factura.setCliente(cliente);
        factura.setReservas(reserva);

        // Guardar
        Factura facturaGuardada = facturaDAO.save(factura);

        // Validaciones
        assertNotNull(facturaGuardada);
        assertNotNull(facturaGuardada.getIdFactura());
        assertEquals("Emitida", facturaGuardada.getEstado());
        assertEquals(new BigDecimal("185.00"), facturaGuardada.getSubtotal());
        assertEquals(cliente.getIdCliente(), facturaGuardada.getCliente().getIdCliente());
        assertEquals(reserva.getIdReservas(), facturaGuardada.getReservas().getIdReservas());

    }

    @Test
    public void update(){
        Optional<Factura> factura = facturaDAO.findById(129);

        factura.orElse(null).setTotal(new BigDecimal("700.00"));
        factura.orElse(null).setSubtotal(new BigDecimal("750.00"));
        factura.orElse(null).setIva(new BigDecimal("70.00"));
        factura.orElse(null).setEstado("No emitida");
    }
    @Test
    public void delete(){
        if (facturaDAO.existsById(129)){
            facturaDAO.deleteById(129);
        }
        assertFalse(facturaDAO.existsById(129),"La factura fue eliminada correctamente");
    }
}


