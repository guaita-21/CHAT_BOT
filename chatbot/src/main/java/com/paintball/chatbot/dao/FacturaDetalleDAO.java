package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.FacturaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaDetalleDAO extends JpaRepository<FacturaDetalle, Integer> {
}
