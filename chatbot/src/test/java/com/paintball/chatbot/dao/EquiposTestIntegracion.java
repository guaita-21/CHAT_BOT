package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Equipos;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback (false)

public class EquiposTestIntegracion {

    @Autowired
    public EquiposDAO equiposDAO;

    @Test
    public void finAll(){
        List<Equipos> equipos1 = equiposDAO.findAll();
        assertNotNull(equipos1);
        assertTrue(equipos1.size() > 0);
        for (Equipos item:equipos1){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Equipos> equipos = equiposDAO.findById(1);
        assertTrue(equipos.isPresent(),"El equipo con ID=1 si existe");
        System.out.println(equipos.toString());
    }

    @Test
    public void save(){
        Equipos equipos = new Equipos(0,"Calibre 58","Marcadora","Soporta paintballs calibre 58");
        Equipos equipoGuardado = equiposDAO.save(equipos);
        assertNotNull(equipoGuardado,"El nuevo equipo fue guardado");
        assertEquals("Calibre 58", equipoGuardado.getNombre());
        assertEquals("Marcadora", equipoGuardado.getTipo());
        assertEquals("Soporta paintballs calibre 58", equipoGuardado.getDescripcion());
    }

    @Test
    public void update(){
        Optional<Equipos> equipos = equiposDAO.findById(22);

        equipos.orElse(null).setNombre("Calibre 68");
        equipos.orElse(null).setTipo("Marcadora5");
        equipos.orElse(null).setDescripcion("Soporta calibre 68");

        Equipos equipoActualizado = equiposDAO.save(equipos.orElse(null));
        assertNotNull(equipoActualizado,"El equipo fue actualizado");
        assertEquals("Calibre 68", equipoActualizado.getNombre());
        assertEquals("Marcadora5", equipoActualizado.getTipo());
    }
    @Test
    public void delete(){
        if (equiposDAO.existsById(22)){
            equiposDAO.deleteById(22);
        }
        assertFalse(equiposDAO.existsById(22),"El equipo fue eliminado correctamente");
    }

}
