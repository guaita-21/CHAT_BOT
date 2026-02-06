package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.ReservasDAO;
import com.paintball.chatbot.model.Reservas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ReservasServiceImpl implements ReservasService{

    @Autowired
    private ReservasDAO reservasDAO;

    @Override
    public List<Reservas> findAll() {
        return reservasDAO.findAll();
    }

    @Override
    public Optional<Reservas> findOne(int id) {
        return reservasDAO.findById(id);
    }

    @Override
    public Reservas save(Reservas reservas) {
        return reservasDAO.save(reservas);
    }

    @Override
    public Reservas update(int id, Reservas reservas) {
        Optional<Reservas> nuevasreservas = reservasDAO.findById(id);

        if (nuevasreservas == null) {
            return null;
        }
        nuevasreservas.orElse(null).setIdReservas(reservas.getIdReservas());
        nuevasreservas.orElse(null).setFechaReservas(reservas.getFechaReservas());
        nuevasreservas.orElse(null).setHoraRegistro(reservas.getHoraRegistro());
        nuevasreservas.orElse(null).setEstado(reservas.getEstado());
        nuevasreservas.orElse(null).setTotal(reservas.getTotal());

        return reservasDAO.save(nuevasreservas.orElse(null));
    }
    @Override
    public void delete(int id) {
     if (reservasDAO.existsById(id)){
         reservasDAO.deleteById(id);
     }
    }
}
