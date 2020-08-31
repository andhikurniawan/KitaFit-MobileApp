package com.akasa.kitafit.model;

public class LiniMasaData {
    private int id_post;
    private String nama_user;
    private String foto_user;
    private String tanggal_post;
    private String foto_post;
    private String caption_post;
    private int jumlah_like;

    public LiniMasaData(int id_post, String nama_user, String foto_user, String tanggal_post, String foto_post, String caption_post, int jumlah_like) {
        this.id_post = id_post;
        this.nama_user = nama_user;
        this.foto_user = foto_user;
        this.tanggal_post = tanggal_post;
        this.foto_post = foto_post;
        this.caption_post = caption_post;
        this.jumlah_like = jumlah_like;
    }

    public LiniMasaData() {
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
    }

    public String getTanggal_post() {
        return tanggal_post;
    }

    public void setTanggal_post(String tanggal_post) {
        this.tanggal_post = tanggal_post;
    }

    public String getFoto_post() {
        return foto_post;
    }

    public void setFoto_post(String foto_post) {
        this.foto_post = foto_post;
    }

    public String getCaption_post() {
        return caption_post;
    }

    public void setCaption_post(String caption_post) {
        this.caption_post = caption_post;
    }

    public int getJumlah_like() {
        return jumlah_like;
    }

    public void setJumlah_like(int jumlah_like) {
        this.jumlah_like = jumlah_like;
    }
}
