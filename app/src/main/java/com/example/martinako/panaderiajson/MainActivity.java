package com.example.martinako.panaderiajson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Productos> productos = new ArrayList<>();
    private ClienteHttp cliente = new ClienteHttp();
    private final int ANIADIR = 1, EDITAR = 2;
    private Adapter adapter;
    private String json;
    private RecyclerView recycler;
    private String urlBase = "https://jsonandroid-markin.c9users.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cliente.ClienteHttp(urlBase, MainActivity.this, "GET" , null);
    }

    public void jsonToArrayList(JSONArray jsonArray) {
        for (int i = 0; i <= jsonArray.length() ; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Productos producto = new Productos(jsonObject.getInt("id"), jsonObject.getString("price"), jsonObject.getString("prod"), jsonObject.getString("description"));
                //Log.v("aaaaa", producto.getNombre());
                productos.add(producto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void inflarRecycler(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            jsonToArrayList(jsonArray);
            recycler = findViewById(R.id.recyclerView);
            adapter = new Adapter(productos, this);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Productos producto;
            int posicion;
            switch (requestCode) {
                case ANIADIR:
                    posicion = data.getIntExtra("posicion", 0);
                    producto = data.getParcelableExtra("productos");
                    int id = productos.get(productos.size() -1).getId();
                    if (posicion == 0) {
                        posicion = (productos.size()) + 1;
                    }
                    productos.add(new Productos(++id,  producto.getPrecio(), producto.getNombre(), producto.getDescripcion()));
                    adapter.notifyItemInserted(posicion);
                    recycler.scrollToPosition(posicion);
                    adapter.notifyDataSetChanged();
                    json = producto.crearJson();
                    cliente.ClienteHttp(urlBase, MainActivity.this, "POST", json);
                    break;
                case EDITAR:
                    posicion = data.getIntExtra("posicion", 0);
                    producto = data.getParcelableExtra("productos");
                    productos.get(posicion).setNombre(producto.getNombre());
                    productos.get(posicion).setPrecio(producto.getPrecio());
                    productos.get(posicion).setDescripcion(producto.getDescripcion());
                    adapter.notifyDataSetChanged();
                    json = producto.crearJson();
                    cliente.ClienteHttp(urlBase +"/productos/"+ producto.getId(), MainActivity.this, "PUT", json);
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aniadeProducto:
                Intent intentSecond = new Intent(this, SecondActivity.class);
                startActivityForResult(intentSecond, ANIADIR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
