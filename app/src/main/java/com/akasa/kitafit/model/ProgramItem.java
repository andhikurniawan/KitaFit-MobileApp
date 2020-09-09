package com.akasa.kitafit.model;

public class ProgramItem {

    public String nama_program, gambar_program, id;

    public ProgramItem(){

    }

    public ProgramItem(String nama_program, String gambar_program, String id) {
        this.nama_program = nama_program;
        this.gambar_program = gambar_program;
        this.id = id;
    }

    public String getNama_program() {
        return nama_program;
    }

    public void setNama_program(String nama_program) {
        this.nama_program = nama_program;
    }

    public String getGambar_program() {
        return gambar_program;
    }

    public void setGambar_program(String gambar_program) {
        this.gambar_program = gambar_program;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
