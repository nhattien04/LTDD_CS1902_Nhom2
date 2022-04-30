package com.ltdd.steptrackerandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaoTaiKhoan extends AppCompatActivity {

    EditText edt_ten_dang_nhap, edt_mat_khau, edt_xac_nhan_mat_khau;
    Button btn_tao_tai_khoan, btn_dang_nhap;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);

        AnhXa();

        btn_tao_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhap = edt_ten_dang_nhap.getText().toString();
                String matKhau = edt_mat_khau.getText().toString();
                String xacNhanMatKhau = edt_xac_nhan_mat_khau.getText().toString();

                if (TextUtils.isEmpty(tenDangNhap) || TextUtils.isEmpty(matKhau) || TextUtils.isEmpty(xacNhanMatKhau)) {
                    Toast.makeText(TaoTaiKhoan.this, "Cần nhập đầy đủ thông tin các trường!", Toast.LENGTH_LONG).show();
                } else if (matKhau.equals(xacNhanMatKhau)) {
                    Boolean kiemTraTenDangNhap = DB.kiemTraTenDangNhap(tenDangNhap);
                    // Kiem tra trong CSDL da co ten dang nhap nay hay chua
                    if (kiemTraTenDangNhap == false) {
                        Boolean them = DB.themNguoiDung(tenDangNhap, matKhau);
                        if (them == true) {
                            Toast.makeText(TaoTaiKhoan.this, "Thêm người dùng thành công!", Toast.LENGTH_LONG).show();
                            Intent taoTaiKhoanIntent = new Intent(TaoTaiKhoan.this, TrangChu.class);
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
    }

    public void AnhXa() {
        edt_ten_dang_nhap = (EditText) findViewById(R.id.edt_tendangnhap);
        edt_mat_khau = (EditText) findViewById(R.id.edt_matkhau);
        edt_xac_nhan_mat_khau = (EditText) findViewById(R.id.edt_xacnhanmatkhau);
        DB = new DBHelper(TaoTaiKhoan.this);

        btn_tao_tai_khoan = (Button) findViewById(R.id.btn_tao_moi);
        btn_dang_nhap = (Button) findViewById(R.id.btn_dang_nhap_bang_tai_khoan_da_co);
    }
}