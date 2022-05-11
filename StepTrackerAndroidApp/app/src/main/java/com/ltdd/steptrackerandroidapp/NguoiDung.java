package com.ltdd.steptrackerandroidapp;

import java.util.Date;

public class NguoiDung {
    private String tenDangNhap;
    private String hoVaTen;
    private Date ngaySinh;
    private String SDT;
    private String Email;
    private double chieuCao;
    private double canNang;

    public NguoiDung() {}

    public NguoiDung(String tenDangNhap, String hoVaTen, Date ngaySinh, String SDT, String Email, double chieuCao, double canNang) {
        this.setTenDangNhap(tenDangNhap);
        this.setHoVaTen(hoVaTen);
        this.setNgaySinh(ngaySinh);
        this.setSDT(SDT);
        this.setEmail(Email);
        this.setChieuCao(chieuCao);
        this.setCanNang(canNang);

    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public double getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(double chieuCao) {
        this.chieuCao = chieuCao;
    }

    public double getCanNang() {
        return canNang;
    }

    public void setCanNang(double canNang) {
        this.canNang = canNang;
    }
}
