package com.ltdd.steptrackerandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChinhSuaNguoiDung extends AppCompatActivity {
    EditText edt_hovaten, edt_ngaysinh, edt_sdt, edt_email;
    Button btn_chonngay, btn_capnhat;
    DBHelper DB;
    String tenDangNhap = MainActivity.TENDANGNHAP;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_nguoi_dung);

        LoadThongTin();

        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NguoiDung nd = DB.getNguoiDung(tenDangNhap);
                String txtHoVaTen = edt_hovaten.getText().toString();
                String txtNgaySinh = edt_ngaysinh.getText().toString();
                String txtSDT = edt_sdt.getText().toString();
                String txtEmail = edt_email.getText().toString();

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                boolean flag = true;
                try {
                    date = sdf1.parse(txtNgaySinh);
                } catch (ParseException e) {
                    flag = false;
                }
                if(flag == false)
                {
                    Toast.makeText(ChinhSuaNguoiDung.this, "Ngày không hợp lệ, vui lòng kiểm tra lại (dd-MM-yyyy).", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(txtHoVaTen) || TextUtils.isEmpty(txtNgaySinh) ||
                        TextUtils.isEmpty(txtSDT) || TextUtils.isEmpty(txtEmail)) {
                    Toast.makeText(ChinhSuaNguoiDung.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    java.sql.Date dateNgaySinh = new java.sql.Date(date.getTime());
                    Boolean ketQua = DB.capNhatNguoiDung(tenDangNhap, txtHoVaTen, dateNgaySinh, txtSDT, txtEmail);
                    if (ketQua && flag) {
                        Toast.makeText(ChinhSuaNguoiDung.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChinhSuaNguoiDung.this, "Cập nhật thất bại. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_chonngay.setOnClickListener(new View.OnClickListener() {
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
                edt_ngaysinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void LoadThongTin() {
        AnhXa();
        NguoiDung n = DB.getNguoiDung(tenDangNhap);
        String txtHoVaTen = n.getHoVaTen();
        String txtNgaySinh = "";
        if (n.getNgaySinh() != null)
            txtNgaySinh = n.getNgaySinh().toString();
        String txtSDT = n.getSDT();
        String txtEmail = n.getEmail();

        edt_hovaten.setText(txtHoVaTen);
        edt_ngaysinh.setText(txtNgaySinh);
        edt_sdt.setText(txtSDT);
        edt_email.setText(txtEmail);
    }

    private void AnhXa() {
        edt_hovaten = (EditText) findViewById(R.id.edt_hovaten);
        edt_ngaysinh = (EditText) findViewById(R.id.edt_ngaysinh);
        edt_sdt = (EditText) findViewById(R.id.edt_sdt);
        edt_email = (EditText) findViewById(R.id.edt_email);

        btn_chonngay = (Button) findViewById(R.id.btn_chon_ngay);
        btn_capnhat = (Button) findViewById(R.id.btn_cap_nhat);

        DB = new DBHelper(this);
    }
}