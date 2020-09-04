package com.akasa.kitafit.model;

public class ProgramKesehatanData {
    private int id_program_kesehatan, total_olahraga, total_kalori, durasi_program, telah_diikuti_sebanyak;
    private String nama_program, deskripsi, gambar_program;


    public ProgramKesehatanData() {
    }

    public ProgramKesehatanData(int id_program_kesehatan, int total_olahraga, int total_kalori, int durasi_program, int telah_diikuti_sebanyak, String nama_program, String deskripsi, String gambar_program) {
        this.id_program_kesehatan = id_program_kesehatan;
        this.total_olahraga = total_olahraga;
        this.total_kalori = total_kalori;
        this.durasi_program = durasi_program;
        this.telah_diikuti_sebanyak = telah_diikuti_sebanyak;
        this.nama_program = nama_program;
        this.deskripsi = deskripsi;
        this.gambar_program = gambar_program;
    }

    public String getGambar_program() {
        return gambar_program;
    }

    public void setGambar_program(String gambar_program) {
        this.gambar_program = gambar_program;
    }

    public int getId_program_Kesehatan() {
        return id_program_kesehatan;
    }

    public void setId_program_Kesehatan(int id_program_kesehatan) {
        this.id_program_kesehatan = id_program_kesehatan;
    }

    public int getTotal_olahraga() {
        return total_olahraga;
    }

    public void setTotal_olahraga(int total_olahraga) {
        this.total_olahraga = total_olahraga;
    }

    public int getTotal_kalori() {
        return total_kalori;
    }

    public void setTotal_kalori(int total_kalori) {
        this.total_kalori = total_kalori;
    }

    public int getDurasi_program() {
        return durasi_program;
    }

    public void setDurasi_program(int durasi_program) {
        this.durasi_program = durasi_program;
    }

    public int getTelah_diikuti_sebanyak() {
        return telah_diikuti_sebanyak;
    }

    public void setTelah_diikuti_sebanyak(int telah_diikuti_sebanyak) {
        this.telah_diikuti_sebanyak = telah_diikuti_sebanyak;
    }

    public String getNama_program() {
        return nama_program;
    }

    public void setNama_program(String nama_program) {
        this.nama_program = nama_program;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
