package com.sise.titulacion.anypsa.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sise.titulacion.anypsa.entidades.Pedido;

import java.util.List;

/**
 * Creado por Luis Negrón el 02/08/16.
 * Email lann8605@gmail.com
 */
public class Constantes {

    /**
     * Puerto que utilizas para la conexión.
     */
    private static final String PUERTO_HOST = " ";

    public static final String msgError = "{\"result\":[{\"message\":\"error\"}]}";
    //{"result":[{"message":"error"}]}
    /**
     * DIRECCION URL DONDE APUNTA EL WEB SERVICE
     */
    private static final String IP = "http://anyservice.esy.es";
    /**
     * URLs del Web Service
     */
    public static final String login = IP  + "/login.php";
    public static final String catalogo = IP + "/list_products.php";
    public static final String HISTORIAL = IP + "/pedido.php";

    public static boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }


}
