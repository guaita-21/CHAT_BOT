package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquiposDAO extends JpaRepository<Equipos, Integer> {
}
