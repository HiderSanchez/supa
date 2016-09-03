package com.sise.titulacion.anypsa.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;

import java.util.HashMap;
import java.util.Map;


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

//            consultar();

        return view;
    }

    public void consultar() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest jsonObjectRequest =
                new StringRequest(
                        Request.Method.POST,
                        Constantes.PEDIDO_PHP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                JsonObject object= new JsonObject().getAsJsonObject(response);

                                JsonObject jsonObject=object.getAsJsonObject("info");
                                tvNombreCliente.setText(jsonObject.get("razonsocial").toString());
                                tvRucCliente.setText(jsonObject.get("ruc").toString());
                                tvDireccion.setText(jsonObject.get("direccion").toString());
                                jsonObject.get("igv").toString();
                                //todo tvMontoTotal.setText(Double.parseDouble(jsonObject.get("subtotal").toString());
                                Log.d(" ",jsonObject.get("razonsocial").toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("idcliente",Estaticos.idusuario.toString());
                        headers.put("action","getfactura");
                        return headers;
                    }
                };
        queue.add(jsonObjectRequest);
    }

}
