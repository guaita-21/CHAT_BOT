package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.ClienteService;
import com.paintball.chatbot.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping

    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findOne(@PathVariable int id){
        Optional<Cliente> cliente = clienteService.findOne(id);
        if (cliente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente.orElse(null));
    }
    @PostMapping
    public  ResponseEntity <Cliente> save (@RequestBody Cliente cliente){
        Cliente nuevoCliente = clienteService.save(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }
    @PutMapping ("/{id}")

    public ResponseEntity <Cliente> update (@PathVariable int id, @RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteService.update(id, cliente);
        if(clienteActualizado == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteActualizado);
    }
    @DeleteMapping ("/{id}")
    public  ResponseEntity <Void> delete (@PathVariable int id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
