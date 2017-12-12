package com.example.hoang.hoabanfood1.Model;

/**
 * Created by hoang on 11/8/2017.
 */

public class Getchitietdonhang {
    String tensp;
    String hinhanh;
    int soluong;
    int tongtien;

    public Getchitietdonhang(String tensp, String hinhanh, int soluong, int tongtien) {
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.soluong = soluong;
        this.tongtien = tongtien;
    }

    public Getchitietdonhang() {
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
