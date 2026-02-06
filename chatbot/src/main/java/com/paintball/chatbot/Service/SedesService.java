package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.Sedes;

import java.util.List;
import java.util.Optional;

public interface SedesService {

        public List<Sedes> findAll();

        public Optional<Sedes> findOne (int id);

        public Sedes save(Sedes sedes);

        public Sedes update(int id, Sedes sedes);

        public void delete(int id);

    }


