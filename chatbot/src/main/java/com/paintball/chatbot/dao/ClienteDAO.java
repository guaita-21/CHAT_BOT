package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDAO extends JpaRepository<Cliente, Integer> {
}
