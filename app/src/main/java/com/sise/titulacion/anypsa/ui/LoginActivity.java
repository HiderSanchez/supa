package com.sise.titulacion.anypsa.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sise.titulacion.anypsa.entidades.Usuario;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    AutoCompleteTextView txtUsuario;
    TextView txtPassword;
    Button btnEnviar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (AutoCompleteTextView) findViewById(R.id.txtUsuario);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        requestQueue = Volley.newRequestQueue(this);

    }

    public void alClickearBoton(View v) {
        if (Constantes.compruebaConexion(this)) {
            consultar();
        }else{
            Toast.makeText(getBaseContext(),"Verifica tu conexión a internet ", Toast.LENGTH_SHORT).show();
        }

    }

    public void consultar() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest =
                new StringRequest(
                        Request.Method.POST,
                        Constantes.login,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject object = null;
                                try {
                                    object = new JSONObject(response);

                                    //Toast.makeText(LoginActivity.this, object.get("user").toString(), Toast.LENGTH_LONG).show();
                                    if(object.get("status").toString()!="0"){
                                        Usuario usuario = new Usuario();
                                        usuario.setNombre(object.get("username").toString());
                                        //Toast.makeText(LoginActivity.this,object.get("message").toString()+usuario.getNombre(), Toast.LENGTH_LONG).show();
                                        Toast.makeText(LoginActivity.this,"Bienvenido "+usuario.getNombre(),Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(LoginActivity.this, object.get("message").toString(), Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                        headers.put("nombre",txtUsuario.getText().toString());
                        headers.put("clave",txtPassword.getText().toString());
                        return headers;
                    }
                };
        queue.add(jsonObjectRequest);
    }





}

