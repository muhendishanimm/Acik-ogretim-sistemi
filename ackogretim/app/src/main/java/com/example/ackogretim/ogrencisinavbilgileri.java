package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class ogrencisinavbilgileri extends AppCompatActivity {

    private TextView dateTxt;
    private TextView txtTarih;
    private TextView alani;
    private TextView suresi;
    private TextView sinavid;
    private Button sinavbaslat;
    private int i=0;
    private int dogru=0;
    private int yanlis=0;
    private int bos=0;
    private int puan=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrencisinavbilgileri);

        dateTxt = findViewById(R.id.dateTxt);
        txtTarih=findViewById(R.id.textView37);
        alani=findViewById(R.id.textView42);
        suresi=findViewById(R.id.textView45);
        sinavid=findViewById(R.id.textView21);
        sinavbaslat=findViewById(R.id.button25);
        final RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent(); //Intent putextra ile yollanan veriyi almak için
        //alan=intent.getStringExtra("alani");
        final int ogrid = intent.getIntExtra("ogrid",0);
        final int idsinav = intent.getIntExtra("id",0);
        String baslama = intent.getStringExtra("baslama");
        String bitis = intent.getStringExtra("bitis");
        String sure = intent.getStringExtra("sure");
        final String Alan = intent.getStringExtra("alan");
        final String adsoyad = intent.getStringExtra("adsoyad");

        sinavid.setText(Integer.toString(idsinav));
        dateTxt.setText(baslama);
        txtTarih.setText(bitis);
        suresi.setText(sure);
        alani.setText(Alan);

        sinavbaslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.1.29:5001/api/AossOnlineSinavOgrenci/onlinesinavogid/"+ogrid+"/"+idsinav;
                System.out.println(url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dogru = response.getInt("dogruS");
                            yanlis = response.getInt("yanlisS");
                            bos = response.getInt("bosS");
                            puan = response.getInt("puan");

                            if(dogru!=0||yanlis!=0||bos!=0||puan!=0){
                                Toast toast= Toast.makeText(getApplicationContext(),"Bu Sınava Daha Önce Zaten Girdiniz", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                                toast.show();
                            }
                            else {

                                final String url = "http://192.168.1.29:5001/api/AossOgrenci/sorulariGetir/"+ogrid+"/"+idsinav;
                                System.out.println(url);
                                JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                try
                                                {
                                                    JSONObject obj = response.getJSONObject(i);
                                                    int id  = obj.getInt("id");
                                                    int onlineSinavOgrenciId  = obj.getInt("onlineSinavOgrenciId");
                                                    int soruId = obj.getInt("soruId");
                                                    String soru= obj.getString("soru");
                                                    String a= obj.getString("a");
                                                    String b= obj.getString("b");
                                                    String c= obj.getString("c");
                                                    String d= obj.getString("d");
                                                    String e= obj.getString("e");
                                                    String dogruCevap= obj.getString("dogruCevap");
                                                    String isaretlenenCevap= obj.getString("isaretlenenCevap");

                                                    Intent intent = new Intent(getApplicationContext(), ogrenci.class);
                                                    intent.putExtra("ogrid",ogrid);
                                                    intent.putExtra("sinavid",idsinav);
                                                    intent.putExtra("alan",Alan);
                                                    intent.putExtra("adsoyad",adsoyad);
                                                    intent.putExtra("i",i);

                                                    intent.putExtra("id",id); //onlinesınavogrencisorularid
                                                    intent.putExtra("onlineSinavOgrenciId",onlineSinavOgrenciId);
                                                    intent.putExtra("soruId",soruId);
                                                    intent.putExtra("soru",soru);
                                                    intent.putExtra("a",a);
                                                    intent.putExtra("b",b);
                                                    intent.putExtra("c",c);
                                                    intent.putExtra("d",d);
                                                    intent.putExtra("e",e);
                                                    intent.putExtra("dogruCevap",dogruCevap);
                                                    intent.putExtra("isaretlenenCevap",isaretlenenCevap);
                                                    intent.putExtra("kacsoru",response.length());
                                                    startActivity(intent);

                                                }
                                                catch (Exception ex) {
                                                    ex.printStackTrace();

                                                }
                                            }
                                        }
                                        , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }

                                }) {

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        return super.getParams();
                                    }
                                };
                                queue.add(request);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast= Toast.makeText(getApplicationContext(),"Bu Sınava Giriş Yapamazsınız.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                        toast.show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        return super.getParams();
                    }
                };
                queue.add(jsonObjectRequest);

            }
        });



    }
}
