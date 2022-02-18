package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class eklenensorularlistesi extends AppCompatActivity {

    private RequestQueue mQueue;
    private ListView listView;
    private ListeAdapter listeAdapter;
    ArrayList sorular = new ArrayList<>();
    ArrayList idd = new ArrayList<>();
    ArrayList soruu = new ArrayList<>();
    ArrayList dogrucevapp = new ArrayList<>();
    ArrayList cevapp1 = new ArrayList<>();
    ArrayList cevapp2 = new ArrayList<>();
    ArrayList cevapp3 = new ArrayList<>();
    ArrayList cevapp4 = new ArrayList<>();
    ArrayList soruAlanii = new ArrayList<>();
    ArrayList zorlukk = new ArrayList<>();
    private String alani;
    private String adsoyad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eklenensorularlistesi);
        listView = findViewById(R.id.EklenenListele);
        mQueue= Volley.newRequestQueue(this);
        EditText arama= findViewById(R.id.editText16);
        Intent intent = getIntent();
        final int hocaid = intent.getIntExtra("id",1);
        final int sinavid = intent.getIntExtra("sinavid",1);
        adsoyad = intent.getStringExtra("adsoyad");
        alani = intent.getStringExtra("alan");
        final int kolay = intent.getIntExtra("kolayPuan",1);
        final int orta = intent.getIntExtra("ortaPuan",1);
        final int zor = intent.getIntExtra("zorPuan",1);


        final String url = "http://192.168.1.29:5001/api/AossOnlineSinavSorular/sinavId?sinavId="+sinavid;
        System.out.println(url);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) { //for döngüsü ile  tüm verileri aldık JSON dosyasından stringleri aldık
                            try
                            {
                                JSONObject obj = response.getJSONObject(i);
                                int Id  = obj.getInt("id");
                                idd.add(Id);
                                String Soru = obj.getString("soru");
                                soruu.add(Soru);
                                String DogruCevap = obj.getString("dogruCevap");
                                dogrucevapp.add(DogruCevap);
                                String Cevap1= obj.getString("cevap1");
                                cevapp1.add(Cevap1);
                                String Cevap2= obj.getString("cevap2");
                                cevapp2.add(Cevap2);
                                String Cevap3= obj.getString("cevap3");
                                cevapp3.add(Cevap3);
                                String Cevap4= obj.getString("cevap4");
                                cevapp4.add(Cevap4);
                                String SoruAlani= obj.getString("soruAlani");
                                soruAlanii.add(SoruAlani);
                                String Zorluk= obj.getString("zorluk");
                                zorlukk.add(Zorluk);
                                sorular.add(
                                        new Sorular( //sorular dizisine soruları attık
                                                Id,
                                                Soru,
                                                DogruCevap,
                                                Cevap1,
                                                Cevap2,
                                                Cevap3,
                                                Cevap4,
                                                SoruAlani,
                                                Zorluk)
                                );
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
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
        mQueue.add(request); //İstek kuyrugunu onaylardık .istek kuyruguna istediğimize ekledik
        listeAdapter = new ListeAdapter(eklenensorularlistesi.this ,sorular); //listeadapter sınıfından yeni nesne oluşturduk sorular dizisini kullanarak olusturdugumuz adaptere set ettik.
        listView.setAdapter(listeAdapter);// ADAPTERE EKLEDİk

        arama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (eklenensorularlistesi.this).listeAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Sinavdansorusil.class);
                intent.putExtra("id", (Integer) idd.get(position));
                intent.putExtra("soru", (String) soruu.get(position));
                intent.putExtra("dogrucevap", (String) dogrucevapp.get(position));
                intent.putExtra("cevap1", (String) cevapp1.get(position));
                intent.putExtra("cevap2", (String) cevapp2.get(position));
                intent.putExtra("cevap3", (String) cevapp3.get(position));
                intent.putExtra("cevap4", (String) cevapp4.get(position));
                intent.putExtra("soruAlani", (String) soruAlanii.get(position));
                intent.putExtra("zorluk", (String) zorlukk.get(position));
                intent.putExtra("hocaid", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                intent.putExtra("sinavid",sinavid);
                intent.putExtra("kolayPuan",kolay);
                intent.putExtra("ortaPuan",orta);
                intent.putExtra("zorPuan",zor);


                startActivity(intent);
            }
        });

    }


}
