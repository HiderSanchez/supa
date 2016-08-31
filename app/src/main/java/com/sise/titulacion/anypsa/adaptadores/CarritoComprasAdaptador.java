package com.sise.titulacion.anypsa.adaptadores;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sise.titulacion.anypsa.R;
import com.sise.titulacion.anypsa.entidades.Color;
import com.sise.titulacion.anypsa.entidades.Producto;
import com.sise.titulacion.anypsa.utils.Estaticos;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Creado por Luis Negrón el 23/08/16.
 * Email lann8605@gmail.com
 */
public class CarritoComprasAdaptador extends RecyclerView.Adapter<CarritoComprasAdaptador.PedidoViewHolder>{

    List<Producto> productos;

    public CarritoComprasAdaptador(List<Producto> productos) {
        this.productos = productos;
    }



    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carrito_compras,parent,false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PedidoViewHolder pedidoViewHolder, final int position) {

        //recorre y setea cada elemento
        final Producto producto = productos.get(position);

        pedidoViewHolder.txtNombre.setText(producto.getMarca()+" "+ producto.getCategoria()+" "+producto.getNombre());
        //carga imagen al imageView

        Context context = pedidoViewHolder.imagenProducto.getContext();
        Picasso.with(context).load(producto.getImagen()).into(pedidoViewHolder.imagenProducto);
        //termina carga de imagen

        Color color = new Color();
        for (int i = 0; i < producto.getColores().size(); i++) {
            color.setStock(producto.getColores().get(i).getStock());
            color.setIdColor(producto.getColores().get(i).getIdColor());
            color.setColor(producto.getColores().get(i).getColor());
            color.setPrecio(producto.getColores().get(i).getPrecio());
            color.setHexadecimal(producto.getColores().get(i).getHexadecimal());

        }
        pedidoViewHolder.txtPrecio.setText("Precio : "+color.getPrecio().toString());
        pedidoViewHolder.txtCantidad.setText("Cantidad : "+String.valueOf( producto.getCantidad()));
      //  pedidoViewHolder.txtStock.setText("Stock : "+String.valueOf(color.getStock()));
        pedidoViewHolder.txtColor.setBackgroundColor(android.graphics.Color.parseColor(color.getHexadecimal()));

        //  ELIMINAR UN PRODUCTO DEL CARRITO
        pedidoViewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estaticos.carritoProductos.remove(producto);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,Estaticos.carritoProductos.size());
                Snackbar.make(v,"Producto Eliminado",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder{

        //elementos que se necesita para llenar los datos en la card
        private TextView txtNombre;
        private ImageView imagenProducto;
        private TextView txtMarca;
        private TextView txtCategoria;
        private TextView txtPrecio;
        private ImageView txtColor;
        private TextView txtStock;
        private Spinner cmbColor;
        private TextView txtCantidad;
        private ImageButton btnComprar;
        private ImageButton btnEliminar;



        public PedidoViewHolder(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            imagenProducto = (ImageView) itemView.findViewById(R.id.ivFoto);
         //   txtMarca = (TextView) itemView.findViewById(R.id.tvm);
      //      txtCategoria = (TextView) itemView.findViewById(R.id.tv);
            txtPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            txtColor = (ImageView) itemView.findViewById(R.id.ivColor);
       //     txtStock = (TextView) itemView.findViewById(R.id.tvStock);
            txtCantidad = (TextView) itemView.findViewById(R.id.tvcatidad);
        //    btnComprar = (ImageButton) itemView.findViewById(R.id.btnComprar);
            btnEliminar = (ImageButton) itemView.findViewById(R.id.ibEliminar);
        //    cmbColor = (Spinner) itemView.findViewById(R.id.spinnerColores);

        }
    }
}
