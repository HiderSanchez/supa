package com.sise.titulacion.anypsa.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.sise.titulacion.anypsa.adaptadores.ProductoAdaptador;
import com.sise.titulacion.anypsa.deserializador.DeserializadorProductoJson;
import com.sise.titulacion.anypsa.controladores.ProductoResponse;
import com.sise.titulacion.anypsa.utils.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatalogoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CatalogoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Declarar recyclewrview
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProductoAdaptador productoAdaptador;

    public CatalogoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvCatalogo);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        llenarData();

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void llenarData() {
        final RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                ProductoResponse productoResponse = gson.fromJson(response.toString(),ProductoResponse.class);
                                for (int i = 0; i < productoResponse.getProductos().size(); i++) {
                                    System.out.println(productoResponse.getProductos().get(i).toString());
                                }



                                productoAdaptador = new ProductoAdaptador(productoResponse.getProductos());
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
}
