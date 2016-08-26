package com.sise.titulacion.anypsa.controladores;

import com.sise.titulacion.anypsa.entidades.Producto;

import java.util.ArrayList;

/**
 * Created by hider on 18/08/16.
 */
public class ProductoResponse {
    public ProductoResponse(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    private  ArrayList<Producto> productos;

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
