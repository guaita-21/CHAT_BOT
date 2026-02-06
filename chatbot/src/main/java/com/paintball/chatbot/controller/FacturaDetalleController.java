package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.FacturaDetalleService;
import com.paintball.chatbot.model.FacturaDetalle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factura-detalles")
public class FacturaDetalleController {

    @Autowired
    private FacturaDetalleService facturaDetalleService;


    @GetMapping
    public ResponseEntity<List<FacturaDetalle>> findAll() {
        return ResponseEntity.ok(facturaDetalleService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FacturaDetalle> findOne(@PathVariable Integer id) {
        Optional<FacturaDetalle> detalle = facturaDetalleService.findOne(id);

        if (detalle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle.get());
    }


    @PostMapping
    public ResponseEntity<FacturaDetalle> save(@RequestBody FacturaDetalle detalle) {
        return ResponseEntity.ok(facturaDetalleService.save(detalle));
    }


    @PutMapping("/{id}")
    public ResponseEntity<FacturaDetalle> update(@PathVariable Integer id, @RequestBody FacturaDetalle detalle) {

        FacturaDetalle detalleActualizado =
                facturaDetalleService.update(id, detalle);

        if (detalleActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalleActualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        facturaDetalleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
