package com.akasa.kitafit.model;

public class PolaMakanData {
    private String gambar, judul, link;

    public PolaMakanData(String gambar, String judul, String link) {
        this.gambar = gambar;
        this.judul = judul;
        this.link = link;
    }

    public PolaMakanData() {
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
