package com.sise.titulacion.anypsa.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.adaptadores.CatalogoAdapter;
import com.sise.titulacion.anypsa.controladores.ProductoResponse;
import com.sise.titulacion.anypsa.deserializador.DeserializadorProductoJson;
import com.sise.titulacion.anypsa.entidades.Mensajes;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Catalogo extends Fragment {
    RecyclerView recyclerView;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_catalogo, container, false);
        button = (Button) view.findViewById(R.id.btnMisCompras);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Estaticos.carritoProductos.size() > 0)) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    CarritoCompras carritoCompras = new CarritoCompras();
                    fragmentTransaction.replace(R.id.contenedor, carritoCompras).commit();
                    EventBus.getDefault().post(new Mensajes("Mi Lista de Compras"));
                } else {
                    Snackbar.make(view, "No tiene productos en su lista de compras", Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();


                }

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.rvCatalogo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        llenarCatalogoDeProductos();
        return view;
    }


    public void llenarCatalogoDeProductos() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest jsonObjectRequest =
                new StringRequest(
                        Request.Method.GET,
                        Constantes.catalogo,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(ProductoResponse.class, new DeserializadorProductoJson());
                                Gson gson = gsonBuilder.create();
                                ProductoResponse productoResponse = gson.fromJson(response.toString(), ProductoResponse.class);
                                for (int i = 0; i < productoResponse.getProductos().size(); i++) {
                                    System.out.println(productoResponse.getProductos().get(i).toString());
                                }
                                CatalogoAdapter productoAdaptador = new CatalogoAdapter(productoResponse.getProductos());
                                recyclerView.setAdapter(productoAdaptador);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        queue.add(jsonObjectRequest);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Mensajes event) {
        button.setText(event.mensaje);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
}
