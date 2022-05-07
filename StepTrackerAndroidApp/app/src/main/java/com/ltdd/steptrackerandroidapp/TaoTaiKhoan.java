package com.ltdd.steptrackerandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaoTaiKhoan extends AppCompatActivity {

    EditText edt_ho_va_ten, edt_ngay_sinh, edt_sdt, edt_email,
            edt_ten_dang_nhap, edt_mat_khau, edt_xac_nhan_mat_khau;
    Button btn_tao_tai_khoan, btn_dang_nhap, btn_chon_ngay;
    DBHelper DB;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);

        AnhXa();

        btn_tao_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoVaTen = edt_ho_va_ten.getText().toString();
                String ngaySinh = edt_ngay_sinh.getText().toString();
                String sdt = edt_sdt.getText().toString();
                String email = edt_email.getText().toString();
                String tenDangNhap = edt_ten_dang_nhap.getText().toString();
                String matKhau = edt_mat_khau.getText().toString();
                String xacNhanMatKhau = edt_xac_nhan_mat_khau.getText().toString();

                if (TextUtils.isEmpty(hoVaTen) || TextUtils.isEmpty(ngaySinh) ||
                        TextUtils.isEmpty(sdt) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(tenDangNhap) || TextUtils.isEmpty(matKhau) ||
                        TextUtils.isEmpty(xacNhanMatKhau)) {
                    Toast.makeText(TaoTaiKhoan.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                } else if (matKhau.equals(xacNhanMatKhau)) {
                    // Chuyen doi ngay
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date date = null;
                    java.sql.Date sqlNgaySinh = null;
                    try {
                        date = sdf1.parse(ngaySinh);
                        sqlNgaySinh = new java.sql.Date(date.getTime());
                    } catch (ParseException e) {
                        Toast.makeText(TaoTaiKhoan.this, "Ngày không hợp lệ, vui lòng kiểm tra lại (dd-MM-yyyy).", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }


                    Boolean kiemTraTenDangNhap = DB.kiemTraTenDangNhap(tenDangNhap);
                    // Kiem tra trong CSDL da co ten dang nhap nay hay chua
                    if (kiemTraTenDangNhap == false && flag == true) {
                        Boolean them = DB.themNguoiDung(tenDangNhap, hoVaTen, sqlNgaySinh, sdt, email, matKhau);
                        if (them == true) {
                            Toast.makeText(TaoTaiKhoan.this, "Thêm người dùng thành công!", Toast.LENGTH_LONG).show();
                            Intent taoTaiKhoanIntent = new Intent(TaoTaiKhoan.this, Main_MenuBottom_Fragment.class);
                            startActivity(taoTaiKhoanIntent);
                        } else {
                            Toast.makeText(TaoTaiKhoan.this, "Thêm không thành công, hãy thử lại!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(TaoTaiKhoan.this, "Tên đăng nhập này đã tồn tại, hãy chọn tên khác!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TaoTaiKhoan.this, "Mật khẩu không trùng nhau!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaoTaiKhoan.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_chon_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                edt_ngay_sinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void AnhXa() {
        edt_ho_va_ten = (EditText) findViewById(R.id.edt_hovaten);
        edt_ngay_sinh = (EditText) findViewById(R.id.edt_ngaysinh);
        edt_sdt = (EditText) findViewById(R.id.edt_sdt);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_ten_dang_nhap = (EditText) findViewById(R.id.edt_tendangnhap);
        edt_mat_khau = (EditText) findViewById(R.id.edt_matkhau);
        edt_xac_nhan_mat_khau = (EditText) findViewById(R.id.edt_xacnhanmatkhau);
        DB = new DBHelper(TaoTaiKhoan.this);

        btn_tao_tai_khoan = (Button) findViewById(R.id.btn_tao_moi);
        btn_dang_nhap = (Button) findViewById(R.id.btn_dang_nhap_bang_tai_khoan_da_co);
        btn_chon_ngay = (Button) findViewById(R.id.btn_chon_ngay);
    }
}