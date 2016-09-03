package com.sise.titulacion.anypsa.utils;

import com.sise.titulacion.anypsa.entidades.DetallePedido;
import com.sise.titulacion.anypsa.entidades.Pedido;
import com.sise.titulacion.anypsa.entidades.Producto;

import java.util.ArrayList;

/**
 * Creado por Luis Negrón el 23/08/16.
 * Email lann8605@gmail.com
 */
public class Estaticos {
    public static int contador = 0;

    public static ArrayList<DetallePedido> listaPedidos = new ArrayList<>();

    public static ArrayList<Producto> carritoProductos = new ArrayList<>();
    public static String idusuario;


    public static Pedido cargarPedido() {
        Pedido pedido = new Pedido();
        ArrayList<DetallePedido> detallePedidos = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < Estaticos.carritoProductos.size(); i++) {
            Producto producto = Estaticos.carritoProductos.get(i);
            DetallePedido detallePedido = new DetallePedido();
            for (int j = 0; j < producto.getColores().size(); j++) {
                detallePedido.setPrecio(producto.getColores().get(j).getPrecio());
                detallePedido.setIdcolor(producto.getColores().get(j).getIdColor());
            }
            detallePedido.setItem(i + 1);
            detallePedido.setCantidad(producto.getCantidad());
            detallePedido.setIdProducto(producto.getIdProducto());
            total = total + (detallePedido.getCantidad() * detallePedido.getPrecio());
            detallePedidos.add(detallePedido);
        }
        pedido.setDetallePedido(detallePedidos);
        pedido.setIgv(total * 0.18);
        pedido.setSubtotal(total - pedido.getIgv());
        pedido.setTotal(total);
        return pedido;
    }
}
