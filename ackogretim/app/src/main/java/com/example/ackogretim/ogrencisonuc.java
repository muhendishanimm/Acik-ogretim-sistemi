package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ogrencisonuc extends AppCompatActivity {
    private TextView dsayisi;
    private TextView ysayisi;
    private TextView bsayisi;
    private TextView puani;
    private Button geri;
    private String adsoyad;
    private String alani;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrencisonuc);

        dsayisi=findViewById(R.id.textView48);
        ysayisi=findViewById(R.id.textView49);
        bsayisi=findViewById(R.id.textView50);
        puani=findViewById(R.id.textView51);
        geri=findViewById(R.id.button32);

        Intent intent = getIntent();
        final int sinavid = intent.getIntExtra("id",0);
        adsoyad = intent.getStringExtra("adsoyad");
        alani = intent.getStringExtra("alan");
        final int dogru = intent.getIntExtra("dogru",0);
        final int yanlis = intent.getIntExtra("yanlis",0);
        final int bos = intent.getIntExtra("bos",0);
        final int puan = intent.getIntExtra("puan",0);

        dsayisi.setText(Integer.toString(dogru));
        ysayisi.setText(Integer.toString(yanlis));
        bsayisi.setText(Integer.toString(bos));
        puani.setText(Integer.toString(puan));

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
