package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.Cliente;
import com.paintball.chatbot.model.Factura;

import java.math.BigDecimal;
import java.util.Date;

public class FacturaPrincipal {
    public static void main(String[] args) {
        Cliente cliente = new Cliente( 1,"Juan","Lopez","1254784512","0923657854","juan23@gmail.com",new Date());

        Factura factura=new Factura();
        factura.setIdFactura(1);
        factura.setFechaEmision(new Date());
        factura.setSubtotal(new BigDecimal(60.00));
        factura.setIva( new BigDecimal(15.00));
        factura.setTotal(new BigDecimal(75.00));
        factura.setEstado("Confirmada");
        //Inyeccion

    }

}


