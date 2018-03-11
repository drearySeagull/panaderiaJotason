package com.example.martinako.panaderiajson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Martinako on 10/03/2018.
 */

public class CargaImagenes extends AsyncTask<String, Void, Bitmap> {

    private String urlBase = "https://androidjson-markin.c9users.io/img";
    private int idImage;
    private Productos producto;

    public CargaImagenes(int id, Productos producto){
        this.idImage = id;
        this.producto = producto;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        Log.i("doInBackground" , "Entra en doInBackground");
        return descargarImagen();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        producto.setImagen(result);
    }

    private Bitmap descargarImagen (){
        Bitmap myBitmap = null;
        try{
            URL url = new URL(urlBase + "/img/" + this.idImage + ".jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myBitmap;
    }

}
