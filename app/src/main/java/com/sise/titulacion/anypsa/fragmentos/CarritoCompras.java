package com.sise.titulacion.anypsa.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.adaptadores.PedidoAdaptador;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CarritoCompras extends Fragment {
    RecyclerView recyclerView;
    Button btnEnviarPedido;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_carrito_compras, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCarrito);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inicializarAdaptadorCarrritoCompras();

        btnEnviarPedido =(Button) view.findViewById(R.id.button);
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarPedido();

            }
        });

        return view;
    }

    void inicializarAdaptadorCarrritoCompras() {
        PedidoAdaptador catalogoAdapter = new PedidoAdaptador(Estaticos.carritoProductos);
        recyclerView.setAdapter(catalogoAdapter);

    }
    public void enviarPedido() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final StringRequest jsonObjectRequest =
                new StringRequest(
                        Request.Method.POST,
                        Constantes.PEDIDO_PHP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    Estaticos.carritoProductos=null;
                                    Snackbar.make(getView(), jsonObject.get("message").toString(), Snackbar.LENGTH_LONG).show();
                                    Log.d("tag", "onResponse-header: "+jsonObject.get("message").toString());
                                    Log.d("tag", "onResponse-error: "+jsonObject.get("error").toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> o = new HashMap<String, String>();
                        o.put("action","newpedido");
                        o.put("cliente", "1");
                        o.put("subtotal", "999.99");
                        o.put("igv", "000");
                        for (int i = 0; i < Estaticos.carritoProductos.size(); i++) {
                            o.put("productos["+i+"][idproducto]",String.valueOf(Estaticos.carritoProductos.get(i).getIdProducto()));
                            o.put("productos["+i+"][item]",String.valueOf(i));
                            o.put("productos["+i+"][cantidad]",String.valueOf(Estaticos.carritoProductos.get(i).getCantidad()));
                            o.put("productos["+i+"][precio]",String.valueOf("50.0"));
                            o.put("productos["+i+"][idcolor]",String.valueOf(Estaticos.carritoProductos.get(i).getColorId()));
                        }

                        String s = String.valueOf(o);
                        Log.d("tag", "getParams: " + s);
                        return o;
                    }
                };
        queue.add(jsonObjectRequest);
    }
}
