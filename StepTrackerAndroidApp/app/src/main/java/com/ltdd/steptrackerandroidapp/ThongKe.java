package com.ltdd.steptrackerandroidapp;

import java.util.Date;

public class ThongKe {
    private int idThongKe;
    private double soBuoc;
    private double quangDuong;
    private Date ngay;

    public ThongKe() {}

    public int getIdThongKe() {
        return idThongKe;
    }

    public void setIdThongKe(int idThongKe) {
        this.idThongKe = idThongKe;
    }

    public double getSoBuoc() {
        return soBuoc;
    }

    public void setSoBuoc(double soBuoc) {
        this.soBuoc = soBuoc;
    }

    public double getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(double quangDuong) {
        this.quangDuong = quangDuong;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
