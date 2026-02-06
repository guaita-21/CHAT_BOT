package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.ComboDAO;
import com.paintball.chatbot.model.Combo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComboServiceImpl implements ComboService{
    @Autowired
    private ComboDAO comboDAO;

    @Override
    public List<Combo> findAll() {
        return comboDAO.findAll();
    }

    @Override
    public Optional<Combo> findOne(int id) {
        return comboDAO.findById(id);
    }

    @Override
    public Combo save(Combo combo) {
        return comboDAO.save(combo);
    }

    @Override
    public Combo update(int id, Combo combo) {
        Optional<Combo> comboExiste =comboDAO.findById(id);
        if (comboExiste == null){
            return null;
        }
        comboExiste.orElse(null).setIdCombo(combo.getIdCombo());
        comboExiste.orElse(null).setNombre(combo.getNombre());
        comboExiste.orElse(null).setIncluyeBalas(combo.getIncluyeBalas());
        comboExiste.orElse(null).setTiempo_min(combo.getTiempo_min());
        comboExiste.orElse(null).setPrecio(combo.getPrecio());

        return comboDAO.save(comboExiste.orElse(null));
    }

    @Override
    public void delete(int id) {
        if (comboDAO.existsById(id)){
            comboDAO.deleteById(id);
        }
    }
}
