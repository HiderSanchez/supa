package com.sise.titulacion.anypsa.fragmentos;

import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.actividades.MainActivity;
import com.sise.titulacion.anypsa.adaptadores.CarritoComprasAdaptador;
import com.sise.titulacion.anypsa.entidades.DetallePedido;
import com.sise.titulacion.anypsa.entidades.Pedido;
import com.sise.titulacion.anypsa.entidades.Total;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CarritoCompras extends Fragment {
    Pedido pedido= Estaticos.cargarPedido();
    RecyclerView recyclerView;
    Button btnEnviarPedido;
    CarritoComprasAdaptador catalogoAdapter;
    TextView tvTotal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_carrito_compras, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCarrito);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inicializarAdaptadorCarrritoCompras();

        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(pedido.getTotal()));
        btnEnviarPedido = (Button) view.findViewById(R.id.button);
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarPedido();

            }
        });
        return view;
    }

    void inicializarAdaptadorCarrritoCompras() {
        catalogoAdapter = new CarritoComprasAdaptador(Estaticos.carritoProductos);
        recyclerView.setAdapter(catalogoAdapter);
    }

    public  void enviarPedido() {
    pedido= Estaticos.cargarPedido();

        if (pedido.getDetallePedido().size()>0) {
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


                                        Estaticos.carritoProductos.clear();
                                        catalogoAdapter.notifyDataSetChanged();
                                        recyclerView.clearOnScrollListeners();
                                        EventBus.getDefault().post(new Total("0.0"));

                                        Snackbar.make(getView(), jsonObject.get("message").toString(), Snackbar.LENGTH_SHORT).show();
                                        Log.d("tag", "onResponse-header: " + jsonObject.get("message").toString());
                                        Log.d("tag", "onResponse-error: " + jsonObject.get("error").toString());



                                    /*    FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        Catalogo catalogo= new Catalogo();
                                        fragmentTransaction.replace(R.id.contenedor, catalogo).commit();*/
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);

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
                            o.put("action", "newpedido");
                            o.put("cliente", Estaticos.idusuario);
                            o.put("subtotal", String.valueOf(pedido.getSubtotal()));
                            o.put("igv", String.valueOf(pedido.getIgv()));
                            for (int i = 0; i < pedido.getDetallePedido().size(); i++) {
                                DetallePedido d = pedido.getDetallePedido().get(i);
                                o.put("productos[" + i + "][idproducto]", String.valueOf(d.getIdProducto()));
                                o.put("productos[" + i + "][item]", String.valueOf(d.getItem()));
                                o.put("productos[" + i + "][cantidad]", String.valueOf(d.getCantidad()));
                                o.put("productos[" + i + "][precio]", String.valueOf(d.getPrecio()));
                                o.put("productos[" + i + "][idcolor]", String.valueOf(d.getIdcolor()));
                            }
                            String s = String.valueOf(o);
                            Log.d("tag", "getParams: " + s);
                            return o;
                        }
                    };
            queue.add(jsonObjectRequest);
        }else{
            Snackbar.make(getView()," No tiene productos para enviar", Snackbar.LENGTH_SHORT).show();
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Total event) {
        tvTotal.setText(event.total);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

}
