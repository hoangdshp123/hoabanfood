package com.example.hoang.hoabanfood1.Model;

/**
 * Created by hoang on 10/9/2017.
 */

public class Giohang {
    public int masp;
    public String tensp;
    public long tongtien;
    public String hinhsp;
    public int soluong;

    public Giohang(int masp) {
        this.masp = masp;
    }

    public Giohang(int masp, String tensp, long tongtien, String hinhsp, int soluong) {
        this.masp = masp;
        this.tensp = tensp;
        this.tongtien = tongtien;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Giohang)) return false;

        Giohang giohang = (Giohang) o;

        return getMasp() == giohang.getMasp();

    }

    @Override
    public int hashCode() {
        return getMasp();
    }
}