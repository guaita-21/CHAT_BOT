package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.FacturaDetalleDAO;
import com.paintball.chatbot.model.FacturaDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
 @Service
public class FacturaDetalleServiceImpl implements FacturaDetalleService {
  @Autowired
     private FacturaDetalleDAO facturaDetalleDAO;

    @Override
    public List<FacturaDetalle> findAll() {
        return facturaDetalleDAO.findAll();
    }

    @Override
    public Optional<FacturaDetalle> findOne(int id) {
        return facturaDetalleDAO.findById(id);
    }

    @Override
    public FacturaDetalle save(FacturaDetalle facturaDetalle) {
        return facturaDetalleDAO.save(facturaDetalle);
    }

    @Override
    public FacturaDetalle update(int id, FacturaDetalle facturaDetalle) {
        Optional<FacturaDetalle> facturaDetalleExistente = facturaDetalleDAO.findById(id);

        if (facturaDetalleExistente ==null) {
            return null;
        }
        facturaDetalleExistente.orElse(null).setIdfacturaDetalle(facturaDetalle.getIdfacturaDetalle());
        facturaDetalleExistente.orElse(null).setDescripcion(facturaDetalle.getDescripcion());
        facturaDetalleExistente.orElse(null).setCantidad(facturaDetalle.getCantidad());
        facturaDetalleExistente.orElse(null).setPrecioPaquete(facturaDetalle.getPrecioPaquete());
        facturaDetalleExistente.orElse(null).setTotalLinea(facturaDetalle.getTotalLinea());

        return  facturaDetalleDAO.save(facturaDetalleExistente.orElse(null));
    }

    @Override
    public void delete(int id) {
        if (facturaDetalleDAO.existsById(id)){
            facturaDetalleDAO.deleteById(id);
        }

    }
}
