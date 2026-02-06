package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.SedesService;
import com.paintball.chatbot.model.Sedes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
public class SedesController {

    @Autowired
    private SedesService sedeService;


    @GetMapping
    public ResponseEntity<List<Sedes>> findAll() {
        return ResponseEntity.ok(sedeService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sedes> findOne(@PathVariable Integer id) {
        Optional<Sedes> sede = sedeService.findOne(id);

        if (sede.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sede.get());
    }


    @PostMapping
    public ResponseEntity<Sedes> save(@RequestBody Sedes sede) {
        return ResponseEntity.ok(sedeService.save(sede));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Sedes> update(@PathVariable int id, @RequestBody Sedes sede) {

        Sedes sedeActualizada = sedeService.update(id, sede);

        if (sedeActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sedeActualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sedeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
