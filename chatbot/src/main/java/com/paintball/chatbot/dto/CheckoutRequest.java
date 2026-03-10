package com.paintball.chatbot.dto;

public class CheckoutRequest {

    private int comboId;
    private String metodoPago; // opcional si luego agregas pasarela

    public int getComboId() { return comboId; }
    public void setComboId(int comboId) { this.comboId = comboId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}

