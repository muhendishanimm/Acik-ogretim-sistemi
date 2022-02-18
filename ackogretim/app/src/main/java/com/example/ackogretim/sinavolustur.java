package com.example.ackogretim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class sinavolustur extends AppCompatActivity {
    private Button selectDate;
    private Button selectTime;
    private Button bitisSaat;
    private Button bitisTarih;
    private DatePickerDialog datePickerDialog;
    private TextView dateTxt;
    private TextView timeTxt;
    private TextView txtTarih;
    private TextView txtSaat;
    private Calendar calendar;
    private int year, month, dayOfMonth;
    private EditText sinavsuresi;
    private TextView alani;
    private String alan;
    private String adsoyad;
    private int hocaid;
    private Button sınavolustur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinavolustur);
        selectDate = findViewById(R.id.selectDate);
        selectTime = findViewById(R.id.button2);
        bitisTarih= findViewById(R.id.button17);
        bitisSaat=findViewById(R.id.button24);
        txtTarih=findViewById(R.id.textView37);
        txtSaat=findViewById(R.id.textView39);
        dateTxt = findViewById(R.id.dateTxt);
        timeTxt = findViewById(R.id.timeTxt);
        sinavsuresi=findViewById(R.id.editText19);
        alani=findViewById(R.id.textView42);
        sınavolustur=findViewById(R.id.button25);

        Intent intent = getIntent(); //Intent putextra ile yollanan veriyi almak için
        alan=intent.getStringExtra("alani");
        hocaid = intent.getIntExtra("id",1);
        adsoyad = intent.getStringExtra("adsoyad");

        alani.setText(alan);

        final RequestQueue queue = Volley.newRequestQueue(this);
        sınavolustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url ="http://192.168.1.29:5001/api/AossOnlineSinav";
                JSONObject js = new JSONObject();
                try {
                    if (timeTxt.getText().toString() != "" && dateTxt.getText().toString() != "" && txtTarih.getText().toString() != "" && txtSaat.getText().toString() != ""&&sinavsuresi.getText().toString()!="" && alani.getText().toString()!="")
                    {

                        js.put("baslama", dateTxt.getText().toString()+" "+timeTxt.getText().toString());
                        js.put("bitis", txtTarih.getText().toString()+" "+txtSaat.getText().toString());
                        js.put("sure", sinavsuresi.getText().toString());
                        js.put("alan", alani.getText().toString());

                        Toast toast= Toast.makeText(getApplicationContext(),"Sınav Ekleme İşlemi Başarılı ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER|Gravity.RIGHT, 100, 200);
                        toast.show();
                        Intent intent = new Intent(sinavolustur.this, ogretmenanasayfa.class);
                        intent.putExtra("id", hocaid);
                        intent.putExtra("adsoyad",adsoyad);
                        startActivity(intent);

                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Lütfen Tüm Bölümleri Doldurunuz..!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 100, 200);
                        toast.show();
                    }

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
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(sinavolustur.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateTxt.setText(day + "." + (month+1) + "." + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {//saatButona Click Listener ekliyoruz

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldık
                int minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayı aldık
                TimePickerDialog timePicker;//Time Picker referansımızı oluşturduk

                //TimePicker objemizi oluşturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(sinavolustur.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTxt.setText( selectedHour + ":" + selectedMinute);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                       // timePicker.setIs24HourView(true);
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);

                timePicker.show();
            }
        });

        bitisTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(sinavolustur.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                txtTarih.setText(day + "." + (month+1) + "." + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        bitisSaat.setOnClickListener(new View.OnClickListener() {//saatButona Click Listener ekliyoruz

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();//
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);//Güncel saati aldık
                int minute = mcurrentTime.get(Calendar.MINUTE);//Güncel dakikayı aldık
                TimePickerDialog timePicker;//Time Picker referansımızı oluşturduk

                //TimePicker objemizi oluşturuyor ve click listener ekliyoruz
                timePicker = new TimePickerDialog(sinavolustur.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtSaat.setText( selectedHour + ":" + selectedMinute);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                        // timePicker.setIs24HourView(true);
                    }
                }, hour, minute, true);//true 24 saatli sistem için
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);

                timePicker.show();
            }
        });
    }
}
