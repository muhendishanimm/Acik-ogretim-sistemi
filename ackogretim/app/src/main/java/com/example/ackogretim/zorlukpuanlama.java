package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class zorlukpuanlama extends AppCompatActivity {

    private Button sorusec;
    private Button sinavonayla;
    private  Button eklenensorular;
    private EditText kolay;
    private EditText orta;
    private EditText zor;
    private String alan;
    private String adsoyad;
    private Button onayla;
    private int kolayy;
    private int ortaa;
    private int zorr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zorlukpuanlama);
        eklenensorular=findViewById(R.id.button30);
        sorusec = findViewById(R.id.button28);
        kolay=findViewById(R.id.editText20);
        orta=findViewById(R.id.editText21);
        zor=findViewById(R.id.editText25);
        onayla=findViewById(R.id.button29);
        sinavonayla=findViewById(R.id.button20);
        final RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        final int hocaid = intent.getIntExtra("hocaid",1);
        adsoyad = intent.getStringExtra("adsoyad");
        final int sinavid = intent.getIntExtra("sinavid",1);
        alan = intent.getStringExtra("alan");
        kolayy=intent.getIntExtra("kolayPuan",0);
        ortaa=intent.getIntExtra("ortaPuan",0);
        zorr=intent.getIntExtra("zorPuan",0);
        System.out.println(alan);
        System.out.println(sinavid);
        kolay.setText(Integer.toString(kolayy));
        orta.setText(Integer.toString(ortaa));
        zor.setText(Integer.toString(zorr));

        if (kolayy != 0 && ortaa!=0 && zorr!=0) {
            onayla.setVisibility(View.INVISIBLE);
        }



        eklenensorular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zorlukpuanlama.this, eklenensorularlistesi.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                intent.putExtra("sinavid",sinavid);
                intent.putExtra("alan",alan);
                intent.putExtra("kolayPuan",kolayy);
                intent.putExtra("ortaPuan",ortaa);
                intent.putExtra("zorPuan",zorr);
                System.out.println(alan);
                startActivity(intent);

            }
        });


        onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (Integer.parseInt(kolay.getText().toString()) != 0 && Integer.parseInt(orta.getText().toString()) != 0 && Integer.parseInt(zor.getText().toString()) != 0)
                    {
                        String url ="http://192.168.1.29:5001/api/AossZorlukPuanlama";
                        JSONObject js = new JSONObject();
                        try {
                        kolayy = Integer.parseInt(kolay.getText().toString());
                        ortaa = Integer.parseInt(orta.getText().toString());
                        zorr = Integer.parseInt(zor.getText().toString());
                        js.put("kolayPuan", kolayy);
                        js.put("ortaPuan", ortaa);
                        js.put("zorPuan", zorr);
                        js.put("onlineSinavId", sinavid);
                        System.out.println(sinavid);

                        Toast toast= Toast.makeText(getApplicationContext(),"Zorluk Puanlama İşlemi Başarılı ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                        toast.show();
                        Intent intent = new Intent(zorlukpuanlama.this, zorlukpuanlama.class);
                        intent.putExtra("id", hocaid);
                        intent.putExtra("adsoyad",adsoyad);
                        intent.putExtra("sinavid",sinavid);
                        intent.putExtra("alan",alan);
                        intent.putExtra("kolayPuan",kolayy);
                        intent.putExtra("ortaPuan",ortaa);
                        intent.putExtra("zorPuan",zorr);
                        System.out.println(alan);
                        startActivity(intent);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();

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
                        });

                        queue.add(jsonObjReq);
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Lütfen Tüm Bölümleri Geçerli Bir Şekilde Doldurunuz..!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 100, 200);
                        toast.show();
                    }

            }
        });

        sorusec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kolayy == 0 && ortaa==0 && zorr==0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen Zorluk Derecelerini Puanlayınız..!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 100, 200);
                    toast.show();
                }
                else {
                    Intent intent = new Intent(zorlukpuanlama.this, sinavsorulistesi.class);
                    intent.putExtra("id", hocaid);
                    intent.putExtra("adsoyad",adsoyad);
                    intent.putExtra("sinavid",sinavid);
                    intent.putExtra("alan",alan);
                    System.out.println(alan);
                    startActivity(intent);
                }

            }
        });

        sinavonayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(1==1)
                {
                    String url = "http://192.168.1.29:5001/api/AossOnlineSinavSorular/KaydiBitir/ilkasama/"+sinavid;
                    System.out.println(url);

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
                }

                String url = "http://192.168.1.29:5001/api/AossOnlineSinavSorular/KaydiBitir/"+sinavid;
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

                Toast toast = Toast.makeText(getApplicationContext(), "Sınav Onaylandı.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 100, 100);
                toast.show();
                Intent intent = new Intent(zorlukpuanlama.this, ogretmenanasayfa.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);
            }

        });

    }

}
