package com.paintball.chatbot.dto;


    public class AddItemRequest {

        private int comboId;
        private int cantidad;

        public AddItemRequest() {
        }

        public AddItemRequest(int comboId, int cantidad) {
            this.comboId = comboId;
            this.cantidad = cantidad;
        }

        public int getComboId() {
            return comboId;
        }

        public void setComboId(int comboId) {
            this.comboId = comboId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }


