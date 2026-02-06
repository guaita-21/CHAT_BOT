package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.FacturaDetalle;

import java.math.BigDecimal;

public class FacturaDetallePrincipal {
    public static  void main(String[]args){
        FacturaDetalle facturaDetalle = new FacturaDetalle(1,"comboPremiun",2,new BigDecimal(30.00), new BigDecimal(60.00));
    }
}
