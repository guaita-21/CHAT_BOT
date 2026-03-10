package com.paintball.chatbot.controller;

import com.paintball.chatbot.Service.GuestCheckoutService;
import com.paintball.chatbot.model.Factura;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/guest/checkout")
//@CrossOrigin(origins = "http://localhost:4200") // ajusta si ya tienes CORS global
public class GuestCheckoutController {
    private final GuestCheckoutService service;

    public GuestCheckoutController(GuestCheckoutService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Factura> checkout(@RequestParam String token) {
        return ResponseEntity.ok(service.checkoutByToken(token));
    }
}

