package com.example.martinako.panaderiajson;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by Martinako on 10/03/2018.
 */

public class ClienteHttp extends Service {
    private Context context;
    private String result;


    public void ClienteHttp(String link, Context context, String method, String json) {
        this.context = context;
        switch (method) {
            case "GET":
                getJson(link, method);
                break;
            case "POST":
                postJson(link, method, json);
                break;
            case "PUT":
                putJson(link, method, json);
                break;
            case "DELETE":
                deleteJson(link, method);
                break;

        }
    }

    private void getJson(final String link, final String method) {
        AsyncTask<Void, Void, String> getTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return descargarjson(link, method);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ((MainActivity) context).inflarRecycler(s);
            }
        };
        getTask.execute();
    }

    private void postJson(final String link, final String method, final String json) {
        AsyncTask<Void, Void, Void> postTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cargarJson(link, method, json);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        postTask.execute();
    }

    private void putJson(final String link, final String method, final String json){
        AsyncTask<Void, Void, Void> putTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.v("aaaaa", json);
                cargarJson(link, method, json);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        putTask.execute();
    }

    private void deleteJson(final String link, final String method){

        AsyncTask<Void, Void, Void> deleteTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                borrarJson(link, method);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        deleteTask.execute();
    }

    private String descargarjson(String link, String method) {
        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);
            con.connect();
            InputStream reader = con.getInputStream();
            if (reader != null) {
                return leerJson(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String cargarJson(String link, String method, String json) {
        try {

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.connect();
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(json);
            osw.flush();
            osw.close();
            con.getInputStream();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private String leerJson(InputStream reader) {
        String resultado = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(reader, "UTF-8"));
            String datos = "";
            while ((datos = br.readLine()) != null) {
                resultado = resultado + datos;            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private void borrarJson(String link, String method){

        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
