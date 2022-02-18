package com.example.ackogretim;

public class OgretmenJava {
    private int Id;
    private String Mail;
    private String Sifre;
    private String AdSoyad;
    private String Alani;


    public OgretmenJava(int Id, String Mail, String Sifre, String AdSoyad, String Alani) {
        this.Id = Id;
        this.Mail = Mail;
        this.Sifre = Sifre;
        this.AdSoyad = AdSoyad;
        this.Alani = Alani;


    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }

    public String getAlani() {
        return Alani;
    }

    public void setAlani(String alani) {
        Alani = alani;
    }
}