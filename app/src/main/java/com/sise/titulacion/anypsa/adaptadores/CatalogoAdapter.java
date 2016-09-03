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
import com.sise.titulacion.anypsa.entidades.Pedido;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.utils.Estaticos;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

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
    public void onBindViewHolder(final CatalgoViewHolder catalgoViewHolder, final int position) {
        final Producto producto = productos.get(position);

        final Context context = catalgoViewHolder.ivFoto.getContext();
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
                            catalgoViewHolder.tvPrecio.setText(color.getPrecio().toString());
                            catalgoViewHolder.tvStock.setText(String.valueOf(color.getStock()));
                            // catalgoViewHolder.txtCantidad.setText("1");  todo modificacion
                            producto.setColorId(color.getIdColor());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }
        );
        catalgoViewHolder.ibComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(catalgoViewHolder.txtCantidad.getText().toString())) {
                    if (Integer.parseInt(catalgoViewHolder.txtCantidad.getText().toString()) > 0) {

                        if (Integer.parseInt(catalgoViewHolder.txtCantidad.getText().toString()) <=
                                Integer.parseInt(catalgoViewHolder.tvStock.getText().toString())) {
                            boolean encontro = false;
                            for (int x = 0; x < Estaticos.carritoProductos.size(); x++) {
                                Producto productoExistente = Estaticos.carritoProductos.get(x);
                                Pedido pedido = new Pedido();
                                if (productoExistente.getIdProducto() == producto.getIdProducto() && productoExistente.getColorId() == producto.getColorId()) {

                                    productoExistente.setCantidad(productoExistente.getCantidad() + Integer.parseInt(catalgoViewHolder.txtCantidad.getText().toString()));
                                    encontro = true;
                                    Estaticos.carritoProductos.set(x, productoExistente);
                                }
                            }
                            if (encontro) {
                                Snackbar.make(view, "Se agrego " + catalgoViewHolder.txtCantidad.getText().toString() + " al producto existente", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Producto itemProducto;
                                producto.setCantidad(Integer.parseInt(catalgoViewHolder.txtCantidad.getText().toString()));
                                producto.setColorId(catalgoViewHolder.colorId);
                                itemProducto = producto;
                                Estaticos.carritoProductos.add(itemProducto);
                                Snackbar.make(view, "Producto agregado", Snackbar.LENGTH_SHORT).show();

                            }
                            EventBus.getDefault().post(new Mensajes("Ir a mis Compras (" + String.valueOf(Estaticos.carritoProductos.size()) + " Productos )"));

                        } else {
                            catalgoViewHolder.txtCantidad.setError("Cantidad supera el stock");
                            catalgoViewHolder.txtCantidad.setSelectAllOnFocus(true);
                            catalgoViewHolder.txtCantidad.requestFocus();
                        }
                    } else {
                        catalgoViewHolder.txtCantidad.setError("Ingrese una cantidad mayor a 0");
                        catalgoViewHolder.txtCantidad.setSelectAllOnFocus(true);
                        catalgoViewHolder.txtCantidad.requestFocus();
                    }
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
