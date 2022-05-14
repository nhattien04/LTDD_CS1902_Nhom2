package com.ltdd.steptrackerandroidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FR_TrangChiSo extends Fragment {
    EditText edt_chieucao, edt_cannang;
    Button btn_luu;
    TextView tv_bmi, tv_nhanxet;
    double chiSoBMI;
    DBHelper DB;
    String tenDangNhap = MainActivity.TENDANGNHAP;
    View view;
    boolean mode=false;
    public void setMode(boolean darkMode){
        this.mode = darkMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f_r__trang_chi_so, container, false);
        AnhXa();
        LoadChiSo();

        //      đổi màu
        setMode(mode);
        if (mode==true) {
            view.findViewById(R.id.img_tieude).setBackgroundResource(R.drawable.trang_chu_background_dark);
        }
        else{
            view.findViewById(R.id.img_tieude).setBackgroundResource(R.drawable.trang_chu_background_light);
        }
//      end doi mau


        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_chieucao.onEditorAction(EditorInfo.IME_ACTION_DONE); // An ban phim
                edt_cannang.onEditorAction(EditorInfo.IME_ACTION_DONE); // An ban phim

                if (TextUtils.isEmpty(edt_chieucao.getText().toString()) || TextUtils.isEmpty(edt_cannang.getText().toString())) {
                    Toast.makeText(view.getContext(), "Vui lòng nhập đầy đủ thông tin trước khi lưu!", Toast.LENGTH_SHORT).show();
                } else {
                    double chieuCao = Double.parseDouble(edt_chieucao.getText().toString()) / 100;
                    double canNang = Double.parseDouble(edt_cannang.getText().toString());
                    chiSoBMI = Math.round((canNang / Math.pow(chieuCao, 2)) * 10.0) / 10.0;

                    if (chiSoBMI < 18.5) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn cần bổ sung thêm dinh dưỡng!");
                    } else if (chiSoBMI >= 18.5 && chiSoBMI <= 24.9) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn có chỉ số BMI bình thường!");
                    } else if (chiSoBMI == 25) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn đang bị thừa cân!");
                    } else if (chiSoBMI > 25 && chiSoBMI <= 29.9) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn đang ở giai đoạn tiền béo phì/béo phì mức độ thấp!");
                    } else if (chiSoBMI >= 30 && chiSoBMI <= 34.9) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn đang bị béo phì độ I");
                    } else if (chiSoBMI >= 35 && chiSoBMI <= 39.9) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn đang bị béo phì độ II");
                    } else if (chiSoBMI == 40) {
                        tv_bmi.setText("Chỉ số BMI của bạn là: " + String.valueOf(chiSoBMI));
                        tv_nhanxet.setText("Bạn đang bị béo phì độ III");
                    }

                    Boolean themChiSo = DB.themChiSo(tenDangNhap, chieuCao * 100, canNang);
                    if (themChiSo) {
                        Toast.makeText(getActivity().getApplication(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplication(), "Lưu thất bại. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public void LoadChiSo() {
        AnhXa();
        NguoiDung n = DB.getChiSo(tenDangNhap);

        if (n.getChieuCao() == 0.0 || n.getCanNang() == 0.0) {
            edt_chieucao.setText("");
            edt_cannang.setText("");
            Toast.makeText(getActivity().getApplication(), "Hiện tại chưa có chỉ số chiều cao và cân nặng!", Toast.LENGTH_SHORT).show();
        } else {
            String txtChieuCao = "";
            if (String.valueOf(n.getChieuCao()) != null)
                txtChieuCao = String.valueOf(n.getChieuCao());

            String txtCanNang = "";
            if (String.valueOf(n.getCanNang()) != null)
                txtCanNang = String.valueOf(n.getCanNang());

            edt_chieucao.setText(txtChieuCao);
            edt_cannang.setText(txtCanNang);

            double chieuCao = n.getChieuCao() / 100;
            double bmi = Math.round((n.getCanNang() / Math.pow(chieuCao, 2)) * 10.0) / 10.0;

            if (bmi < 18.5) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn cần bổ sung thêm dinh dưỡng!");
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn có chỉ số BMI bình thường!");
            } else if (bmi == 25) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn đang bị thừa cân!");
            } else if (bmi > 25 && bmi <= 29.9) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn đang ở giai đoạn tiền béo phì/béo phì mức độ thấp!");
            } else if (bmi >= 30 && bmi <= 34.9) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn đang bị béo phì độ I");
            } else if (bmi >= 35 && bmi <= 39.9) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn đang bị béo phì độ II");
            } else if (bmi == 40) {
                tv_bmi.setText("Chỉ số BMI hiện tại của bạn là: " + String.valueOf(bmi));
                tv_nhanxet.setText("Bạn đang bị béo phì độ III");
            }
        }

    }
    private void AnhXa() {
        edt_chieucao = (EditText) view.findViewById(R.id.edt_chieucao);
        edt_cannang = (EditText) view.findViewById(R.id.edt_cannang);
        tv_bmi = (TextView) view.findViewById(R.id.tv_bmi);
        tv_nhanxet = (TextView) view.findViewById(R.id.tv_nhanxet);
        btn_luu = (Button) view.findViewById(R.id.btn_luu);
        DB = new DBHelper(getActivity());
    }
}