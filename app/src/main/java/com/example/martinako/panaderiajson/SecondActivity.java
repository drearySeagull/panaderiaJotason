package com.example.martinako.panaderiajson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Martinako on 08/03/2018.
 */

public class SecondActivity extends AppCompatActivity {

    private Productos producto;
    private String nombre, precio, descripcion;
    private int id, posicion;
    private EditText etNombre, etPrecio, etDescripcion;
    private Button guardar, cancelar;
    private ImageView image;
    private String urlBase = "https://jsonandroid-markin.c9users.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_producto);

        image = findViewById(R.id.imgView);
        etNombre =  findViewById(R.id.nombre);
        etPrecio =  findViewById(R.id.precio);
        etDescripcion = findViewById(R.id.descripcion);
        cancelar = findViewById(R.id.btnCancel);
        guardar = findViewById(R.id.btnSave);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            producto = bundle.getParcelable("productos");
            id = producto.getId();
            nombre = producto.getNombre();
            precio = producto.getPrecio();
            descripcion = producto.getDescripcion();
            posicion = bundle.getInt("posicion");
            Picasso.with(this).load(urlBase+"/img/"+id+".jpg").into(image);

            //meto los datos en los edit text
            etNombre.setText(nombre);
            etPrecio.setText(precio);
            etDescripcion.setText(descripcion + "");
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validacion()) {
                    Intent intentSecond = getIntent();
                    intentSecond.putExtra("posicion", posicion);
                    producto = new Productos(id, etPrecio.getText().toString(), etNombre.getText().toString(), etDescripcion.getText().toString());
                    intentSecond.putExtra("productos", producto);
                    setResult(Activity.RESULT_OK, intentSecond);
                    finish();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSecond = getIntent();
                setResult(Activity.RESULT_CANCELED, intentSecond);
                finish();
            }
        });
    }

    private boolean validacion() {
        Boolean bool = true;
        if (etNombre.length() < 3) {
            etNombre.setError("Debe introducir el nombre del producto.");
            bool = false;
        }
        if (etPrecio.length() < 1) {
            etPrecio.setError("Debe introducir el precio del producto.");
            bool = false;
        }
        if (etDescripcion.length() < 3) {
            etDescripcion.setError("Debe introducir la descripcion del producto.");
            bool = false;
        }
        return bool;
    }

}
