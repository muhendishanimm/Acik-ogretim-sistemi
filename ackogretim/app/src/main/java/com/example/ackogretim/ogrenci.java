package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ogrenci extends AppCompatActivity {

    private EditText Soru,A,B,C,D,E;
    private Button ileri,geri,sinavibitir;
    private TextView onlinesinavsorularId;
    private RadioButton RadioA,RadioB,RadioC,RadioD,RadioE;
    private int i,kacsoru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci);
        Soru=findViewById(R.id.editText);
        A=findViewById(R.id.editText2);
        B=findViewById(R.id.editText4);
        C=findViewById(R.id.editText10);
        D=findViewById(R.id.editText11);
        E=findViewById(R.id.editText26);
        ileri=findViewById(R.id.button19);
        geri=findViewById(R.id.button3);
        sinavibitir=findViewById(R.id.button4);
        onlinesinavsorularId=findViewById(R.id.textView10);
        RadioA=findViewById(R.id.radioButton6);
        RadioB=findViewById(R.id.radioButton7);
        RadioC=findViewById(R.id.radioButton8);
        RadioD=findViewById(R.id.radioButton9);
        RadioE=findViewById(R.id.radioButton10);
        final RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        final String Alan = intent.getStringExtra("alan");
        final int ogrid = intent.getIntExtra("ogrid",0);
        final int idsinav = intent.getIntExtra("sinavid",0);
        final int id = intent.getIntExtra("id",0);
        final int onlineSinavOgrenciId = intent.getIntExtra("onlineSinavOgrenciId",0);
        final int soruId = intent.getIntExtra("soruId",0);
        final String adsoyad = intent.getStringExtra("adsoyad");
        String soru = intent.getStringExtra("soru");
        String a = intent.getStringExtra("a");
        String b = intent.getStringExtra("b");
        String c= intent.getStringExtra("c");
        String d = intent.getStringExtra("d");
        String e = intent.getStringExtra("e");
        String dogruCevap = intent.getStringExtra("dogruCevap");
        String isaretlenenCevap = intent.getStringExtra("isaretlenenCevap");
        kacsoru=intent.getIntExtra("kacsoru",-1);
        i = intent.getIntExtra("i",0);
        System.out.println(i);
        System.out.println(kacsoru-1);

        Soru.setKeyListener(null);
        A.setKeyListener(null);
        B.setKeyListener(null);
        C.setKeyListener(null);
        D.setKeyListener(null);
        E.setKeyListener(null);

        onlinesinavsorularId.setText(Integer.toString(i+1));
        Soru.setText(soru);
        A.setText(a);
        B.setText(b);
        C.setText(c);
        D.setText(d);
        E.setText(e);

        if(i==0){
            geri.setVisibility(View.INVISIBLE);
        }
        if(i==kacsoru-1){
            ileri.setVisibility(View.INVISIBLE);
        }

        if(isaretlenenCevap.contains("A")){
            RadioA.setChecked(true);
        }
        if(isaretlenenCevap.contains("B")){
            RadioB.setChecked(true);
        }
        if(isaretlenenCevap.contains("C")){
            RadioC.setChecked(true);
        }
        if(isaretlenenCevap.contains("D")){
            RadioD.setChecked(true);
        }
        if(isaretlenenCevap.contains("E")){
            RadioE.setChecked(true);
        }


    ileri.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(RadioA.isChecked()==true || RadioB.isChecked()==true || RadioC.isChecked()==true|| RadioD.isChecked()==true|| RadioE.isChecked()==true)
        {
            String url ="http://192.168.1.29:5001/Sinav/Cevap/"+id+"/";
            JSONObject jsonObject = new JSONObject();
            try {
                if (RadioA.isChecked()) {
                    url+="A";
                }
                else if(RadioB.isChecked())
                {
                    url+="B";
                }
                else if(RadioC.isChecked())
                {
                    url+="C";
                }
                else if(RadioD.isChecked())
                {
                    url+="D";
                }
                else if(RadioE.isChecked())
                {
                    url+="E";
                }
            } catch (Exception e) {

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


        final String url = "http://192.168.1.29:5001/api/AossOgrenci/sorulariGetir/"+idsinav+"/"+ogrid;
        System.out.println(url);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try
                        {
                            i += 1;
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
                            intent.putExtra("kacsoru",kacsoru);
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
   });

   geri.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           if(RadioA.isChecked()==true || RadioB.isChecked()==true || RadioC.isChecked()==true|| RadioD.isChecked()==true|| RadioE.isChecked()==true)
           {
               String url ="http://192.168.1.29:5001/Sinav/Cevap/"+id+"/";
               JSONObject jsonObject = new JSONObject();
               try {
                   if (RadioA.isChecked()) {
                       url+="A";
                   }
                   else if(RadioB.isChecked())
                   {
                       url+="B";
                   }
                   else if(RadioC.isChecked())
                   {
                       url+="C";
                   }
                   else if(RadioD.isChecked())
                   {
                       url+="D";
                   }
                   else if(RadioE.isChecked())
                   {
                       url+="E";
                   }
               } catch (Exception e) {

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


           final String url = "http://192.168.1.29:5001/api/AossOgrenci/sorulariGetir/"+idsinav+"/"+ogrid;
           System.out.println(url);
           JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url, null,
                   new Response.Listener<JSONArray>() {
                       @Override
                       public void onResponse(JSONArray response) {
                           try
                           {
                               i -= 1;
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
                               intent.putExtra("kacsoru",kacsoru);
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
   });

   sinavibitir.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(RadioA.isChecked()==true || RadioB.isChecked()==true || RadioC.isChecked()==true|| RadioD.isChecked()==true|| RadioE.isChecked()==true)
           {
               String url ="http://192.168.1.29:5001/Sinav/Cevap/"+id+"/";
               JSONObject jsonObject = new JSONObject();
               try {
                   if (RadioA.isChecked()) {
                       url+="A";
                   }
                   else if(RadioB.isChecked())
                   {
                       url+="B";
                   }
                   else if(RadioC.isChecked())
                   {
                       url+="C";
                   }
                   else if(RadioD.isChecked())
                   {
                       url+="D";
                   }
                   else if(RadioE.isChecked())
                   {
                       url+="E";
                   }
               } catch (Exception e) {

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

               String url ="http://192.168.1.29:5001/Sinav/Bitir/";
           System.out.println(url);
               try {
                   url+=onlineSinavOgrenciId;

                   Intent intent = new Intent(ogrenci.this, anasayfaogrenci.class);
                   intent.putExtra("id", ogrid);
                   intent.putExtra("adsoyad",adsoyad);
                   intent.putExtra("alan",Alan);
                   startActivity(intent);

               } catch (Exception e) {

               }
               JsonObjectRequest jsonForPutRequest1 = new JsonObjectRequest(Request.Method.PUT, url, null,
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
               }) {

                   @Override
                   public Map<String, String> getHeaders() throws AuthFailureError {

                       Map<String, String> param = new HashMap<>();
                       return param;
                   }
               };
               queue.add(jsonForPutRequest1);


       }

   });

    }

}
