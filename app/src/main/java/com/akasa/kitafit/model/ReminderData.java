package com.akasa.kitafit.model;

public class ReminderData {
    int id;
    String title, tanggal, jam, date_time, img;

    public ReminderData() {
    }

    public ReminderData(int id, String title, String tanggal, String jam, String date_time, String img) {
        this.id = id;
        this.title = title;
        this.tanggal = tanggal;
        this.jam = jam;
        this.date_time = date_time;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
