package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservasDAO extends JpaRepository<Reservas, Integer> {
}
