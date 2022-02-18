package com.example.ackogretim;

public class Sorular {

    private int Id  ;
    private String Soru;
    private String DogruCevap;
    private String Cevap1;
    private String Cevap2;
    private String Cevap3;
    private String Cevap4;
    private String SoruAlani;
    private String Zorluk;



    public Sorular() {
    }

    public Sorular(int Id, String Soru , String DogruCevap, String Cevap1, String Cevap2, String Cevap3 , String Cevap4,String SoruAlani,String Zorluk ) {
        this.Id = Id;
        this.Soru = Soru;
        this.DogruCevap = DogruCevap;
        this.Cevap1 = Cevap1;
        this.Cevap2 = Cevap2;
        this.Cevap3 = Cevap3;
        this.Cevap4 = Cevap4;
        this.SoruAlani = SoruAlani;
        this.Zorluk = Zorluk;

    }
    // public  Sorular(int Id)


    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getSoru() {
        return Soru;
    }

    public void setSoru(String Soru) { this.Soru = Soru; }

    public String getDogruCevap() {
        return DogruCevap;
    }

    public void setDogruCevap(String DogruCevap) {
        this.DogruCevap = DogruCevap;
    }

    public String getCevap1() {
        return Cevap1;
    }

    public void setCevap1(String Cevap1) {
        this.Cevap1 = Cevap1;
    }

    public String getCevap2() {
        return Cevap2;
    }

    public void setCevap2(String Cevap2) {
        this.Cevap2 = Cevap2;
    }
    public String getCevap3() {
        return Cevap3;
    }

    public void setCevap3(String Cevap3) {
        this.Cevap3 = Cevap3;
    }
    public String getCevap4() {
        return Cevap4;
    }

    public void setCevap4(String Cevap4) {
        this.Cevap4 = Cevap4;
    }

    public String getSoruAlani() {
        return SoruAlani;
    }

    public void setSoruAlani(String SoruAlani) {
        this.SoruAlani = SoruAlani;
    }

    public String getZorluk() {
        return Zorluk;
    }

    public void setZorluk(String Zorluk) {
        this.Zorluk = Zorluk;
    }

}