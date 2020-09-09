package com.akasa.kitafit.model;

public class AktivitasItem {
    String counter_hari, id_program_kesehatan;


    public AktivitasItem(){

    }

    public AktivitasItem(String counter_hari, String id_program_kesehatan) {
        this.counter_hari = counter_hari;
        this.id_program_kesehatan = id_program_kesehatan;
    }

    public String getCounter_hari() {
        return counter_hari;
    }

    public void setCounter_hari(String counter_hari) {
        this.counter_hari = counter_hari;
    }

    public String getId_program_kesehatan() {
        return id_program_kesehatan;
    }

    public void setId_program_kesehatan(String id_program_kesehatan) {
        this.id_program_kesehatan = id_program_kesehatan;
    }
}
