package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.SedesDAO;
import com.paintball.chatbot.model.Sedes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SedesServiceImpl implements SedesService{
    @Autowired
    private SedesDAO sedesDAO;

    @Override
    public List<Sedes> findAll() {
        return sedesDAO.findAll();
    }

    @Override
    public Optional<Sedes> findOne(int id) {
        return sedesDAO.findById(id);
    }

    @Override
    public Sedes save(Sedes sedes) {
        return sedesDAO.save(sedes);
    }

    @Override
    public Sedes update(int id, Sedes sedes) {
        Optional<Sedes> NuevasSedes = sedesDAO.findById(id);
        if (NuevasSedes == null) {
            return null;
        }
        NuevasSedes.orElse(null).setIdSedes(sedes.getIdSedes());
        NuevasSedes.orElse(null).setNombre(sedes.getNombre());
        NuevasSedes.orElse(null).setDireccion(sedes.getDireccion());
        NuevasSedes.orElse(null).setTelefono(sedes.getTelefono());

        return sedesDAO.save(NuevasSedes.orElse(null));
    }


    @Override
    public void delete(int id) {
        if (sedesDAO.existsById(id)){
            sedesDAO.deleteById(id);
        }

    }
}
