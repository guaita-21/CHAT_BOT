package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Combo;

import java.util.List;
import java.util.Optional;

public interface ComboService {
    public List<Combo> findAll();

    public Optional<Combo> findOne (int id);

    public Combo save(Combo combo);

    public Combo update(int id, Combo combo);

    public void delete(int id);
}
