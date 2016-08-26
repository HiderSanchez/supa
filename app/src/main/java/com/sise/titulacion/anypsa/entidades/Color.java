package com.sise.titulacion.anypsa.entidades;

/**
 * Creado por Luis Negrón el 15/08/16.
 * Email lann8605@gmail.com
 */
public class Color {
    private int idColor;
    private String color;
    private String hexadecimal;
    private Double precio;
    private int stock;

    public Color() {
    }

    public Color(int idColor, String color, String hexadecimal, Double precio, int stock) {
        this.idColor = idColor;
        this.color = color;
        this.hexadecimal = hexadecimal;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Color{" +
                "idColor=" + idColor +
                ", color='" + color + '\'' +
                ", hexadecimal='" + hexadecimal + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
