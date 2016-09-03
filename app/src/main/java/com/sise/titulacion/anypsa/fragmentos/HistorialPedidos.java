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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.adaptadores.HistorialAdapter;
import com.sise.titulacion.anypsa.controladores.HistorialResponse;
import com.sise.titulacion.anypsa.deserializador.DeserializadorHistorial;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HistorialPedidos extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_historial_pedidos, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvHistorial);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        llenarData();
        return view;
    }

    public void llenarData() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest jsonObjectRequest =
                new StringRequest(
                        Request.Method.POST,
                        Constantes.PEDIDO_PHP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                int status = -1;
                                String msj="";
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    status = (int) jsonObject.get("status");
                                    msj=jsonObject.get("message").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.d("log",String.valueOf(status));

                                //  JsonObject object = new JsonObject(response.toString());

                                //     int status = object.get("status").getAsInt();
                                if (status <=0) {
                                    Snackbar.make(getView(),msj,Snackbar.LENGTH_LONG).show();
                                }else {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    gsonBuilder.registerTypeAdapter(HistorialResponse.class, new DeserializadorHistorial());
                                    Gson gson = gsonBuilder.create();
                                    HistorialResponse historialResponse = gson.fromJson(response.toString(), HistorialResponse.class);
                                    for (int i = 0; i < historialResponse.getHistorials().size(); i++) {
                                        System.out.println(historialResponse.getHistorials().get(i).toString());
                                    }
                                    HistorialAdapter productoAdaptador = new HistorialAdapter(historialResponse.getHistorials());
                                    recyclerView.setAdapter(productoAdaptador);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("action", "listpedidos");
                        headers.put("idcliente", Estaticos.idusuario);
                        return headers;
                    }
                };

        queue.add(jsonObjectRequest);
    }
}
