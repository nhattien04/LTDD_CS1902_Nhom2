package com.ltdd.steptrackerandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="DangNhap.db";
    public DBHelper(Context context) {
        super(context, "NguoiDung.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table NguoiDung (tendangnhap TEXT primary key, matkhau TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists NguoiDung");
    }

    public Boolean themNguoiDung(String tenDangNhap, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tendangnhap", tenDangNhap);
        values.put("matkhau", matKhau);

        long ketQua = db.insert("NguoiDung", null, values);
        if (ketQua == -1)
            return false;
        else {
            return true;
        }
    }

    public Boolean kiemTraTenDangNhap(String tenDangNhap) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung where tendangnhap = ?", new String[] {tenDangNhap});

        if (cursor.getCount() > 0) {
            return true;
        }
        else return false;
    }

    public Boolean kiemTraTenDangNhap_MatKhau(String tenDangNhap, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung where tendangnhap = ? and matkhau = ?", new String[] {tenDangNhap, matKhau});
        if (cursor.getCount() > 0) {
            return true;
        }
        else return false;
    }
}
