package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Equipos;

import java.util.List;
import java.util.Optional;

public interface EquiposService {

    public List<Equipos> findAll();

    public Optional<Equipos> findOne (int id);

    public Equipos save(Equipos equipos);

    public Equipos update(int id, Equipos equipos);

    public void delete(int id);
}
