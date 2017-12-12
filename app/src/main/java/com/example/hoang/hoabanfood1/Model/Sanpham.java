package com.example.hoang.hoabanfood1.Model;

import java.io.Serializable;

/**
 * Created by hoang on 10/1/2017.
 */

public class Sanpham implements Serializable {
    int masp;
    String tensp;
    int maloaisp;
    String hinhanhsp;
    int giasp;
    String ghichu;

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

    public int getMaloaisp() {
        return maloaisp;
    }

    public void setMaloaisp(int maloaisp) {
        this.maloaisp = maloaisp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Sanpham(int masp, String tensp, int maloaisp, String hinhanhsp, int giasp, String ghichu) {

        this.masp = masp;
        this.tensp = tensp;
        this.maloaisp = maloaisp;
        this.hinhanhsp = hinhanhsp;
        this.giasp = giasp;
        this.ghichu = ghichu;
    }
}
