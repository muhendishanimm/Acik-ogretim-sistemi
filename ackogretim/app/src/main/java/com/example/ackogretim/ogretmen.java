package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ogretmen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen);
    }
    public void geridon (View view)
    {
        Intent intent = new Intent(ogretmen.this, ogretmenanasayfa.class);

        startActivity(intent);
    }
}
