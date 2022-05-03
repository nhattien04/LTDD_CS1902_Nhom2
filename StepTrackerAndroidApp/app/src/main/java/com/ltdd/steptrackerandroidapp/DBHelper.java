package com.ltdd.steptrackerandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    // Khai báo tên và phiên bản database
    final static int DATABASE_VERSION = 1;
    final static String DATABASE_NAME = "StepTracker.db";
    private SQLiteDatabase mDefaultWritableDatabase = null;

    // Khai báo biến Context
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if (mDefaultWritableDatabase != null) {
            db = mDefaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.mDefaultWritableDatabase = db;
        String sql1 = "CREATE TABLE nguoidung ("
                + "TenDangNhap nvarchar PRIMARY KEY,"
                + "HoVaTen nvarchar,"
                + "NgaySinh DATETIME,"
                + "SDT nvarchar,"
                + "Email nvarchar,"
                + "MatKhau nvarchar,"
                + "XacNhanMatKhau nvarchar)";
        db.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS nguoidung");

        onCreate(db);
    }

    public Boolean themNguoiDung(String tenDangNhap, String hoVaTen, Date ngaySinh, String sdt, String email, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("HoVaTen", hoVaTen);
        values.put("NgaySinh", String.valueOf(ngaySinh));
        values.put("SDT", sdt);
        values.put("Email", email);
        values.put("TenDangNhap", tenDangNhap);
        values.put("MatKhau", matKhau);

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
