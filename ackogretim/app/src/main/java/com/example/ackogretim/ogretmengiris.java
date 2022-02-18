package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ogretmengiris extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView textview;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmengiris);

    }

           public void giris (View view)
            {
                final TextView email = (TextView) findViewById(R.id.editText14);
                final TextView sifre = (TextView) findViewById(R.id.editText15);
                final RequestQueue queue = Volley.newRequestQueue(this);

                String url ="http://192.168.1.29:5001/api/AossHoca/";
                String Email = email.getText().toString();
                String Sifre = sifre.getText().toString();
                url+=Email+"/"+Sifre;


                   // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                 id= Integer.parseInt(response);

                                if(id!=-1) {
                                    String strid = Integer.toString(id);
                                    String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String alan = response.getString("alani");
                                                        String adsoyad = response.getString("adSoyad"); 
                                                        Intent intent = new Intent(ogretmengiris.this, ogretmenanasayfa.class);
                                                        intent.putExtra("id", id);
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

                                    queue.add(jsonObjectRequest);
                                }
                                else if(id==-1) {

                                    Toast toast= Toast.makeText(getApplicationContext(),"Mail veya Şifreyi Hatalı Girdiniz..!! ", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                                    toast.show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast= Toast.makeText(getApplicationContext(),"Mail veya Şifre Giriniz..!! ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                        toast.show();
                    }
                });

                queue.add(stringRequest);

              //  Intent intent = new Intent(ogretmengiris.this, ogretmenanasayfa.class);
              //  startActivity(intent);
            }
            public void geri (View view){
                Intent intent = new Intent(ogretmengiris.this, MainActivity.class);

                startActivity(intent);
            }


        }

