package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.ReservasService;
import com.paintball.chatbot.model.Reservas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservaService;


    @GetMapping
    public ResponseEntity<List<Reservas>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reservas> findOne(@PathVariable Integer id) {
        Optional<Reservas> reserva = reservaService.findOne(id);

        if (reserva.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva.get());
    }


    @PostMapping
    public ResponseEntity<Reservas> save(@RequestBody Reservas reserva) {
        return ResponseEntity.ok(reservaService.save(reserva));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Reservas> update(@PathVariable Integer id, @RequestBody Reservas reserva) {

        Reservas reservaActualizada = reservaService.update(id, reserva);

        if (reservaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservaActualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
