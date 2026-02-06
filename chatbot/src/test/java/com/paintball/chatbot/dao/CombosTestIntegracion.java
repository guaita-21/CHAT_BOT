package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Combo;
import com.paintball.chatbot.model.Equipos;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
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
public class CombosTestIntegracion {

    @Autowired
    public ComboDAO comboDAO;

    @Test
        public void finAll(){
        List<Combo> combos = comboDAO.findAll();
        assertNotNull(combos);
            assertTrue(combos.size() > 0);
            for (Combo item:combos){
                System.out.println(item.toString());
            }
        }


        @Test
    public void finOne(){
        Optional<Combo> combo = comboDAO.findById(1);
        assertTrue(combo.isPresent(),"El combo con ID:1 si existe");
        System.out.println(combo.toString());
    }

    @Test
    public void save(){
        Combo combo = new Combo(
                0,
                "Plus",
                250,
                3,
                4, // jugadores (OBLIGATORIO ahora)
                new BigDecimal("17.00")
        );

        Combo comboGuardado = comboDAO.save(combo);

        assertNotNull(comboGuardado);
        assertEquals("Plus", comboGuardado.getNombre());
        assertEquals(250, comboGuardado.getIncluyeBalas());
        assertEquals(3, comboGuardado.getTiempo_min());
    }


    @Test
    public void update(){
        Optional<Combo> combo = comboDAO.findById(9);

        combo.orElse(null).setNombre("Plus");
        combo.orElse(null).setIncluyeBalas(300);
        combo.orElse(null).setTiempo_min(3);
        combo.orElse(null).setPrecio(new BigDecimal("17.00"));

        Combo comboActualizado = comboDAO.save(combo.orElse(null));
        assertNotNull(comboActualizado,"El combo fue actualizado");
        assertEquals("Plus", comboActualizado.getNombre());
        assertEquals(300, comboActualizado.getIncluyeBalas());
    }
    @Test
    public void delete(){
        if (comboDAO.existsById(10)){
            comboDAO.deleteById(10);
        }
        assertFalse(comboDAO.existsById(10),"El combo fue eliminado");
    }
}
