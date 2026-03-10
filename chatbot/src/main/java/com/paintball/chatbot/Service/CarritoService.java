package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Carrito;

public interface CarritoService {
    Carrito getOrCreateByClienteId(int clienteId, String token);
    Carrito addItem(int clienteId, int comboId, int cantidad);
    Carrito updateItemCantidad(int clienteId, long carritoItemId, int nuevaCantidad);
    void removeItem(int clienteId, long carritoItemId);
    void clear(int clienteId);
    Carrito getByClienteId(int clienteId);

    Carrito getOrCreateByToken(String token);
    Carrito addItem(String token, int comboId, int cantidad);
    Carrito updateItemCantidad(String token, long carritoItemId, int nuevaCantidad);
    void removeItem(String token, long carritoItemId);
    void clearByToken(String token);
    Carrito getByToken(String token);
}
