package com.akasa.kitafit.model;

public class StepItem {

    public String durasi, poster;

    public StepItem(){

    }

    public StepItem(String durasi, String poster) {
        this.durasi = durasi;
        this.poster = poster;
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
