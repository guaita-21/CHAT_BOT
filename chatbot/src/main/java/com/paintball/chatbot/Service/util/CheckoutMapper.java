package com.paintball.chatbot.Service.util;

import com.paintball.chatbot.model.Carrito;
import com.paintball.chatbot.model.CarritoItem;
import com.paintball.chatbot.model.Factura;
import com.paintball.chatbot.model.FacturaDetalle;

import java.math.BigDecimal;
import java.util.Date;

public class CheckoutMapper {
    private CheckoutMapper() {}

    public static Factura construirFacturaDesdeCarrito(Carrito carrito, String numFactura, double tasaIva) {
        Factura f = new Factura();
        f.setNum_factura(numFactura);
        f.setFechaEmision(new Date());
        f.setCliente(carrito.getCliente());

        double subtotal = carrito.getItems().stream()
                .mapToDouble(i -> i.getTotal().doubleValue())
                .sum();

        double iva = Math.max(0, subtotal) * tasaIva;
        double total = subtotal + iva;

        f.setSubtotal(BigDecimal.valueOf(subtotal));
        f.setIva(BigDecimal.valueOf(iva));
        f.setTotal(BigDecimal.valueOf(total));

        return f;
    }

    public static FacturaDetalle construirDetalle(Factura factura, CarritoItem item) {
        FacturaDetalle d = new FacturaDetalle();
        d.setFactura(factura);
        d.setCombo(item.getCombo());
        d.setCantidad(item.getCantidad());
        d.setTotalLinea(item.getTotal());
        return d;
    }
}

