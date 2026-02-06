package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboDAO extends JpaRepository<Combo, Integer> {
}
