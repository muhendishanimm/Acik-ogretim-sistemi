package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ogretmenanasayfa extends AppCompatActivity {
    public String alan;
    public int idd;
    public String adsoyad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmenanasayfa);

        final TextView AdSoyad = findViewById(R.id.textView35);
        Intent intent = getIntent();
        idd = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");
        AdSoyad.setText("Hoşgeldiniz "+adsoyad);

    }

    public void soruekleme (View view)
    {
        Intent intent = new Intent(ogretmenanasayfa.this, soruekleme.class);
        intent.putExtra("id", idd);
        intent.putExtra("adsoyad",adsoyad);
        startActivity(intent);
    }

    public void hatalısoruoylama (View view)
    {
        String strid = Integer.toString(idd);
        String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alan = response.getString("alani");
                            Intent intent = new Intent(ogretmenanasayfa.this, hatalisorularr.class);
                            intent.putExtra("id", idd);
                            intent.putExtra("alani",alan);
                            intent.putExtra("adsoyad",adsoyad);
                            startActivity(intent);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through yor singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void sorulistele (View view)
    {

        String strid = Integer.toString(idd);
        String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alan = response.getString("alani");
                            Intent intent = new Intent(ogretmenanasayfa.this, SoruListeleme.class);
                            intent.putExtra("id", idd);
                            intent.putExtra("alani",alan);
                            intent.putExtra("adsoyad",adsoyad);
                            startActivity(intent);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }
    public void sınavolustur (View view)
    {
        String strid = Integer.toString(idd);
        String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alan = response.getString("alani");
                            Intent intent = new Intent(ogretmenanasayfa.this, sinavolustur.class);
                            intent.putExtra("id", idd);
                            intent.putExtra("alani",alan);
                            intent.putExtra("adsoyad",adsoyad);
                            startActivity(intent);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void cikis (View view)
    {
        Intent intent = new Intent(ogretmenanasayfa.this, ogretmengiris.class);
        startActivity(intent);
    }

    public void sinavlar (View view)
    {
        String strid = Integer.toString(idd);
        String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alan = response.getString("alani");
                            Intent intent = new Intent(ogretmenanasayfa.this, sinavlistesii.class);
                            intent.putExtra("id", idd);
                            intent.putExtra("alani",alan);
                            intent.putExtra("adsoyad",adsoyad);
                            startActivity(intent);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
