package com.example.ackogretim;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

public class sinavasoruekle extends AppCompatActivity {

    public String adsoyad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinavasoruekle);
        Button sinavaekle=findViewById(R.id.ekleyelim);
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
        final int soruid = intent.getIntExtra("id",1);
        final int sinavid = intent.getIntExtra("sinavid",1);
        adsoyad = intent.getStringExtra("adsoyad");
        System.out.println(sinavid);
        System.out.println(soruid);
        String soru = intent.getStringExtra("soru");
        String dogruCevap = intent.getStringExtra("dogrucevap");
        String cevap1 = intent.getStringExtra("cevap1");
        String cevap2 = intent.getStringExtra("cevap2");
        String cevap3 = intent.getStringExtra("cevap3");
        String cevap4 = intent.getStringExtra("cevap4");
        String soruAlani = intent.getStringExtra("soruAlani");
        String zorluk = intent.getStringExtra("zorluk");
        final int hocaid = intent.getIntExtra("hocaid",1);
        idedit.setText(Integer.toString(soruid));
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

        sinavaekle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url ="http://192.168.1.29:5001/api/AossOnlineSinavSorular";
                JSONObject js = new JSONObject();
                try
                {
                    js.put("onlineSinavId", sinavid);
                    js.put("soruId", soruid);

                    if (js.getInt("onlineSinavId")!=sinavid){
                        Toast toast= Toast.makeText(getApplicationContext(),"Sorunuz Eklenmedi.Puanlamayı veya Havuzdaki Horu Sayısını Kontrol Edin.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 100);
                        toast.show();
                    }else{
                        Toast toast= Toast.makeText(getApplicationContext(),"Sorunuz Eklendi..", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 100);
                        toast.show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast toast= Toast.makeText(getApplicationContext(),"Ekleyemediniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 100);
                    toast.show();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, url, js,
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
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (Exception e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                });

                queue.add(jsonObjReq);


            }
        });

    }
    public void geri(View view) {
        Intent intent = new Intent(sinavasoruekle.this, SoruListeleme.class);
        startActivity(intent);
    }
}