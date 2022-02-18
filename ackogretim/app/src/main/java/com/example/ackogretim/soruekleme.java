package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class soruekleme extends AppCompatActivity {
    public Button soruekle;
    public EditText soru, dogrucevap, cevap1, cevap2, cevap3, cevap4;
    public RadioButton kolay,orta,zor;
    public TextView ders;
    public String alan;
    public int hocaid;
    public String adsoyad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soruekleme);

        Intent intent = getIntent();
        hocaid = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");
        String strid = Integer.toString(hocaid);

        final TextView ders = findViewById(R.id.ders);
        soruekle = (Button) findViewById(R.id.kaydet);
        soru = (EditText) findViewById(R.id.editText3);
        dogrucevap = (EditText) findViewById(R.id.editText5);
        cevap1 = (EditText) findViewById(R.id.editText6);
        cevap2 = (EditText) findViewById(R.id.editText7);
        cevap3 = (EditText) findViewById(R.id.editText8);
        cevap4 = (EditText) findViewById(R.id.editText22);
        kolay=(RadioButton) findViewById(R.id.radioButton);
        orta=(RadioButton) findViewById(R.id.radioButton2);
        zor=(RadioButton) findViewById(R.id.radioButton3);


        String url = "http://192.168.1.29:5001/api/AossHoca/"+strid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            alan = response.getString("alani");
                            System.out.println(alan);
                            ders.setText(alan);
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
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void ekle (View view) {
        String url ="http://192.168.1.29:5001/api/AossSorular";
        JSONObject js = new JSONObject();
        try {

            if(soru.getText().toString()!=""&&dogrucevap.getText().toString()!=""&&cevap1.getText().toString()!=""&&cevap2.getText().toString()!=""&&cevap3.getText().toString()!=""&&cevap4.getText().toString()!=""&&kolay.isChecked()==true||orta.isChecked()==true||zor.isChecked()==true)
            {
                    js.put("soru", soru.getText().toString());
                    js.put("dogruCevap", dogrucevap.getText().toString());
                    js.put("cevap1", cevap1.getText().toString());
                    js.put("cevap2", cevap2.getText().toString());
                    js.put("cevap3", cevap3.getText().toString());
                    js.put("cevap4", cevap4.getText().toString());
                    js.put("soruAlani", alan);
                    if (kolay.isChecked()) {
                        js.put("zorluk", "KOLAY");
                    }
                    else if(orta.isChecked())
                    {
                        js.put("zorluk", "ORTA");
                    }
                    else if(zor.isChecked())
                    {
                        js.put("zorluk", "ZOR");
                    }

                    Toast toast= Toast.makeText(getApplicationContext(),"Soru Ekleme İşlemi Başarılı ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                    toast.show();
                Intent intent = new Intent(soruekleme.this, ogretmenanasayfa.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);
            }
            else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen Tüm Bölümleri Doldurunuz..!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 100, 200);
                    toast.show();
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
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
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?A
                        e2.printStackTrace();
                    }
                }
            }
        });


        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }
        public void geri (View view)
        {
            Intent intent = new Intent(soruekleme.this, ogretmenanasayfa.class);

            startActivity(intent);
        }
    }
