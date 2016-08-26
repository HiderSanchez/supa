package com.sise.titulacion.anypsa.entidades;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Historial {
    private int idPedido;
    private String fechaPedido;
    private String fechaEntrega;
    private double subTotal;
    private double igv;
    private String estado;
    private int pago;
    private String pagado;

    public Historial(int idPedido, String fechaPedido, String fechaEntrega, double subTotal, double igv, int pagado, String estado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.subTotal = subTotal;
        this.igv = igv;
        this.pago = pagado;
        this.estado = estado;
    }

    public Historial() {

    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void convertirFecha() {
        Date date = null;
        try {
            Locale espanol = new Locale("es", "ES");
       //     DateFormat format = new SimpleDateFormat("MMMM d, yyyy", espanol);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat format1= new SimpleDateFormat("d MMMM, yyyy", espanol);

            date = format.parse(fechaEntrega); //Obtienes el String como Date.

            setFechaEntrega(format1.format(date));

            date = format.parse(fechaPedido);
            setFechaPedido(format1.format(date));
            Log.i("INFO", date.toString());
            Log.i("inf",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPagado() {
        return pagado;
    }

    public String pintarEstadoPedido() {
        String color = "";
        if (pago == 1) {
            color = "#81F781";
            pagado = "Pagado";
        } else if (pago == 0) {
            pagado = "No pagado";
            color = "#F78181";
        } else {
            color = "#FFFFFF";
        }
        return color;
    }

}
