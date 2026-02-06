package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.FacturaService;
import com.paintball.chatbot.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {


    @Autowired
    private FacturaService facturaservice;

    @GetMapping
    public ResponseEntity<List<Factura>> findAll(){
        return ResponseEntity.ok( facturaservice.findAll());
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Factura> findOne(@PathVariable int id){
        Optional<Factura> factura = facturaservice.findOne(id);

        if (factura == null){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(factura.orElse(null));
    }
    @PostMapping
    public  ResponseEntity <Factura> save(@RequestBody Factura factura){
        return  ResponseEntity.ok( facturaservice.save(factura));
    }
    @PutMapping("/{id}")
    public ResponseEntity <Factura> update(@PathVariable int id, @RequestBody Factura factura){
        Factura facturaActualizada = facturaservice.update(id, factura);

        if (facturaActualizada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facturaActualizada);
    }
    @DeleteMapping ("/{id}")
    public  ResponseEntity<Void>  delete(@PathVariable int id){
        facturaservice.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
