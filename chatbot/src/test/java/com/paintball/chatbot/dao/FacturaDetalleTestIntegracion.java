package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.FacturaDetalle;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class FacturaDetalleTestIntegracion {

    private static final Logger log = LoggerFactory.getLogger(FacturaDetalleTestIntegracion.class);
    @Autowired
    public FacturaDAO facturaDAO;

    @Autowired
    public FacturaDetalleDAO facturaDetalleDAO;

    @Test
    public void finAll(){
        List<FacturaDetalle> facturasdetalles = facturaDetalleDAO.findAll();
        assertNotNull(facturasdetalles);
        assertTrue(facturasdetalles.size() > 0);
        for (FacturaDetalle item: facturasdetalles){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<FacturaDetalle> facturaDetalle = facturaDetalleDAO.findById(351);
        assertTrue(facturaDetalle.isPresent(),"La facturadetalle con ID= 351 si existe");
        assertEquals(351, facturaDetalle.orElse(null).getIdfacturaDetalle());
        System.out.println(facturaDetalle.toString());
    }

    @Test
    public void save(){
        Factura factura = facturaDAO.findById(64).orElseThrow(
                () -> new RuntimeException("Factura no encontrada")
        );

        FacturaDetalle facturaDetalle = new FacturaDetalle();
        facturaDetalle.setDescripcion("Combo Plus");
        facturaDetalle.setPrecioPaquete(new BigDecimal("17.00"));
        facturaDetalle.setCantidad(2);
        facturaDetalle.setTotalLinea(new BigDecimal("17.00"));

        facturaDetalle.setFactura(factura);

        FacturaDetalle facturaDetalleGuardado = facturaDetalleDAO.save(facturaDetalle);
        assertNotNull(facturaDetalleGuardado);
        assertNotNull(facturaDetalleGuardado.getIdfacturaDetalle());
        assertEquals("Combo Plus", facturaDetalleGuardado.getDescripcion());
        assertEquals(new BigDecimal("17.00"), facturaDetalleGuardado.getPrecioPaquete());
        assertEquals(new BigDecimal("17.00"), facturaDetalleGuardado.getTotalLinea());
    }

    @Test
    public void update(){
        Optional<FacturaDetalle> facturaDetalle = facturaDetalleDAO.findById(403);

        facturaDetalle.orElse(null).setTotalLinea(new BigDecimal("19.00"));
        facturaDetalle.orElse(null).setPrecioPaquete(new BigDecimal("19.00"));
        facturaDetalle.orElse(null).setDescripcion("Combo ExtraPlus");
    }
    @Test
    public void delete(){
        if (facturaDetalleDAO.existsById(403)){
            facturaDetalleDAO.deleteById(403);
        }
        assertFalse(facturaDetalleDAO.existsById(403),"La facturaDetalle fue eliminada correctamente");
    }
}
