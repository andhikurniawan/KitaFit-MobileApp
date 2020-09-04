package com.akasa.kitafit;

public class OlahragaItem {

    public String id, nama_olahraga, poster, deskripsi, durasi, fokus_area, kalori, link_video;


    public OlahragaItem() {

    }
    public OlahragaItem(String id, String nama_olahraga, String poster, String deskripsi, String durasi, String fokus_area, String kalori, String link_video) {
        this.nama_olahraga = nama_olahraga;
        this.poster = poster;
        this.deskripsi = deskripsi;
        this.durasi = durasi;
        this.fokus_area = fokus_area;
        this.kalori = kalori;
        this.link_video = link_video;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_olahraga() {
        return nama_olahraga;
    }

    public void setNama_olahraga(String nama_olahraga) {
        this.nama_olahraga = nama_olahraga;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getFokus_area() {
        return fokus_area;
    }

    public void setFokus_area(String fokus_area) {
        this.fokus_area = fokus_area;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getLink_video() {
        return link_video;
    }

    public void setLink_video(String link_video) {
        this.link_video = link_video;
    }
}
