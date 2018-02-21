package com.example.hoang.hoabanfood1.Model;

/**
 * Created by hoang on 1/26/2018.
 */

public class ModelComment {
    String username;
    String noidung;

    public ModelComment(String username, String noidung) {
        this.username = username;
        this.noidung = noidung;
    }

    public ModelComment() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
