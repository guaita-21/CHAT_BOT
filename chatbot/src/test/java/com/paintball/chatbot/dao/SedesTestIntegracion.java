package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Sedes;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class SedesTestIntegracion {

    @Autowired
    public SedesDAO sedesDAO;

    @Test
    public void finAll(){
        List<Sedes> sedes = sedesDAO.findAll();
        assertNotNull(sedes);
        assertTrue(sedes.size() > 0);
        for (Sedes item: sedes){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Sedes> sedes = sedesDAO.findById(1);
        assertTrue(sedes.isPresent(),"La sede con ID=1 si existe");
        System.out.println(sedes.toString());
    }

    @Test
    public void save(){
        Sedes sedes = new Sedes(0,"TumBall","Tumbaco Calle 69","09969665");
        Sedes sedesGuardado = sedesDAO.save(sedes);
        assertNotNull(sedesGuardado,"La sede fue guardada correctamente");
        assertEquals("TumBall", sedesGuardado.getNombre());
        assertEquals("Tumbaco Calle 69", sedesGuardado.getDireccion());
        assertEquals("09969665", sedesGuardado.getTelefono());
    }
    @Test
    public void update(){

        Optional<Sedes> optionalSede = sedesDAO.findById(4);
        assertTrue(optionalSede.isPresent(), "La sede con ID=1 existe para actualizar");


        Sedes sede = optionalSede.get();
        sede.setNombre("TumBall Actualizada");
        sede.setDireccion("Tumbaco Calle 70");
        sede.setTelefono("099999999");


        Sedes sedeActualizada = sedesDAO.save(sede);


        assertEquals("TumBall Actualizada", sedeActualizada.getNombre());
        assertEquals("Tumbaco Calle 70", sedeActualizada.getDireccion());
        assertEquals("099999999", sedeActualizada.getTelefono());

        System.out.println("Sede actualizada: " + sedeActualizada);
    }

    @Test
    public void delete(){
        if (sedesDAO.existsById(4)){
            sedesDAO.deleteById(4);
        }
        assertFalse(sedesDAO.existsById(4),"La sede fue eliminada correctamente");
    }
}
