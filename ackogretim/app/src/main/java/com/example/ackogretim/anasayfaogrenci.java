package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class anasayfaogrenci extends AppCompatActivity {

    private String adsoyad;
    private String alani;
    private Button sinavlarim;
    private Button sonuclarim;
    private TextView txtadsoyad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfaogrenci);

        sinavlarim=findViewById(R.id.button22);
        sonuclarim=findViewById(R.id.button31);
        txtadsoyad=findViewById(R.id.textView22);

        Intent intent = getIntent();
        final int ogrid = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");
        alani = intent.getStringExtra("alan");
        final int ogrenciNo = intent.getIntExtra("ogrenciNo",1);
        txtadsoyad.setText("Hoşgeldiniz "+adsoyad);

        sinavlarim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(anasayfaogrenci.this, ogrencianasayfa.class);
                intent.putExtra("ogrid", ogrid);
                intent.putExtra("alan",alani);
                intent.putExtra("adsoyad",adsoyad);
                intent.putExtra("ogrenciNo",ogrenciNo);
                startActivity(intent);
            }
        });

        sonuclarim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(anasayfaogrenci.this, ogrencisonuclar.class);
                intent.putExtra("ogrid", ogrid);
                intent.putExtra("alan",alani);
                intent.putExtra("adsoyad",adsoyad);
                startActivity(intent);

            }
        });

    }
}
