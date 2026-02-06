package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.FacturaDAO;
import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.FacturaDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FacturaServiceImpl implements FacturaService{
    @Autowired
    private FacturaDAO facturaDAO;

    @Override
    public List<Factura> findAll() {
        return facturaDAO.findAll();
    }

    @Override
    public Optional<Factura> findOne(int id) {
        return facturaDAO.findById(id);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaDAO.save(factura);
    }

    @Override
    public Factura update(int id, Factura factura) {
        Optional<Factura> facturaExistente = facturaDAO.findById(id);
        if (facturaExistente == null){
            return null;
        }

        facturaExistente.orElse(null).setIdFactura(factura.getIdFactura());
        facturaExistente.orElse(null).setFechaEmision(factura.getFechaEmision());
        facturaExistente.orElse(null).setSubtotal(factura.getSubtotal());
        facturaExistente.orElse(null).setIva(factura.getIva());
        facturaExistente.orElse(null).setTotal(factura.getTotal());
        facturaExistente.orElse(null).setEstado(factura.getEstado());
        facturaExistente.orElse(null).setCliente(factura.getCliente());
        facturaExistente.orElse(null).setReservas(factura.getReservas());

        return  facturaDAO.save(facturaExistente.orElse(null));
    }
    @Override
    public void delete(int id) {
        if (facturaDAO.existsById(id)){
            facturaDAO.deleteById(id);
        }

    }
}
