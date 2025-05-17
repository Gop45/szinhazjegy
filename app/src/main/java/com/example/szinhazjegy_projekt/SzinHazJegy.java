package com.example.szinhazjegy_projekt;

public class SzinHazJegy {
    private String id;
    private String eloadas;
    private String datum;
    private String helyszin;
    private int sor;
    private int szek;
    private int ar;

    public SzinHazJegy() {}

    public SzinHazJegy(String id, String eloadas, String datum, String helyszin, int sor, int szek, int ar) {
        this.id = id;
        this.eloadas = eloadas;
        this.datum = datum;
        this.helyszin = helyszin;
        this.sor = sor;
        this.szek = szek;
        this.ar = ar;
    }

    // Getters és setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEloadas() { return eloadas; }
    public void setEloadas(String eloadas) { this.eloadas = eloadas; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getHelyszin() { return helyszin; }
    public void setHelyszin(String helyszin) { this.helyszin = helyszin; }

    public int getSor() { return sor; }
    public void setSor(int sor) { this.sor = sor; }

    public int getSzek() { return szek; }
    public void setSzek(int szek) { this.szek = szek; }

    public int getAr() { return ar; }
    public void setAr(int ar) { this.ar = ar; }
}
