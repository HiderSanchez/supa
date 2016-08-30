package com.sise.titulacion.anypsa.adaptadores;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.entidades.Historial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hider on 25/08/16.
 */
public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {
    ArrayList<Historial> historials = new ArrayList<>();

    public HistorialAdapter(ArrayList<Historial> historials) {
        this.historials = historials;
    }


    @Override
    public HistorialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_historial_pedidos, parent, false);

        return new HistorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistorialViewHolder historialViewHolder, final int position) {
        final Historial historial = historials.get(position);
        historial.pintarEstadoPedido();
        historial.convertirFecha();
        historialViewHolder.tvEstadoProceso.setText("Estado de su pedido : " + historial.getEstado());
        historialViewHolder.tvFechaEntrega.setText(historial.getFechaEntrega());
        historialViewHolder.tvFechaPedido.setText(historial.getFechaPedido());
        historialViewHolder.tvIdPedido.setText(String.valueOf("Pedido N° : " + historial.getIdPedido()));
        historialViewHolder.tvMonto.setText(String.valueOf(historial.getIgv() + historial.getSubTotal()));
        historialViewHolder.tvPagado.setText(historial.getPagado());

        historialViewHolder.layout.setBackgroundColor(android.graphics.Color.parseColor(historial.pintarEstadoPedido()));
        historialViewHolder.tvIdPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo implemtar onclick

            }
        });
    }

    @Override
    public int getItemCount() {
        return historials.size();
    }


    public static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPedido;
        TextView tvFechaPedido;
        TextView tvFechaEntrega;
        TextView tvMonto;
        TextView tvPagado;
        TextView tvEstadoProceso;
        CardView layout;

        public HistorialViewHolder(View v) {
            super(v);
            tvIdPedido = (TextView) v.findViewById(R.id.tvIdPedido);
            tvFechaPedido = (TextView) v.findViewById(R.id.tvFechaPedido);
            tvFechaEntrega = (TextView) v.findViewById(R.id.tvFechaEntrega);
            tvMonto = (TextView) v.findViewById(R.id.tvMonto);
            tvPagado = (TextView) v.findViewById(R.id.tvPagado);
            tvEstadoProceso = (TextView) v.findViewById(R.id.tvEstadoProceso);
            layout = (CardView) v.findViewById(R.id.cvHistorial);
        }
    }
}
