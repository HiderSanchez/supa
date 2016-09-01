package com.sise.titulacion.anypsa.actividades;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.sise.titulacion.anypsa.utils.Estaticos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{
    AutoCompleteTextView txtUsuario;
    TextView txtPassword;
    Button btnEnviar;
    RequestQueue requestQueue;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = (AutoCompleteTextView) findViewById(R.id.txtUsuario);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Anypsa Iniciar Sesion");
        requestQueue = Volley.newRequestQueue(this);
    }

    public void alClickearBoton(View v) {
        if (Constantes.compruebaConexion(this)) {

            if (!TextUtils.isEmpty(txtUsuario.getText())) {

                if (!TextUtils.isEmpty(txtPassword.getText())) {

                    consultar();
                }else{
                    txtPassword.requestFocus();
                   txtPassword.setError("Ingrese una Contraseña");
                }
            }else{
                txtUsuario.requestFocus();
                txtUsuario.setError("Ingrese su Usuario");
            }
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
                                JSONObject object;
                                try {
                                    object = new JSONObject(response);

                                    if(!object.get("status").toString().equals("0")){
                                        Usuario usuario= new Usuario();
                                        usuario.setNombre(object.get("username").toString());
                                        usuario.setIdusuario(Integer.parseInt(object.get("idusuario").toString()));
                                        Estaticos.idusuario=String.valueOf(usuario.getIdusuario());
                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Snackbar.make(
                                                getWindow().getDecorView(),
                                                object.get("message").toString(),
                                                Snackbar.LENGTH_INDEFINITE)
                                                .setAction("De Nuevo", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        txtUsuario.requestFocus();
                                                        txtUsuario.setSelectAllOnFocus(true);
                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    Snackbar.make(getWindow().getDecorView(),String.valueOf(e.toString()),Snackbar.LENGTH_SHORT).show();
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

