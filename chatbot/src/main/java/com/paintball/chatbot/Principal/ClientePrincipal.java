package com.paintball.chatbot.Principal;

import com.paintball.chatbot.model.Cliente;

import java.util.Date;

public class ClientePrincipal {
    public static  void main(String[]args){
        Cliente cliente= new Cliente( 1,"Juan","Lopez","1254784512","0923657854","juan23@gmail.com",new Date());
        System.out.println(cliente.toString());
    }
}
