package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ogrencigiris extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrencigiris);
    }

    public void giris (View view) {
        final RequestQueue queue = Volley.newRequestQueue(this);

            final TextView sifre = (TextView) findViewById(R.id.editText12);
            final TextView ogrenciNo = (TextView) findViewById(R.id.editText13);

            String url ="http://192.168.1.29:5001/api/AossOgrenci/";
            final String OgrenciNo = ogrenciNo.getText().toString();
            String Sifre = sifre.getText().toString();
            url+=OgrenciNo+"/"+Sifre;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            id= Integer.parseInt(response);
                            System.out.println(id);
                            if(id!=-1) {
                                String strid = Integer.toString(id);
                                String url = "http://192.168.1.29:5001/api/AossOgrenci/"+strid;

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    String alan = response.getString("alani");
                                                    String adsoyad = response.getString("adSoyad");
                                                    int ogrenciNo = response.getInt("ogrenciNo");
                                                    Intent intent = new Intent(ogrencigiris.this, anasayfaogrenci.class);
                                                    intent.putExtra("id", id);
                                                    intent.putExtra("alan",alan);
                                                    intent.putExtra("adsoyad",adsoyad);
                                                    intent.putExtra("ogrenciNo",ogrenciNo);
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

                                Toast toast= Toast.makeText(getApplicationContext(),"Öğrenci No veya Şifreyi Hatalı Girdiniz..!!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                                toast.show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast= Toast.makeText(getApplicationContext(),"Öğrenci No veya Şifre Giriniz..!!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                    toast.show();
                }
            });

            queue.add(stringRequest);

    } public void geri (View view) {
        Intent intent = new Intent(ogrencigiris.this, MainActivity.class);

        startActivity(intent);
    }

}
