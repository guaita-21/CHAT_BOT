package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.EquiposService;
import com.paintball.chatbot.model.Equipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipos")
public class EquiposController {

    @Autowired
    private EquiposService equiposService;


    @GetMapping
    public ResponseEntity<List<Equipos>> findAll() {
        return ResponseEntity.ok(equiposService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Equipos> findOne(@PathVariable Integer id) {
        Optional<Equipos> equipo = equiposService.findOne(id);

        if (equipo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipo.get());
    }


    @PostMapping
    public ResponseEntity<Equipos> save(@RequestBody Equipos equipo) {
        return ResponseEntity.ok(equiposService.save(equipo));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Equipos> update(@PathVariable Integer id, @RequestBody Equipos equipo) {

        Equipos equipoActualizado = equiposService.update(id, equipo);

        if (equipoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipoActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        equiposService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
