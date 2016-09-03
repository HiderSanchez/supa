package com.sise.titulacion.anypsa.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sise.titulacion.anypsa.entidades.Color;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.controladores.ProductoResponse;
import com.sise.titulacion.anypsa.utils.productoJsonKeys;


import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hider on 18/08/16.
 */
public class DeserializadorProductoJson implements JsonDeserializer<ProductoResponse> {
    @Override
    public ProductoResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        // obtine toda la data
       ProductoResponse productoResponse= gson.fromJson(json,ProductoResponse.class);
        // arreglo de objetos
        JsonArray productosResponseData =
                json.getAsJsonObject().getAsJsonArray(productoJsonKeys.RESPONSE_ARRAY_ROOT_PRODUCTS);

       //almaceno los datos en los pojos
        productoResponse.setProductos(deserializarContactodeJson(productosResponseData));

        return productoResponse;
    }
    private ArrayList<Producto> deserializarContactodeJson(JsonArray jsonArray){
        ArrayList<Producto> productos= new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object =jsonArray.get(i).getAsJsonObject();
            int idProducto= object.get(productoJsonKeys.RESPONSE_PRODUCTOS_ID).getAsInt();
            String nombre= object.get(productoJsonKeys.RESPONSE_PRODUCTOS_NOMBRE).getAsString();
            String imagen= object.get(productoJsonKeys.RESPONSE_PRODUCTOS_IMAGEN).getAsString();
            String categoria= object.get(productoJsonKeys.RESPONSE_PRODUCTOS_CATEGORIA).getAsString();
            String marca= object.get(productoJsonKeys.RESPONSE_PRODUCTOS_MARCA).getAsString();


            JsonArray listadeColoresDelProductoActual= object.getAsJsonObject().getAsJsonArray(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES);
            ArrayList<Color> colores = new ArrayList<>();
            for (int j = 0; j < listadeColoresDelProductoActual.size(); j++) {
                JsonObject colorActual= listadeColoresDelProductoActual.get(j).getAsJsonObject();
                int idColor= colorActual.get(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES_ID).getAsInt();
                String color = colorActual.get(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES_COLOR).getAsString();
                String hexadecimal = colorActual.get(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES_HEXA).getAsString();
                double precio = colorActual.get(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES_PRECIO).getAsDouble();
                int stock = colorActual.get(productoJsonKeys.RESPONSE_ARRAY_PRODUCTS_COLORES_STOCK).getAsInt();
                colores.add(new Color(idColor,color,hexadecimal,precio,stock));
            }

            productos.add( new Producto(idProducto,nombre,imagen,categoria,marca,colores));
        }

        return productos;
    }
}
