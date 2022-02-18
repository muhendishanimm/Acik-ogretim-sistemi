package com.example.ackogretim;

public class OnlineSinavlar {
    private int Id;
    private String Baslama;
    private String Bitis;
    private String Sure;
    private String Alan;

    public OnlineSinavlar(int Id, String Baslama, String Bitis, String Sure, String Alan) {
        this.Id = Id;
        this.Baslama = Baslama;
        this.Bitis = Bitis;
        this.Sure = Sure;
        this.Alan = Alan;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBaslama() {
        return Baslama;
    }

    public void setBaslama(String baslama) {
        Baslama = baslama;
    }

    public String getBitis() {
        return Bitis;
    }

    public void setBitis(String bitis) {
        Bitis = bitis;
    }

    public String getSure() {
        return Sure;
    }

    public void setAlan(String alan) {
        Alan = alan;
    }

    public String getAlan() {
        return Alan;
    }

    public void setSure(String sure) {
        Sure = sure;
    }
}
