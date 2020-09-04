package com.akasa.kitafit.model;

public class UserData {
   public String nama_user, jenis_kelamin, umur, berat_badan,tinggi_badan, email, password,  foto_user;

    public UserData(){}

    public UserData(String nama_user, String jenis_kelamin, String umur, String berat_badan, String tinggi_badan, String email, String password, String foto_user) {
        this.nama_user = nama_user;
        this.jenis_kelamin = jenis_kelamin;
        this.umur = umur;
        this.berat_badan = berat_badan;
        this.tinggi_badan = tinggi_badan;
        this.email = email;
        this.password = password;
        this.foto_user = foto_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(String berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(String tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
    }
}
