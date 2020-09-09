package com.akasa.kitafit.model;

public class PolaItem {
    String id, mjudul, sjudul, pjudul, mposter, sposter, pposter, mmenu, smenu, pmenu;

    public PolaItem() {
    }

    public PolaItem(String id, String mjudul, String sjudul, String pjudul,
                    String mposter, String sposter, String pposter, String mmenu, String smenu, String pmenu) {
        this.id = id;
        this.mjudul = mjudul;
        this.sjudul = sjudul;
        this.pjudul = pjudul;
        this.mposter = mposter;
        this.sposter = sposter;
        this.pposter = pposter;
        this.mmenu = mmenu;
        this.smenu = smenu;
        this.pmenu = pmenu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMjudul() {
        return mjudul;
    }

    public void setMjudul(String mjudul) {
        this.mjudul = mjudul;
    }

    public String getSjudul() {
        return sjudul;
    }

    public void setSjudul(String sjudul) {
        this.sjudul = sjudul;
    }

    public String getPjudul() {
        return pjudul;
    }

    public void setPjudul(String pjudul) {
        this.pjudul = pjudul;
    }

    public String getMposter() {
        return mposter;
    }

    public void setMposter(String mposter) {
        this.mposter = mposter;
    }

    public String getSposter() {
        return sposter;
    }

    public void setSposter(String sposter) {
        this.sposter = sposter;
    }

    public String getPposter() {
        return pposter;
    }

    public void setPposter(String pposter) {
        this.pposter = pposter;
    }

    public String getMmenu() {
        return mmenu;
    }

    public void setMmenu(String mmenu) {
        this.mmenu = mmenu;
    }

    public String getSmenu() {
        return smenu;
    }

    public void setSmenu(String smenu) {
        this.smenu = smenu;
    }

    public String getPmenu() {
        return pmenu;
    }

    public void setPmenu(String pmenu) {
        this.pmenu = pmenu;
    }
}
