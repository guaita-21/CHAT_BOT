package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaDAO extends JpaRepository<Factura, Integer> {
}
