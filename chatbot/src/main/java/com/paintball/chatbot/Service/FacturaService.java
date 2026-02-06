package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaService {

    public List<Factura> findAll();

    public Optional<Factura> findOne (int id);

    public Factura save(Factura factura);

    public Factura update(int id, Factura factura);

    public void delete(int id);

}
