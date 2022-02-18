package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ogrencisonuclar extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView listView;
    OnlineSinavAdapter onlineSinavAdapter;
    ArrayList idd = new ArrayList<>();
    ArrayList baslangıcc = new ArrayList<>();
    ArrayList bitiss = new ArrayList<>();
    ArrayList alanii = new ArrayList<>();
    ArrayList suree = new ArrayList<>();
    public String alani;
    public String adsoyad;
    ArrayList sinavlar = new ArrayList<>();

    private int dogru=0;
    private int yanlis=0;
    private int bos=0;
    private int puan=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrencisonuclar);

        listView = findViewById(R.id.ListeleSinav);
        mQueue= Volley.newRequestQueue(this);
        Intent intent = getIntent();
        final int hocaid = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");
        alani = intent.getStringExtra("alan");
        final int ogrid = intent.getIntExtra("ogrid",0);

        final String url = "http://192.168.1.29:5001/api/AossOnlineSinav/alan?alan="+alani;

        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                int Id = obj.getInt("id");
                                idd.add(Id); //öteki sayafya taşıması içindi
                                String Baslama = obj.getString("baslama");
                                baslangıcc.add(Baslama);
                                String Bitis = obj.getString("bitis");
                                bitiss.add(Bitis);
                                String Sure = obj.getString("sure");
                                suree.add(Sure);
                                String Alan = obj.getString("alan");
                                alanii.add(Alan);
                                sinavlar.add(
                                        new OnlineSinavlar(
                                                Id,
                                                Baslama,
                                                Bitis,
                                                Sure,
                                                Alan
                                        )
                                );
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return super.getParams();
            }
        };

        mQueue.add(request);
        onlineSinavAdapter = new OnlineSinavAdapter(ogrencisonuclar.this,sinavlar);
        listView.setAdapter(onlineSinavAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                String url = "http://192.168.1.29:5001/api/AossOnlineSinavOgrenci/onlinesinavogid/"+ogrid+"/"+idd.get(position);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    dogru = response.getInt("dogruS");
                                    yanlis = response.getInt("yanlisS");
                                    bos = response.getInt("bosS");
                                    puan = response.getInt("puan");

                                    Intent intent = new Intent(getApplicationContext(), ogrencisonuc.class);
                                    intent.putExtra("id", (Integer) idd.get(position));
                                    intent.putExtra("alan", (String) alanii.get(position));
                                    intent.putExtra("hocaid", hocaid);
                                    intent.putExtra("adsoyad",adsoyad);
                                    intent.putExtra("dogru",dogru);
                                    intent.putExtra("yanlis",yanlis);
                                    intent.putExtra("bos",bos);
                                    intent.putExtra("puan",puan);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        return super.getParams();
                    }
                };
                mQueue.add(jsonObjectRequest);


                /*Intent intent = new Intent(getApplicationContext(), ogrencisonuc.class);
                intent.putExtra("id", (Integer) idd.get(position));
                intent.putExtra("baslama", (String) baslangıcc.get(position));
                intent.putExtra("bitis", (String) bitiss.get(position));
                intent.putExtra("sure", (String) suree.get(position));
                intent.putExtra("alan", (String) alanii.get(position));
                intent.putExtra("hocaid", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);*/
            }
        });
    }
}
