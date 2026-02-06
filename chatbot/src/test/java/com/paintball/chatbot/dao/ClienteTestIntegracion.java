package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)

public class ClienteTestIntegracion {

    @Autowired
    public ClienteDAO clienteDAO;

    @Test
    public void finAll(){
        List<Cliente> clientes = clienteDAO.findAll();
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);
        for (Cliente item: clientes){
            System.out.println(item.toString());
        }
    }

    @Test
    public void finOne(){
        Optional<Cliente> cliente = clienteDAO.findById(1);
        assertTrue(cliente.isPresent(),"El cliente con ID=1 si existe");
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"Sofia","Guevara","1760104133","0998631033","sofia@correo.com", new Date());
        Cliente clienteGuardado = clienteDAO.save(cliente);
        assertNotNull(clienteGuardado,"El cliente nuevo se guardo correctamente");
        assertEquals("1760104133", clienteGuardado.getCedula());
        assertEquals("Sofia", clienteGuardado.getNombre());
        assertEquals("Guevara", clienteGuardado.getApellido());
    }

    @Test
    public void update() {
        Optional<Cliente> cliente = clienteDAO.findById(85);

        cliente.orElse(null).setCedula("1750904142");
        cliente.orElse(null).setNombre("Diego2");
        cliente.orElse(null).setApellido("Bedon2");
        cliente.orElse(null).setCorreo("diego1@correo.com");
        cliente.orElse(null).setTelefono("0998631025");

        Cliente clienteActualizado = clienteDAO.save(cliente.orElse(null));
        assertNotNull(clienteActualizado,"El cliente fue actualizado");
        assertEquals("Diego2", clienteActualizado.getNombre());
        assertEquals("Bedon2", clienteActualizado.getApellido());
    }
    @Test
    public void delete(){
        if (clienteDAO.existsById(85)){
            clienteDAO.deleteById(85);
        }
        assertFalse(clienteDAO.existsById(85),"El cliente fue eliminado correctamente");
    }

}

