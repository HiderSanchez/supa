package com.sise.titulacion.anypsa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EstadoCuenta extends Fragment {
    TextView tvNombreCliente;
    TextView tvRucCliente;
    TextView tvDireccion;
    TextView tvLineaCredito;
    TextView tvPeriodoFacturacion;
    TextView tvMontoTotal;


    public EstadoCuenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estado_cuenta, container, false);

        tvDireccion= (TextView) view.findViewById(R.id.tvDireccion);
        tvLineaCredito= (TextView) view.findViewById(R.id.tvLineaCredito);
        tvMontoTotal= (TextView) view.findViewById(R.id.tvMontoTotal);
        tvNombreCliente = (TextView) view.findViewById(R.id.tvNombreCliente);
        tvPeriodoFacturacion= (TextView) view.findViewById(R.id.tvPeriodoFacturacion);
        tvRucCliente= (TextView) view.findViewById(R.id.tvRuc);


        return view;
    }

}
