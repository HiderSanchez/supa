package com.sise.titulacion.anypsa.adaptadores;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.entidades.Color;
import com.sise.titulacion.anypsa.entidades.Mensajes;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.utils.Estaticos;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by hider on 24/08/16.
 */
public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalgoViewHolder> {
    Button boton;

    ArrayList<Producto> productos = new ArrayList<>();
    public CatalogoAdapter(ArrayList<Producto> catalogo) {
        this.productos = catalogo;
    }
    @Override
    public CatalgoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_catalogo, parent, false);

        return new CatalgoViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final CatalgoViewHolder catalgoViewHolder, int position) {
        final Producto producto = productos.get(position);

        Context context = catalgoViewHolder.ivFoto.getContext();
        Picasso.with(context).load(producto.getImagen()).into(catalgoViewHolder.ivFoto);


        catalgoViewHolder.tvNombre.setText(producto.getMarca() + " " + producto.getCategoria() + " " + producto.getNombre());
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
                            catalgoViewHolder.colorId = color.getIdColor();
                            catalgoViewHolder.ivColor.setBackgroundColor(android.graphics.Color.parseColor(color.getHexadecimal()));
                            catalgoViewHolder.tvPrecio.setText("Precio: S/. " + color.getPrecio().toString());
                            catalgoViewHolder.tvStock.setText("Stock : " + String.valueOf(color.getStock()));
                            catalgoViewHolder.txtCantidad.setText("1");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }
        );
        catalgoViewHolder.ibComprar.setOnClickListener(new View.OnClickListener() {

            @Subscribe(threadMode = ThreadMode.MAIN)
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(catalgoViewHolder.txtCantidad.getText().toString())) {

                    Producto itemProducto;
                    producto.setCantidad(Integer.parseInt(catalgoViewHolder.txtCantidad.getText().toString()));
                    producto.setColorId(catalgoViewHolder.colorId);
                    itemProducto=producto;

                    for (int i = 0; i < Estaticos.carritoProductos.size(); i++) {
                        int idPrdouctoOld= Estaticos.carritoProductos.get(i).getIdProducto();
                        if (idPrdouctoOld == producto.getIdProducto()) {
                          itemProducto.setCantidad(itemProducto.getCantidad()+Estaticos.carritoProductos.get(i).getCantidad());
                            Snackbar.make(view, "Se agrego " + producto.getCantidad() + " al producto existente", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    if (Estaticos.carritoProductos.contains(producto)) {
                        int index = Estaticos.carritoProductos.indexOf(itemProducto);
                        int cantidadOld = Estaticos.carritoProductos.get(index).getCantidad();
                        Estaticos.carritoProductos.get(index).setCantidad(cantidadOld + producto.getCantidad());
                    } else {
                        Estaticos.carritoProductos.add(itemProducto);
                        Snackbar.make(view, "Producto Agregado"+itemProducto.getIdProducto() , Snackbar.LENGTH_LONG).show();
                    }
                    EventBus.getDefault().post(new Mensajes("Ver mis Compras (" + String.valueOf(Estaticos.carritoProductos.size()) + " Productos )"));

                } else {
                    catalgoViewHolder.txtCantidad.setError("Ingrese la catidad a comprar");
                    catalgoViewHolder.txtCantidad.requestFocus();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class CatalgoViewHolder extends RecyclerView.ViewHolder {
        Spinner spinnerColores;
        ImageView ivColor;
        ImageView ivFoto;
        TextView tvPrecio;
        TextView tvStock;
        TextView tvMedida;
        TextView tvNombre;
        TextInputEditText txtCantidad;
        ImageButton ibComprar;
        Button btnMisCompras;
        private int colorId;

        public CatalgoViewHolder(View v) {

            super(v);

            spinnerColores = (Spinner) v.findViewById(R.id.spinnerColores);
            ivColor = (ImageView) v.findViewById(R.id.ivColor);
            ivFoto = (ImageView) v.findViewById(R.id.ivFoto);
            tvPrecio = (TextView) v.findViewById(R.id.tvPrecio);
            tvStock = (TextView) v.findViewById(R.id.tvStock);
            tvMedida = (TextView) v.findViewById(R.id.tvMedida);
            tvNombre = (TextView) v.findViewById(R.id.tvNombre);
            txtCantidad = (TextInputEditText) v.findViewById(R.id.txtCantidad);
            ibComprar = (ImageButton) v.findViewById(R.id.ibComprar);
            btnMisCompras = (Button) v.findViewById(R.id.btnMisCompras);
        }
    }
}
