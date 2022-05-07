package com.client.appfp.Actividades;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.client.appfp.Modelo.Datos;
import com.client.appfp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class verificadoActivity extends AppCompatActivity {
    private String auth, token;
    private TextView medio;
    private static final String TAG = "verifActivity";
    private Datos dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificado);

        //Esconder la barra superior de la APP
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Enlazar views
        medio = (TextView) findViewById(R.id.textViewMedio);

        //Obtenemos los datos del bundle de la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dat = (Datos) extras.getParcelable("datos");        //Obtenemos el modelo de la actividad anterior
            auth = dat.getAuth();
        }
        //Esperamos 3 segundos
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(verificadoActivity.this);
                String url = "https://smsretrieverservera-default-rtdb.europe-west1.firebasedatabase.app/numeros.json?auth="+auth;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    token = response.getString("token");
                                    medio.setText("El token recibido es: "+token);
                                    Log.d(TAG, "¡Se recibe el token!");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestQueue requestDelQueue = Volley.newRequestQueue(verificadoActivity.this);
                                JSONObject delData = new JSONObject();
                                String url ="https://smsretrieverservera-default-rtdb.europe-west1.firebasedatabase.app/numeros.json?auth="+auth;
                                try {

                                    delData.put("token", null);
                                    //delData.put("otp", null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Borramos la info en la URL.
                                JsonObjectRequest jsonDelObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, delData, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, "¡Canal de comunicación borrado!");
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "FALLO AL BORRAR CANAL");
                                        error.printStackTrace();
                                    }
                                });
                                requestDelQueue.add(jsonDelObjectRequest);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "FALLO AL RECIBIR TOKEN");
                                medio.setText("FALLO AL RECIBIR TOKEN");

                                error.printStackTrace();
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        }, 3000);


    }
}