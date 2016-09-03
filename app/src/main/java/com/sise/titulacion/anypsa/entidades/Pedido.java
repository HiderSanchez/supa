package com.sise.titulacion.anypsa.entidades;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Creado por Luis Negrón el 22/08/16.
 * Email lann8605@gmail.com
 */
public class Pedido {
    private int idCliente;
    private double subtotal;
    private double igv;
    private double total;
    private ArrayList<DetallePedido> detallePedido;

    public Pedido() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getSubtotal() {
        DecimalFormat df = new DecimalFormat("###.00");
        return   Double.valueOf(df.format(subtotal));
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIgv() {
        DecimalFormat df = new DecimalFormat("###.00");
        return   Double.valueOf(df.format(igv));
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        DecimalFormat df = new DecimalFormat("###.00");
        return   Double.valueOf(df.format(total));
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(ArrayList<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
