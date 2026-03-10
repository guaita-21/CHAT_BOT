package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Carrito;
import com.paintball.chatbot.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoDao extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByCliente(Cliente cliente);
    Optional<Carrito> findByToken(String token);
}