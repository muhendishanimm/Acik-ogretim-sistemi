package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class soruislemler extends AppCompatActivity {

    public String adsoyad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soruislemler);
        Button guncelle=findViewById(R.id.guncelle);
        Button sil=findViewById(R.id.ekleyelim);
        final Button hatalibtn=findViewById(R.id.hataliBtn);
        final TextView idedit = findViewById(R.id.textView34);
        final TextView soruedit = findViewById(R.id.editText3);
        final TextView dogrucevapedit = findViewById(R.id.editText5);
        final TextView cevap1edit = findViewById(R.id.editText6);
        final TextView cevap2edit = findViewById(R.id.editText7);
        final TextView cevap3edit = findViewById(R.id.editText8);
        final TextView cevap4edit = findViewById(R.id.editText23);
        final TextView soruAlaniedit = findViewById(R.id.ders);
        RadioGroup rg = findViewById(R.id.radioGroup);
        final RadioButton kolayedit = findViewById(R.id.radioButton);
        final RadioButton ortaedit = findViewById(R.id.radioButton2);
        final RadioButton zoredit = findViewById(R.id.radioButton3);

        final RequestQueue queue = Volley.newRequestQueue(this);

        idedit.setVisibility(View.INVISIBLE);

        Intent intent = getIntent(); //Intent putextra ile yollanan veriyi almak için
        final int id = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");

        String soru = intent.getStringExtra("soru");
        String dogruCevap = intent.getStringExtra("dogrucevap");
        String cevap1 = intent.getStringExtra("cevap1");
        String cevap2 = intent.getStringExtra("cevap2");
        String cevap3 = intent.getStringExtra("cevap3");
        String cevap4 = intent.getStringExtra("cevap4");
        String soruAlani = intent.getStringExtra("soruAlani");
        String zorluk = intent.getStringExtra("zorluk");
        final int hocaid = intent.getIntExtra("hocaid",1);
        idedit.setText(Integer.toString(id));
        soruedit.setText(soru);
        dogrucevapedit.setText(dogruCevap);
        cevap1edit.setText(cevap1);
        cevap2edit.setText(cevap2);
        cevap3edit.setText(cevap3);
        cevap4edit.setText(cevap4);
        soruAlaniedit.setText(soruAlani);


        if(zorluk.contains("KOLAY")) {
            kolayedit.setChecked(true);
        }
        else if(zorluk.contains("ORTA")){
            ortaedit.setChecked(true);
        }
        else if(zorluk.contains("ZOR")){
            zoredit.setChecked(true);
        }

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soruedit.getText().length()>0 && dogrucevapedit.getText().length()>0
                        && cevap1edit.getText().length()>0 && cevap2edit.getText().length()>0
                        && cevap3edit.getText().length()>0  && cevap4edit.getText().length()>0
                        && soruAlaniedit.getText().length()>0){

                    executePutMethod();
                    Toast.makeText(getApplicationContext(),"Soru Güncellendi.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(soruislemler.this, ogretmenanasayfa.class);
                    intent.putExtra("id", hocaid);
                    intent.putExtra("adsoyad",adsoyad);
                    startActivity(intent);
                }else{

                    Toast.makeText(getApplicationContext(),"Lütfen Gerekli Alanları Doldurunuz!",Toast.LENGTH_SHORT).show();
                }
            }
            private void executePutMethod()
            {
                String url ="http://192.168.1.29:5001/api/AossSorular/";
                url+=id;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", id);
                    jsonObject.put("soru", soruedit.getText().toString());
                    jsonObject.put("dogruCevap", dogrucevapedit.getText().toString());
                    jsonObject.put("cevap1", cevap1edit.getText().toString());
                    jsonObject.put("cevap2", cevap2edit.getText().toString());
                    jsonObject.put("cevap3", cevap3edit.getText().toString());
                    jsonObject.put("cevap4", cevap4edit.getText().toString());
                    jsonObject.put("soruAlani", soruAlaniedit.getText().toString());
                    if (kolayedit.isChecked()) {
                        jsonObject.put("zorluk", "KOLAY");
                    }
                    else if(ortaedit.isChecked())
                    {
                        jsonObject.put("zorluk", "ORTA");
                    }
                    else if(zoredit.isChecked())
                    {
                        jsonObject.put("zorluk", "ZOR");
                    }
                } catch (JSONException e) {
                    // handle exception
                }
                JsonObjectRequest jsonForPutRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject obj = response
                                            .getJSONObject("some_json_obj");

                                    Log.w("myApp",
                                            "status code..." + obj.getString("name"));

                                    // VolleyLog.v("Response:%n %s", response.toString(4));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        return param;
                    }
                };
                queue.add(jsonForPutRequest);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idedit.getText().length() > 0) {
                    deleteRequest();
                    Toast.makeText(getApplicationContext(),"Soru Silindi.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(soruislemler.this, ogretmenanasayfa.class);
                    intent.putExtra("id", hocaid);
                    intent.putExtra("adsoyad",adsoyad);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen Silmek İstediğiniz Sorunun ID sini giriniz!", Toast.LENGTH_SHORT).show();
                }

            }
            private void deleteRequest() {
                String url = "http://192.168.1.29:5001/api/AossSorular/";
                url += id;

                StringRequest jsonForPutRequest = new StringRequest(
                        Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("log", response.toString());
                        try {
                            JSONObject json = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), "" + json.getString("mesaj"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();

                        return param;
                    }

                };
                queue.add(jsonForPutRequest);
            }

        });
        hatalibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url ="http://192.168.1.29:5001/api/AossSorular/hataliSoru/"+id+"/"+hocaid;
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);
                Toast.makeText(getApplicationContext(),"Hatalı Soru Olarak Bildirildi.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(soruislemler.this, ogretmenanasayfa.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);
            }
        });

    }
    public void geri(View view) {
        Intent intent = new Intent(soruislemler.this, SoruListeleme.class);
        startActivity(intent);
    }
}