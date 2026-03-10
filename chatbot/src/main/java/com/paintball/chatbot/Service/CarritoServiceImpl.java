package com.paintball.chatbot.Service;

import com.paintball.chatbot.dao.CarritoDao;
import com.paintball.chatbot.dao.CarritoItemDAO;
import com.paintball.chatbot.dao.ClienteDAO;
import com.paintball.chatbot.dao.ComboDAO;
import com.paintball.chatbot.model.Carrito;
import com.paintball.chatbot.model.CarritoItem;
import com.paintball.chatbot.model.Combo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CarritoServiceImpl implements CarritoService {
    private final CarritoDao carritoRepository;
    private final CarritoItemDAO carritoItemRepository;
    private final ClienteDAO clienteRepository;
    private final ComboDAO comboRepository;

    private static final BigDecimal IVA = new BigDecimal("0.15"); // ajusta si corresponde
  //  private final CarritoDao CarritoDao;
  //  private final CarritoItemDAO CarritoItemDao;

    public CarritoServiceImpl(CarritoDao carritoDao,
                              CarritoItemDAO carritoItemDAO,
                              ClienteDAO clienteDAO,
                              ComboDAO comboDAO) {
        this.carritoRepository = carritoDao;
        this.carritoItemRepository = carritoItemDAO;
        this.clienteRepository = clienteDAO;
        this.comboRepository= comboDAO;
    }

    @Override
    @Transactional
    public Carrito getOrCreateByClienteId(int clienteId, String token) {
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + clienteId));

        var carritoOpt = carritoRepository.findByCliente(cliente);
        if (carritoOpt.isPresent()) return carritoOpt.get();

        var carrito = new Carrito();
        carrito.setCliente(cliente);
        carrito.setToken(token);
        carrito.recomprobacionTotalesCompat(); // (ver método al final) — opcional
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito addItem(int clienteId, int comboId, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");

        var carrito = getOrCreateByClienteId(clienteId, null);
        var combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new IllegalArgumentException("Combo no encontrado: " + comboId));

        var itemOpt = carritoItemRepository.findByCarritoAndCombo(carrito, combo);
        if (itemOpt.isPresent()) {
            var item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(combo.getPrecio());
            item.calcTotal();                         // <-- CALCULAR ANTES
            carritoItemRepository.save(item);
        } else {
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setCombo(combo);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(combo.getPrecio());
            item.calcTotal();                         // <-- CALCULAR ANTES
            carrito.getItems().add(item);
        }

        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }


    @Override
    @Transactional
    public Carrito updateItemCantidad(int clienteId, long carritoItemId, int nuevaCantidad) {
        if (nuevaCantidad < 0) throw new IllegalArgumentException("Cantidad no puede ser negativa");

        var carrito = getByClienteId(clienteId);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: " + carritoItemId));

        if (!item.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new IllegalStateException("El item no pertenece al carrito del cliente");
        }

        if (nuevaCantidad == 0) {
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        } else {
            item.setCantidad(nuevaCantidad);
            carritoItemRepository.save(item);
        }

        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(int clienteId, long carritoItemId) {
        updateItemCantidad(clienteId, carritoItemId, 0);
    }

    @Override
    @Transactional
    public void clear(int clienteId) {
        var carrito = getByClienteId(clienteId);
        carrito.getItems().clear();
        carrito.recomputarTotales(IVA);
        carritoRepository.save(carrito);
    }

    @Override
    @Transactional(readOnly = true)
    public Carrito getByClienteId(int clienteId) {
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + clienteId));
        return carritoRepository.findByCliente(cliente)
                .orElseGet(() -> { // devolver vacío sin crear
                    var c = new Carrito();
                    c.setCliente(cliente);
                    return c;
                });
    }



    @Override
    @Transactional
    public Carrito getOrCreateByToken(String token) {
        if (token == null || token.isBlank())
            throw new IllegalArgumentException("Token de carrito requerido");

        return carritoRepository.findByToken(token).orElseGet(() -> {
            var c = new Carrito();
            c.setToken(token);
            c.setSubtotal(BigDecimal.ZERO);
            c.setDescuento(BigDecimal.ZERO);
            c.setImpuestos(BigDecimal.ZERO);
            c.setTotal(BigDecimal.ZERO);
            return carritoRepository.save(c);
        });
    }

    @Override @Transactional(readOnly = true)
    public Carrito getByToken(String token) {
        return carritoRepository.findByToken(token)
                .orElseGet(() -> {
                    var c = new Carrito();
                    c.setToken(token);
                    c.setSubtotal(BigDecimal.ZERO);
                    c.setDescuento(BigDecimal.ZERO);
                    c.setImpuestos(BigDecimal.ZERO);
                    c.setTotal(BigDecimal.ZERO);
                    return c; // no persisto en GET
                });
    }

    @Override
    @Transactional
    public Carrito addItem(String token, int comboId, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");

        var carrito = getOrCreateByToken(token);
        var combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new IllegalArgumentException("Combo no encontrado: " + comboId));

        var itemOpt = carritoItemRepository.findByCarritoAndCombo(carrito, combo);
        if (itemOpt.isPresent()) {
            var item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(combo.getPrecio());
            item.calcTotal();
            carritoItemRepository.save(item);
        } else {
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setCombo(combo);
            item.setCantidad(cantidad);
            item.setPrecioUnitario((combo.getPrecio()));
            item.calcTotal();
            carrito.getItems().add(item);
        }

        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito updateItemCantidad(String token, long carritoItemId, int nuevaCantidad) {
        var carrito = getOrCreateByToken(token);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: " + carritoItemId));

        if (!item.getCarrito().getIdCarrito().equals(carrito.getIdCarrito()))
            throw new IllegalStateException("El item no pertenece al carrito del token");

        if (nuevaCantidad <= 0) {
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        } else {
            item.setCantidad(nuevaCantidad);
            item.calcTotal();
            carritoItemRepository.save(item);
        }

        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(String token, long carritoItemId) {
        updateItemCantidad(token, carritoItemId, 0);
    }

    @Override
    @Transactional
    public void clearByToken(String token) {
        var carrito = getOrCreateByToken(token);
        carrito.getItems().clear();
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }

}
