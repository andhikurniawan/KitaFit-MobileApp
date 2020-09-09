package com.akasa.kitafit.model;

public class StepItem {

    public String durasi, poster, nomor;

    public StepItem(){

    }

    public StepItem(String durasi, String poster, String nomor) {
        this.durasi = durasi;
        this.poster = poster;
        this.nomor = nomor;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
