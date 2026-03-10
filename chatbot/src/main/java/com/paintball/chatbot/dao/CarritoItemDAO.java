package com.paintball.chatbot.dao;

import com.paintball.chatbot.model.Carrito;
import com.paintball.chatbot.model.CarritoItem;
import com.paintball.chatbot.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemDAO  extends JpaRepository<CarritoItem, Long> {
    Optional<CarritoItem> findByCarritoAndCombo(Carrito carrito, Combo combo);
    List<CarritoItem> findByCarrito(Carrito carrito);
}