package com.sise.titulacion.anypsa.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sise.titulacion.anypsa.controladores.HistorialResponse;
import com.sise.titulacion.anypsa.entidades.Historial;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hider on 25/08/16.
 */
public class DeserializadorHistorial implements JsonDeserializer<HistorialResponse> {


    @Override
    public HistorialResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Gson gson = new Gson();
        HistorialResponse historialResponse = gson.fromJson(jsonElement, HistorialResponse.class);

        JsonArray historialResponseData = jsonElement.getAsJsonObject().getAsJsonArray("pedidos");

        historialResponse.setHistorials(descerializarHistorial(historialResponseData));

        return historialResponse;
    }

    private ArrayList<Historial> descerializarHistorial(JsonArray jsonArray) {

        ArrayList<Historial> historiales = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            int idpedido = object.get("idPedido").getAsInt();
            String fechapedido = object.get("fechaPedido").getAsString();
            String fechaentrega = object.get("fechaEntrega").getAsString();
            double subtotal = object.get("subtotal").getAsDouble();
            double igv = object.get("igv").getAsDouble();
            int pagado = object.get("pagado").getAsInt();
            String estado = object.get("estado").getAsString();
            historiales.add(new Historial(idpedido, fechapedido, fechaentrega, subtotal, igv, pagado, estado));
        }
        return historiales;
    }
}
