package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.ClienteDAO;
import com.paintball.chatbot.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public List<Cliente> findAll() {
        return clienteDAO.findAll();
    }

    @Override
    public Optional<Cliente> findOne(int id) {
        return clienteDAO.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteDAO.save(cliente);
    }

    @Override
    public Cliente update(int id, Cliente cliente) {
        Optional<Cliente> clienteExiste =clienteDAO.findById(id);
        if (clienteExiste == null){
            return null;
        }
        clienteExiste.orElse(null).setIdCliente(cliente.getIdCliente());
        clienteExiste.orElse(null).setNombre(cliente.getNombre());
        clienteExiste.orElse(null).setApellido(cliente.getApellido());
        clienteExiste.orElse(null).setCedula(cliente.getCedula());
        clienteExiste.orElse(null).setTelefono(cliente.getTelefono());
        clienteExiste.orElse(null).setCorreo(cliente.getCorreo());
        clienteExiste.orElse(null).setFecharegistro(cliente.getFecharegistro());

        return clienteDAO.save(clienteExiste.orElse(null));
    }

    @Override
    public void delete(int id) {
        if (clienteDAO.existsById(id)){
            clienteDAO.deleteById(id);
        }

    }

}
