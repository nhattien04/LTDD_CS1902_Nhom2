package com.ltdd.steptrackerandroidapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

        // Khai bao bang NguoiDung
        String sql1 = "CREATE TABLE nguoidung ("
                + "TenDangNhap nvarchar PRIMARY KEY UNIQUE,"
                + "HoVaTen nvarchar NOT NULL,"
                + "NgaySinh DATETIME NOT NULL,"
                + "ChieuCao DOUBLE,"
                + "CanNang DOUBLE, "
                + "BMI DOUBLE,"
                + "SDT nvarchar NOT NULL,"
                + "Email nvarchar NOT NULL,"
                + "MatKhau nvarchar NOT NULL)";
        db.execSQL(sql1);

        // Khai bao bang thongke
        String sql2 = "CREATE TABLE thongke (" +
                "IDThongKe INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SoBuoc DOUBLE," +
                "QuangDuong DOUBLE," +
                "Ngay DATETIME)";
        db.execSQL(sql2);

        // Khai bao bang chitietthongke
        String sql3 = "CREATE TABLE chitietthongke (" +
                "TenDangNhap nvarchar NOT NULL," +
                "IDThongKe INTEGER PRIMARY KEY)";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS nguoidung");
        db.execSQL("DROP TABLE IF EXISTS thongke");
        db.execSQL("DROP TABLE IF EXISTS chitietthongke");

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

        long ketQua = db.insert("nguoidung", null, values);
        if (ketQua == -1)
            return false;
        else {
            return true;
        }
    }

    public Boolean kiemTraTenDangNhap(String tenDangNhap) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from nguoidung where tendangnhap = ?", new String[] {tenDangNhap});

        if (cursor.getCount() > 0) {
            return true;
        }
        else return false;
    }

    public Boolean kiemTraTenDangNhap_MatKhau(String tenDangNhap, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from nguoidung where tendangnhap = ? and matkhau = ?", new String[] {tenDangNhap, matKhau});
        if (cursor.getCount() > 0) {
            return true;
        }
        else return false;
    }

    //..........NGUOIDUNG..........
    @SuppressLint("Range")
    public NguoiDung getNguoiDung(String tenDangNhap) {
        SQLiteDatabase db = this.getWritableDatabase();
        NguoiDung n = new NguoiDung();
        String sql = "select HoVaTen, NgaySinh, SDT, Email from nguoidung where TenDangNhap = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tenDangNhap} );

        if (cursor != null && cursor.moveToFirst()) {
            n.setHoVaTen(cursor.getString(cursor.getColumnIndex("HoVaTen")));
            n.setNgaySinh(java.sql.Date.valueOf(cursor.getString(cursor.getColumnIndex("NgaySinh"))));
            n.setSDT(cursor.getString(cursor.getColumnIndex("SDT")));
            n.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
        }
        cursor.close();
        return n;
    }

    public Boolean capNhatNguoiDung(String tenDangNhap, String hoVaTen, Date ngaySinh, String SDT, String Email) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = false;
        String sql = "select * from nguoidung where TenDangNhap = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tenDangNhap});

        if (cursor.moveToFirst() && cursor != null) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("HoVaTen", hoVaTen);
            contentValues.put("NgaySinh", String.valueOf(ngaySinh));
            contentValues.put("SDT", SDT);
            contentValues.put("Email", Email);

            long ketQua = db.update("nguoidung", contentValues, "TenDangNhap =?", new String[]{tenDangNhap});
            if (ketQua != -1)
                flag = true;
        }
        return flag;
    }

    //..........CHISO..........
    public Boolean themChiSo(String tenDangNhap, double chieuCao, double cannang) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = false;
        String sql = "select * from nguoidung where TenDangNhap = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tenDangNhap});

        if (cursor.moveToFirst() && cursor != null) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("ChieuCao", chieuCao);
            contentValues.put("CanNang", cannang);

            long ketQua = db.update("nguoidung", contentValues, "TenDangNhap =?", new String[]{tenDangNhap});
            if (ketQua != -1)
                flag = true;
        }
        return flag;
    }

    @SuppressLint("Range")
    public NguoiDung getChiSo(String tenDangNhap) {
        SQLiteDatabase db = this.getWritableDatabase();
        NguoiDung n = new NguoiDung();
        String sql = "select ChieuCao, CanNang from nguoidung where TenDangNhap = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tenDangNhap} );

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex("ChieuCao")) != null && cursor.getString(cursor.getColumnIndex("CanNang")) != null)
            {
                n.setChieuCao(Double.parseDouble(cursor.getString(cursor.getColumnIndex("ChieuCao"))));
                n.setCanNang(Double.parseDouble(cursor.getString(cursor.getColumnIndex("CanNang"))));
            }
        }
        cursor.close();
        return n;
    }

    public void sampleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        java.sql.Date sqlNgaySinh = null;
        try {
            date = sdf1.parse("26-03-2022");
            sqlNgaySinh = new java.sql.Date(date.getTime());
            values.put("SoBuoc", 23);
            values.put("QuangDuong", 43);
            values.put("Ngay", String.valueOf(sqlNgaySinh));
            db.insert("thongke", null, values);
        } catch (ParseException e) {

        }
        ContentValues valuess = new ContentValues();
        valuess.put("TenDangNhap","123");
        valuess.put("IDThongKe",2);
        valuess.put("SoNgay",4);
        db.insert("chitietthongke",null,valuess);
    }



    public List<BaoCaoNgay> reportNgay(String tenDangNhap){
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor res = dbb.rawQuery("SELECT kq.*\n" +
                "FROM thongke kq, chitietthongke ct\n" +
                "WHERE kq.IDThongKe = ct.IDThongKe and ct.TenDangNhap = ? and strftime('%Y', Ngay) = strftime('%Y',date('now'))\n" +
                "GROUP BY Ngay ORDER BY Ngay DESC LIMIT 7",new String[] {tenDangNhap});

        res.moveToFirst();
        List<BaoCaoNgay> baoCaoNgays = new ArrayList<>();
        if (res.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                BaoCaoNgay baoCaoNgay = new BaoCaoNgay();
                baoCaoNgay.setIDThongKE(res.getInt(0));
                baoCaoNgay.setSoBuoc(res.getDouble(1));
                baoCaoNgay.setQuangDuong(res.getDouble(2));
                baoCaoNgay.setNgay(res.getString(3));
                baoCaoNgays.add(baoCaoNgay);
            } while (res.moveToNext());
        }
        res.close();
        return baoCaoNgays;
    }

    public List<BaoCaoNgay> reportThang(String tenDangNhap){
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor res = dbb.rawQuery("SELECT SUM(SoBuoc) as 'Buoc', SUM(QuangDuong) as 'QD',strftime('%m', Ngay) as 'thang'\n" +
                "FROM thongke kq, chitietthongke ct\n" +
                "WHERE kq.IDThongKe = ct.IDThongKe and ct.TenDangNhap = ? and strftime('%Y', Ngay) = strftime('%Y',date('now'))\n" +
                "GROUP BY strftime('%m', Ngay)\n" +
                "ORDER BY strftime('%m', Ngay) DESC LIMIT 12",new String[] {tenDangNhap});

        res.moveToFirst();
        List<BaoCaoNgay> baoCaoNgays = new ArrayList<>();
        if (res.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                BaoCaoNgay baoCaoNgay = new BaoCaoNgay();
                baoCaoNgay.setSoBuoc(res.getDouble(0));
                baoCaoNgay.setNgay(String.valueOf(res.getDouble(2)));
                baoCaoNgays.add(baoCaoNgay);
            } while (res.moveToNext());
        }
        res.close();
        return baoCaoNgays;
    }

    public List<BaoCaoNgay> reportNam(String tenDangNhap){
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor res = dbb.rawQuery("SELECT SUM(QuangDuong) as 'QD', strftime('%Y', Ngay) as 'Nam'\n" +
                "FROM thongke kq, chitietthongke ct\n" +
                "WHERE kq.IDThongKe = ct.IDThongKe and ct.TenDangNhap = ? \n" +
                "GROUP BY strftime('%Y', Ngay)\n" +
                "ORDER BY strftime('%Y', Ngay) DESC",new String[] {tenDangNhap});

        res.moveToFirst();
        List<BaoCaoNgay> baoCaoNgays = new ArrayList<>();
        if (res.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                BaoCaoNgay baoCaoNgay = new BaoCaoNgay();
                baoCaoNgay.setSoBuoc(res.getDouble(0));
                baoCaoNgay.setNgay(String.valueOf(res.getDouble(1)));
                baoCaoNgays.add(baoCaoNgay);
            } while (res.moveToNext());
        }
        res.close();
        return baoCaoNgays;
    }

    public String getTen(String tenDangNhap){
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor res = dbb.rawQuery("select * from NguoiDung where tendangnhap = ?", new String[] {tenDangNhap});
        String ten = "";
        res.moveToFirst();

        if (res.moveToFirst()) {
            do {
                ten = res.getString(0);
            } while (res.moveToNext());
        }
        res.close();
        return ten;
    }

    //..........DEMBUOC..........
