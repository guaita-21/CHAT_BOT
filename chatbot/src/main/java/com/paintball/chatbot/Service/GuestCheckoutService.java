package com.paintball.chatbot.Service;

import com.paintball.chatbot.model.Factura;

public interface GuestCheckoutService {
    Factura checkoutByToken(String token);
}

