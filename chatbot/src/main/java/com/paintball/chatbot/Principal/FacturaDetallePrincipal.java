package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.Combo;
import com.paintball.chatbot.model.FacturaDetalle;

import java.math.BigDecimal;

public class FacturaDetallePrincipal {

    public static void main(String[] args) {

        Factura factura = new Factura();
        Combo combo = new Combo();

        FacturaDetalle facturaDetalle = new FacturaDetalle(
                1,
                "Combo Premium",
                2,
                BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(60.00),
                factura,
                combo
        );

        System.out.println(facturaDetalle);
    }
}