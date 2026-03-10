package com.paintball.chatbot.Service;

import com.paintball.chatbot.Service.util.CheckoutMapper;
import com.paintball.chatbot.dao.CarritoDao;
import com.paintball.chatbot.dao.ComboDAO;
import com.paintball.chatbot.dao.FacturaDAO;
import com.paintball.chatbot.dao.FacturaDetalleDAO;
import com.paintball.chatbot.model.Factura;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GuestCheckoutServiceImpl  implements GuestCheckoutService{
    private final CarritoDao carritoRepository;
    private final FacturaDAO facturaRepository;
    private final FacturaDetalleDAO facturaDetalleRepository;
    private final ComboDAO comboRepository;


    private static final double IVA = 0.15d;

    public GuestCheckoutServiceImpl(CarritoDao carritoRepository,
                                    FacturaDAO facturaRepository,
                                    FacturaDetalleDAO facturaDetalleRepository,
                                    ComboDAO comboRepository) {
        this.carritoRepository = carritoRepository;
        this.facturaRepository = facturaRepository;
        this.facturaDetalleRepository = facturaDetalleRepository;
        this.comboRepository = comboRepository;
    }

    @Override
    @Transactional
    public Factura checkoutByToken(String token) {
        var carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("No existe carrito para el token"));

        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        // Validar stock
        for (var item : carrito.getItems()) {
            var combo = item.getCombo();
            if (combo.getStock() < item.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para: " + combo.getIdCombo());
            }
        }
        // Descontar stock
        for (var item : carrito.getItems()) {
            var combo = item.getCombo();
            combo.setStock(combo.getStock() - item.getCantidad());
            comboRepository.save(combo);
        }

        // Número de factura
        String numFactura = "F-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(LocalDateTime.now());

        // Construir factura desde carrito (cliente puede ser null si tu esquema lo permite)
        var factura = CheckoutMapper.construirFacturaDesdeCarrito(carrito, numFactura, IVA);
        // Si tu Factura requiere cliente NOT NULL, aquí puedes forzar uno invitado:
        // if (factura.getCliente() == null) factura.setCliente(ensureGuestCliente(token));

        factura = facturaRepository.save(factura);

        // Detalles
        for (var item : carrito.getItems()) {
            var det = CheckoutMapper.construirDetalle(factura, item);
            facturaDetalleRepository.save(det);
        }

        // Limpiar carrito
        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return factura;
    }

    // Si tu tabla FACTURA exige cliente != null, crea/usa un cliente invitado:
    // private final ClienteRepository clienteRepository;
    // private Cliente ensureGuestCliente(String token) { ... }
}

