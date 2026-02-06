package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.Combo;
import java.math.BigDecimal;

public class CombosPrincipal {

    public static void main(String[] args) {

        Combo combo = new Combo(
                2,
                "HappyMoment",
                250,
                2,
                5,
                new BigDecimal("40")
        );

        System.out.println(combo);
    }
}
