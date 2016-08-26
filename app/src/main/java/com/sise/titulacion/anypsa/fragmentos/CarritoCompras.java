package com.sise.titulacion.anypsa.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.adaptadores.CarritoComprasAdapter;
import com.sise.titulacion.anypsa.adaptadores.PedidoAdaptador;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.utils.Estaticos;

import java.util.ArrayList;


public class CarritoCompras extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_carrito_compras, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCarrito);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inicializarAdaptadorCarrritoCompras();
        return view;
    }

    void inicializarAdaptadorCarrritoCompras() {
        PedidoAdaptador catalogoAdapter = new PedidoAdaptador(Estaticos.carritoProductos);
        recyclerView.setAdapter(catalogoAdapter);

    }
}
