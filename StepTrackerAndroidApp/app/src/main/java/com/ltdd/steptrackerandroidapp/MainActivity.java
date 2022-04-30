package com.ltdd.steptrackerandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edt_tendangnhap, edt_matkhau;
    Button btn_batdau;
    ImageView facebook_icon, instagram_icon;
    TextView tv_taotaikhoanmoi;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        facebook_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myUriString = "https://www.facebook.com/TruongDaiHocMo";
                Intent intentNumber1 = new Intent(Intent.ACTION_VIEW, Uri.parse(myUriString));
                startActivity(intentNumber1);
            }
        });

        instagram_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myUriString = "https://www.instagram.com/ou.edu.vn/";
                Intent intentNumber2 = new Intent(Intent.ACTION_VIEW, Uri.parse(myUriString));
                startActivity(intentNumber2);
            }
        });

        tv_taotaikhoanmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taoTaiKhoanIntent = new Intent(MainActivity.this, TaoTaiKhoan.class);
                startActivity(taoTaiKhoanIntent);
            }
        });

        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhap = edt_tendangnhap.getText().toString();
                String matKhau = edt_matkhau.getText().toString();

                if (TextUtils.isEmpty(tenDangNhap) || TextUtils.isEmpty(matKhau)) {
                    Toast.makeText(MainActivity.this, "Cần nhập đầy đủ thông tin các trường!", Toast.LENGTH_LONG).show();

                } else {
                    Boolean kiemTraTenDangNhap_MatKhau = DB.kiemTraTenDangNhap_MatKhau(tenDangNhap, matKhau);
                    if (kiemTraTenDangNhap_MatKhau == true) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, TrangChu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu! Hãy tạo tài khoản nếu chưa có!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void AnhXa() {
        edt_tendangnhap = (EditText) findViewById(R.id.edt_tendangnhap);
        edt_matkhau = (EditText) findViewById(R.id.edt_matkhau);
        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        facebook_icon = (ImageView) findViewById(R.id.facebook_icon);
        instagram_icon = (ImageView) findViewById(R.id.instagram_icon);

        tv_taotaikhoanmoi = (TextView) findViewById(R.id.tv_taotaikhoan);

        DB = new DBHelper(this);
    }
}