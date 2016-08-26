package com.sise.titulacion.anypsa.fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Creado por Luis Negrón el 07/08/16.
 * Email lann8605@gmail.com
 */
public class LoginFragment extends Fragment {
    /**
     * Etiqueta para depuración
     */
    private static final String TAG = LoginFragment.class.getSimpleName();

    AutoCompleteTextView txtUsuario;
    TextView txtPassword;
    Button btnEnviar;

    public LoginFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflando layout del fragmento
        View v = inflater.inflate(R.layout.activity_login, container, false);

        // Obtención de instancias controles
        txtUsuario = (AutoCompleteTextView) v.findViewById(R.id.txtUsuario);
        txtPassword = (TextView) v.findViewById(R.id.txtPassword);
        btnEnviar = (Button) v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mensaje","entre al clic");
                Toast.makeText(
                        getActivity(),
                        "bienvenido ",
                        Toast.LENGTH_LONG).show();
                validar();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*
        int id = item.getItemId();

        switch (id) {
            case R.id.btnEnviar:// DESCARTAR
               validar();
                return  true;

        }*/

        return super.onOptionsItemSelected(item);
    }


    public void validar() {

        // Obtener valores actuales de los controles
        final String usuario = txtUsuario.getText().toString();
        final String password = txtPassword.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("nombre", txtUsuario.getText().toString());
        map.put("password", txtPassword.getText().toString());

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.login,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );

    }

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            String usuario = response.getString("user");
            // Obtener mensaje
            String mensaje = response.getString("Mensaje");

            switch (estado) {
                case "ingreso":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            "bienvenido "+usuario,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "error":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

