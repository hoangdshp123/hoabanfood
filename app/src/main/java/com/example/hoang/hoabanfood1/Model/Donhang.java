package com.example.hoang.hoabanfood1.Model;

/**
 * Created by hoang on 11/3/2017.
 */

public class Donhang {
    String username;
    int madonhang;
    String tenkh;
    String sdt;

    public Donhang(String username, int madonhang, String tenkh, String sdt) {
        this.username = username;
        this.madonhang = madonhang;
        this.tenkh = tenkh;
        this.sdt = sdt;
    }

    public Donhang() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(int madonhang) {
        this.madonhang = madonhang;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
