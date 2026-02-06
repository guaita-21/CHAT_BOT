package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.EquiposDAO;
import com.paintball.chatbot.model.Equipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquiposServiceImpl implements EquiposService{
    @Autowired
    private EquiposDAO equiposDAO;

    @Override
    public List<Equipos> findAll() {
        return equiposDAO.findAll();
    }

    @Override
    public Optional<Equipos> findOne(int id) {
        return equiposDAO.findById(id);
    }

    @Override
    public Equipos save(Equipos equipos) {
        return equiposDAO.save(equipos);
    }

    @Override
    public Equipos update(int id, Equipos equipos) {
        Optional<Equipos> equiposExiste = equiposDAO.findById(id);
        if (equiposExiste == null){
            return null;
        }
        equiposExiste.orElse(null).setIdEquipos(equipos.getIdEquipos());
        equiposExiste.orElse(null).setNombre(equipos.getNombre());
        equiposExiste.orElse(null).setTipo(equipos.getTipo());
        equiposExiste.orElse(null).setDescripcion(equipos.getDescripcion());

        return equiposDAO.save(equiposExiste.orElse(null));
    }

    @Override
    public void delete(int id) {
        if (equiposDAO.existsById(id)){
            equiposDAO.deleteById(id);
        }
    }
}
