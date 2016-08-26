package com.sise.titulacion.anypsa.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sise.titulacion.anypsa.entidades.Color;
import com.sise.titulacion.anypsa.entidades.DetallePedido;
import com.sise.titulacion.anypsa.entidades.Pedido;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.utils.Constantes;
import com.sise.titulacion.anypsa.utils.Estaticos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Se remplazo esta clase por la clase CatalogoAdapter
 */
@Deprecated
public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoViewHolder>{

    private List<Producto> productos;

    public ProductoAdaptador(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //infla el layout donde esta la cardView
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_catalogo,parent,false);
        return new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductoViewHolder productoViewHolder, int position) {



        //recorre y setea cada elemento
        final Producto producto = productos.get(position);
        productoViewHolder.txtNombre.setText(producto.getNombre());
        //carga imagen al imageView
        Uri uri = Uri.parse(producto.getImagen());
        final Context context = productoViewHolder.imagenProducto.getContext();
        Picasso.with(context).load(producto.getImagen()).into(productoViewHolder.imagenProducto);
        //termina carga de imagen
        productoViewHolder.txtMarca.setText(producto.getMarca());
        productoViewHolder.txtCategoria.setText(producto.getCategoria());

        final List<Color>  colores = producto.getColores();
        LinkedList modelo = new LinkedList();
        for (int i = 0; i < colores.size(); i++) {
            modelo.add(i,producto.getColores().get(i).getColor());
        }

        ArrayAdapter adapter = new ArrayAdapter(productoViewHolder.cmbColor.getContext(),R.layout.support_simple_spinner_dropdown_item,modelo);
        productoViewHolder.cmbColor.setAdapter(adapter);
        productoViewHolder.cmbColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.spinnerColores) {
                    Color color = colores.get(position);
                    productoViewHolder.colorId = color.getIdColor();
                    //para usar el contenido en la compra
                    productoViewHolder.precio = color.getPrecio();
                    productoViewHolder.txtPrecio.setText(String.valueOf(productoViewHolder.precio));

                    productoViewHolder.txtStock.setText(String.valueOf(color.getStock()));
                    productoViewHolder.txtColor.setText("                ");
                    productoViewHolder.txtColor.setBackgroundColor(android.graphics.Color.parseColor(color.getHexadecimal()));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        productoViewHolder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Producto itemProducto = new Producto();
                producto.setCantidad(Integer.parseInt(productoViewHolder.txtCantidad.getText().toString()));
                Color color = new Color();
                ArrayList<Color> colors= new ArrayList<Color>();
                color.setColor(productoViewHolder.cmbColor.getChildAt(0).toString());
                color.setHexadecimal(productoViewHolder.txtColor.getBackground().toString());
                color.setIdColor(productoViewHolder.colorId);
                color.setPrecio(Double.parseDouble(productoViewHolder.txtPrecio.getText().toString()));
                color.setStock(Integer.parseInt(productoViewHolder.txtStock.getText().toString()));
                colors.add(color);
                producto.setColores(colors);

                itemProducto = producto;

                Estaticos.carritoProductos.add(itemProducto);

                    /*DetallePedido item = new DetallePedido();
                    item.setIdProducto(producto.getIdProducto());
                    item.setIdcolor(productoViewHolder.colorId);
                    item.setCantidad(Integer.parseInt(productoViewHolder.txtCantidad.getText().toString()));
                    item.setPrecio(productoViewHolder.precio);
                    Estaticos.contador = Estaticos.contador + 1;
                    item.setItem(Estaticos.contador);
                    Estaticos.listaPedidos.add(item);*/
                    Toast.makeText(v.getContext(),Estaticos.carritoProductos.size()+" cargados ",Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        //devuelve el total de productos
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder{

        //elementos que se necesita para llenar los datos en la card
        private TextView txtNombre;
        private ImageView imagenProducto;
        private TextView txtMarca;
        private TextView txtCategoria;
        private TextView txtPrecio;
        private TextView txtColor;
        private TextView txtStock;
        private Spinner cmbColor;
        private EditText txtCantidad;
        private ImageButton btnComprar;
        //valores para el detalle
        private int colorId;
        private double precio;



        public ProductoViewHolder(View itemView) {
            super(itemView);

            txtNombre = (TextView) itemView.findViewById(R.id.txtNombre);
            imagenProducto = (ImageView) itemView.findViewById(R.id.imagenProducto);
            txtMarca = (TextView) itemView.findViewById(R.id.txtMarca);
            txtCategoria = (TextView) itemView.findViewById(R.id.txtCategoria);
            txtPrecio = (TextView) itemView.findViewById(R.id.txtPrecio);
            txtColor = (TextView)itemView.findViewById(R.id.txtColor);
            txtStock = (TextView) itemView.findViewById(R.id.txtStock);
            txtCantidad = (EditText) itemView.findViewById(R.id.txtCantidad);
            btnComprar = (ImageButton) itemView.findViewById(R.id.btnComprar);
           cmbColor = (Spinner) itemView.findViewById(R.id.spinnerColores);
        }
    }
}