//    public NguoiDung getChiSo(String tenDangNhap) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        NguoiDung n = new NguoiDung();
//        String sql = "select ChieuCao, CanNang from nguoidung where TenDangNhap = ?";
//        Cursor cursor = db.rawQuery(sql, new String[]{tenDangNhap});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            if (cursor.getString(cursor.getColumnIndex("ChieuCao")) != null && cursor.getString(cursor.getColumnIndex("CanNang")) != null) {
//                n.setChieuCao(Double.parseDouble(cursor.getString(cursor.getColumnIndex("ChieuCao"))));
//                n.setCanNang(Double.parseDouble(cursor.getString(cursor.getColumnIndex("CanNang"))));
//            }
//        }
//        cursor.close();
//        return n;
//    }
    public Boolean themThongKe(double soBuoc, double quangduong, Date ngay) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("SoBuoc", soBuoc);
        contentValues.put("QuangDuong", quangduong);
        contentValues.put("Ngay", String.valueOf(ngay));
        long result = DB.insert("thongke", null, contentValues);

        return result != -1;
    }

    public Boolean themSoBuoc(int idThongKe, double soBuoc) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IDThongKe", idThongKe);
        contentValues.put("SoBuoc", soBuoc);

        long result = DB.insert("thongke", null, contentValues);

        return result != -1;
    }

    public Boolean themChiTietThongKe(int idThongKe, String tenDangNhap) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("IDThongKe", idThongKe);
        contentValues.put("TenDangNhap", tenDangNhap);

        long result = DB.insert("chitietthongke", null, contentValues);

        return result != -1;
    }

    @SuppressLint("Range")
    public int getIDThongKe() {
        SQLiteDatabase db = this.getWritableDatabase();
        ChiTietThongKe c = new ChiTietThongKe();
        String sql = "select IDThongKe from thongke order by IDThongKe desc limit 1";
        int idThongKe = 0;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex("IDThongKe")) != null)
            {
                idThongKe = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDThongKe")));
            }
        }
        cursor.close();
        return idThongKe;
    }

    @SuppressLint("Range")
    public Date getNgay() {
        SQLiteDatabase db = this.getWritableDatabase();
        ThongKe tk = new ThongKe();
        Date ngay = null;
        String sql = "select Ngay from thongke group by Ngay order by Ngay desc limit 1";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex("Ngay")) != null)
            {
                ngay = java.sql.Date.valueOf(cursor.getString(cursor.getColumnIndex("Ngay")));
            }
        }
        cursor.close();
        return ngay;
    }



}
