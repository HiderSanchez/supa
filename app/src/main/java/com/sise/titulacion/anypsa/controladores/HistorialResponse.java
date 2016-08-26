package com.sise.titulacion.anypsa.controladores;

import com.sise.titulacion.anypsa.entidades.Historial;

import java.util.ArrayList;

/**
 * Created by hider on 26/08/16.
 */
public class HistorialResponse {
    private ArrayList<Historial> historials;

    public ArrayList<Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(ArrayList<Historial> historials) {
        this.historials = historials;
    }
}
