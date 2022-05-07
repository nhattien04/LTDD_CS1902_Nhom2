package com.ltdd.steptrackerandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_MenuBottom_Fragment extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_bottom_fragment);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        // Load Trang chu dau tien khi vua khoi dong
        Fragment selectedFragment0 = new FR_TrangChu();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment0).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_trang_chu:
                    Toast.makeText(Main_MenuBottom_Fragment.this, "Trang chủ", Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment1 = new FR_TrangChu();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment1).commit();
                    break;

                case R.id.action_bao_cao:
                    Toast.makeText(Main_MenuBottom_Fragment.this, "Báo cáo", Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment2 = new FR_TrangBaoCao();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment2).commit();
                    break;

                case R.id.action_chi_so:
                    Toast.makeText(Main_MenuBottom_Fragment.this, "Chỉ số", Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment3 = new FR_TrangChiSo();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment3).commit();
                    break;

                case R.id.action_nguoi_dung:
                    Toast.makeText(Main_MenuBottom_Fragment.this, "Tôi", Toast.LENGTH_SHORT).show();
                    Fragment selectedFragment4 = new FR_TrangNguoiDung();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment4).commit();
                    break;
            }
            return true;
        }
    };
}