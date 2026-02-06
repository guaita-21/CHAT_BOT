package com.paintball.chatbot.Service;



import com.paintball.chatbot.model.Reservas;

import java.util.List;
import java.util.Optional;

public interface ReservasService {
    public List<Reservas> findAll();

    public Optional<Reservas> findOne (int id);

    public Reservas save(Reservas reservas);

    public Reservas update(int id, Reservas reservas);

    public void delete(int id);

}

