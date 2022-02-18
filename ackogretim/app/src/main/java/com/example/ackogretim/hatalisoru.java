package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.xml.transform.ErrorListener;

public class hatalisoru extends AppCompatActivity {
    public String adsoyad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hatalisoru);

        Button hataliBtnn=findViewById(R.id.hatalibtnn);
        Button hatalidegilbtnn=findViewById(R.id.hatalidegilbtn);
        final TextView idedit = findViewById(R.id.textView32);
        final TextView soruedit = findViewById(R.id.editText3);
        final TextView dogrucevapedit = findViewById(R.id.editText5);
        final TextView cevap1edit = findViewById(R.id.editText6);
        final TextView cevap2edit = findViewById(R.id.editText7);
        final TextView cevap3edit = findViewById(R.id.editText8);
        final TextView cevap4edit = findViewById(R.id.editText24);
        final TextView soruAlaniedit = findViewById(R.id.ders);
        final RadioButton kolayedit = findViewById(R.id.radioButton);
        final RadioButton ortaedit = findViewById(R.id.radioButton2);
        final RadioButton zoredit = findViewById(R.id.radioButton3);

        final RequestQueue queue = Volley.newRequestQueue(this);
        idedit.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id",1);
        String soru = intent.getStringExtra("soru");
        String dogruCevap = intent.getStringExtra("dogrucevap");
        String cevap1 = intent.getStringExtra("cevap1");
        String cevap2 = intent.getStringExtra("cevap2");
        String cevap3 = intent.getStringExtra("cevap3");
        String cevap4 = intent.getStringExtra("cevap4");
        String soruAlani = intent.getStringExtra("soruAlani");
        String zorluk = intent.getStringExtra("zorluk");
        final int hocaid = intent.getIntExtra("hocaid",1);
        adsoyad = intent.getStringExtra("adsoyad");

        soruedit.setKeyListener(null);
        dogrucevapedit.setKeyListener(null);
        cevap1edit.setKeyListener(null);
        cevap2edit.setKeyListener(null);
        cevap3edit.setKeyListener(null);
        cevap4edit.setKeyListener(null);
        soruAlaniedit.setKeyListener(null);








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

        hataliBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://192.168.1.29:5001/api/AossHataliSorular/hatali/"+id;
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
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
                Toast.makeText(getApplicationContext(),"Soru, Hatalı Olarak Oylanmıştır..",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(hatalisoru.this, ogretmenanasayfa.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);
            }
        });

        hatalidegilbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://192.168.1.3:5001/api/AossHataliSorular/hatasiz/"+id;
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
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
                Toast.makeText(getApplicationContext(),"Soru, Hatalı Değil Olarak Oylanmıştır..",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(hatalisoru.this, ogretmenanasayfa.class);
                intent.putExtra("id", hocaid);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);
            }
        });
    }
    public void geri (View view)
    {
        Intent intent = new Intent(hatalisoru.this, hatalisorularr.class);

        startActivity(intent);
    }
}
