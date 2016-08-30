package com.sise.titulacion.anypsa.adaptadores;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.entidades.Color;
import com.sise.titulacion.anypsa.entidades.Producto;

import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Created by hider on 24/08/16.
 */
public class CarritoComprasAdapter extends RecyclerView.Adapter<CarritoComprasAdapter.CarritoViewHolder> {
    ArrayList<Producto> productos= new ArrayList<>();

    public CarritoComprasAdapter(ArrayList<Producto> catalogo) {
        this.productos = catalogo;
    }

    //infla el layout y lo pasa al viewholder
    @Override
    public CarritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carrito_compras, parent, false);
        return new CarritoViewHolder(v);
    }
    //asocia cada dato  acada view
    @Override
    public void onBindViewHolder(final CarritoViewHolder catalgoViewHolder, int position) {
        Producto producto = productos.get(position);
      //  catalgoViewHolder.ivFoto.setImageResource(producto.getImagen());
        catalgoViewHolder.tvNombre.setText(producto.getNombre());
        catalgoViewHolder.txtCantidad.setText(String.valueOf(producto.getCantidad()));
       final ArrayList<Color> colors = new ArrayList<>();
        for (int i = 0; i < producto.getColores().size(); i++) {
            colors.add(producto.getColores().get(i));
        }
        LinkedList modeloSpinner = new LinkedList();
        for (int i = 0; i < colors.size(); i++) {
            modeloSpinner.add(i, colors.get(i).getColor());
        }
        ArrayAdapter adaptadorSpnner = new ArrayAdapter(
                catalgoViewHolder.spinnerColores.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                modeloSpinner);
        catalgoViewHolder.spinnerColores.setAdapter(adaptadorSpnner);
        catalgoViewHolder.spinnerColores.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView.getId() == R.id.spinnerColores) {
                            Color color = colors.get(i);
                     //      catalgoViewHolder.spinnerColores.setBackgroundColor(android.graphics.Color.parseColor(color.getHexadecimal()));

                           catalgoViewHolder.ivColor.setBackgroundColor(android.graphics.Color.parseColor(color.getHexadecimal()));
                            catalgoViewHolder.tvPrecio.setText("Precio: S/. "+color.getPrecio().toString());
                            catalgoViewHolder.tvStock.setText("Stock: "+ String.valueOf(color.getStock()));
                            catalgoViewHolder.tvMedida.setText(String.valueOf("5 Galones"));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                }
        );
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        Spinner spinnerColores;
        ImageView ivColor;
        ImageView ivFoto;
        TextView tvPrecio;
        TextView tvStock;
        TextView tvMedida;
        TextView tvNombre;
        TextView tvcatidad;
        TextView txtCantidad;
        ImageButton ibComprar;


        public CarritoViewHolder(View v) {
            super(v);
            spinnerColores   = (Spinner) v.findViewById(R.id.spinnerColores);
            ivColor          = (ImageView) v.findViewById(R.id.ivColor);
            ivFoto           = (ImageView) v.findViewById(R.id.ivFoto);
            tvPrecio         = (TextView) v.findViewById(R.id.tvPrecio);
            tvStock          = (TextView) v.findViewById(R.id.tvStock);
            tvMedida         = (TextView) v.findViewById(R.id.tvMedida);
            tvNombre         = (TextView) v.findViewById(R.id.tvNombre);
            tvcatidad        = (TextView) v.findViewById(R.id.tvcatidad);
            txtCantidad      = (TextView) v.findViewById(R.id.tvcatidad);
            ibComprar        = (ImageButton) v.findViewById(R.id.ibComprar);
        }
    }
}
