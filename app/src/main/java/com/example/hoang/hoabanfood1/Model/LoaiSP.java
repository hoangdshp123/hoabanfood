package com.example.hoang.hoabanfood1.Model;

/**
 * Created by hoang on 9/30/2017.
 */

public class LoaiSP {
    public int Maloaisp;
    public String Tenloaisp;
    public String Hinhanhloaisp;

    public LoaiSP(int maloaisp, String tenloaisp, String hinhanhloaisp) {
        Maloaisp = maloaisp;
        Tenloaisp = tenloaisp;
        Hinhanhloaisp = hinhanhloaisp;
    }

    public LoaiSP() {
    }

    public int getMaloaisp() {
        return Maloaisp;
    }

    public void setMaloaisp(int maloaisp) {
        Maloaisp = maloaisp;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return Hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        Hinhanhloaisp = hinhanhloaisp;
    }
}
