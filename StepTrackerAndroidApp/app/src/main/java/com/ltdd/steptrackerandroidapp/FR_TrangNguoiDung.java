package com.ltdd.steptrackerandroidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FR_TrangNguoiDung extends Fragment {
    TextView tv_tennguoidung;
    Button btn_chinhsua, btn_dangxuat;
    DBHelper DB;
    String tenDangNhap = MainActivity.TENDANGNHAP;
    View view;
    boolean mode=false;
    public void setMode(boolean darkMode){
        this.mode = darkMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f_r__trang_nguoi_dung, container, false);
        AnhXa();
        NguoiDung n = DB.getNguoiDung(tenDangNhap);
        String txtHoVaTen = n.getHoVaTen();
        tv_tennguoidung.setText(txtHoVaTen);

        //      đổi màu
        setMode(mode);
        if (mode==true) {
            view.findViewById(R.id.img_tieude).setBackgroundResource(R.drawable.trang_chu_background_dark);
        }
        else{
            view.findViewById(R.id.img_tieude).setBackgroundResource(R.drawable.trang_chu_background_light);
        }
//      end doi mau


        btn_chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChinhSua = new Intent(getActivity().getApplication(), ChinhSuaNguoiDung.class);
                startActivity(intentChinhSua);
            }
        });

        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDangXuat = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(intentDangXuat);
            }
        });

        return view;
    }

    public void AnhXa() {
        tv_tennguoidung = (TextView) view.findViewById(R.id.tv_tennguoidung);
        btn_chinhsua = (Button) view.findViewById(R.id.btn_chinhsua);
        btn_dangxuat = (Button) view.findViewById(R.id.btn_dangxuat);

        DB = new DBHelper(getActivity());
    }
}