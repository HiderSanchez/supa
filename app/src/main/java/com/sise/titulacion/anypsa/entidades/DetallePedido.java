package com.sise.titulacion.anypsa.entidades;

import java.text.DecimalFormat;

public class DetallePedido {
    private int idcolor;
    private int idProducto;
    private int item;
    private int cantidad;
    private double precio;

    public DetallePedido() {
    }

    public int getIdcolor() {
        return idcolor;
    }

    public void setIdcolor(int idcolor) {
        this.idcolor = idcolor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        DecimalFormat df = new DecimalFormat("###.00");
        return   Double.valueOf(df.format(precio));
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
