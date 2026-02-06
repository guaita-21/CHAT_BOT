package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.Reservas;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class ReservasPrincipal {
    public static void main(String[] args) {
        Reservas reservas = new Reservas(
                1,
                new Date(),
                LocalTime.of(10, 0), // 10:00
                "Confirmada",
                new BigDecimal(40)
        );

    }
}
