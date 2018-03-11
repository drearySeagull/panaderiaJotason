package com.example.martinako.panaderiajson;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by Martinako on 08/03/2018.
 */

public class Productos implements Parcelable{
    int id;
    String precio;
    String nombre, descripcion;
    Bitmap imagen;


    public  Productos(int id, String precio, String producto, String desc){
        this.id = id;
        this.precio = precio;
        this.nombre = producto;
        this.descripcion = desc;
    }

    protected Productos(Parcel parcel){
        this.id = parcel.readInt();
        this.precio = parcel.readString();
        this.nombre = parcel.readString();
        this.descripcion = parcel.readString();
        this.imagen = parcel.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "Productos{" +
                "id=" + id +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public String crearJson(){
        String json = "{\n" +
                "\"id\":" + id + ",\n" +
                "\"nombre\":\"" + nombre + "\",\n" +
                "\"precio\":\"" + precio + "\",\n" +
                "\"descripcion\":" + descripcion + "\n" +
                "}";
        return json;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(precio);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeValue(imagen);
    }

    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };


    //Setter Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String product) {
        this.nombre = product;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }






}
