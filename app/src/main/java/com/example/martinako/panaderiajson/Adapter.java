package com.example.martinako.panaderiajson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Martinako on 10/03/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ClienteHttp cliente = new ClienteHttp();
    private ArrayList<Productos> productos;
    private Context context;
    private final int ANIADIR = 1, EDITAR = 2;
    private String urlBase = "https://jsonandroid-markin.c9users.io/";

    public Adapter(ArrayList<Productos> productos, MainActivity context) {
        this.productos = productos;
        this.context = context;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Productos producto = this.productos.get(position);
        holder.bind(producto, context, position);
    }

    @Override
    public int getItemCount() { return productos.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvPrecio;
        private ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(final Productos productos, final Context context, final int position) {
            tvNombre.setText(productos.getNombre());
            tvPrecio.setText(productos.getPrecio());
            //imageView.setImageBitmap(productos.getImagen());
            Picasso.with(context).load(urlBase+"/img/"+productos.getId()+".jpg").into(imageView);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentSecond = new Intent(context, SecondActivity.class);
                    intentSecond.putExtra("productos", productos);
                    intentSecond.putExtra("posicion", position);
                    ((Activity) context).startActivityForResult(intentSecond, EDITAR);
                }
            });

            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eliminarProducto(getAdapterPosition());
                    return false;
                }
            });

        }

        private void eliminarProducto(final int position) {
            Button aceptar, cancelar;

            if (productos.size() != 1) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                final AlertDialog alertDialog;

                alert.setView(li.inflate((R.layout.dialogo), null));
                alert.setTitle("ELIMINAR PRODUCTO");
                alertDialog = alert.create();
                alertDialog.show();
                aceptar = alertDialog.findViewById(R.id.aceptar);
                cancelar = alertDialog.findViewById(R.id.cancelar);
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cliente.ClienteHttp(urlBase + "/productos/" +productos.get(position).getId(), context, "DELETE", null);
                        productos.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), productos.size());
                        alertDialog.cancel();
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        }
    }

}
