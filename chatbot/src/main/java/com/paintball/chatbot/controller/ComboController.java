package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.ComboService;
import com.paintball.chatbot.model.Combo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/combos")
public class ComboController {

    @Autowired
    private ComboService comboService;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Combo>> findAll() {
        return ResponseEntity.ok(comboService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Combo> findOne(@PathVariable Integer id) {
        Optional<Combo> combo = comboService.findOne(id);

        if (combo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(combo.get());
    }


    @PostMapping
    public ResponseEntity<Combo> save(@RequestBody Combo combo) {
        return ResponseEntity.ok(comboService.save(combo));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Combo> update(@PathVariable Integer id, @RequestBody Combo combo) {

        Combo comboActualizado = comboService.update(id, combo);

        if (comboActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comboActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        comboService.delete(id);
        return ResponseEntity.noContent().build();
    }
}