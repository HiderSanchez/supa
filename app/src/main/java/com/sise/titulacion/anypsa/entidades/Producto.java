package com.sise.titulacion.anypsa.entidades;

import java.util.ArrayList;

/**
 * Creado por Luis Negrón el 14/08/16.
 * Email lann8605@gmail.com
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private String imagen;
    private String categoria;
    private String marca;
    private int cantidad;
    private int colorId;
    //El color sera reemplazado por una lista
    private ArrayList<Color> colores;

    public Producto() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public ArrayList<Color> getColores() {
        return colores;
    }

    public void setColores(ArrayList<Color> colores) {
        this.colores = colores;
    }

    public Producto(int idProducto, String nombre, String imagen, String categoria, String marca, ArrayList<Color> colores) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.imagen = imagen;
        this.categoria = categoria;
        this.marca = marca;
        this.colores = colores;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", categoria='" + categoria + '\'' +
                ", marca='" + marca + '\'' +
                ", colores=" + colores +
                '}';
    }
}
