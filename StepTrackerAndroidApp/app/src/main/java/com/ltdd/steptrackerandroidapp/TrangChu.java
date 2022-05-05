package com.ltdd.steptrackerandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrangChu extends AppCompatActivity implements SensorEventListener {
    TextView tv_thoigian, tv_sobuoc;
//    Button btn_batdau;
    SensorManager sensorManager;
    boolean running = false;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        AnhXa();
        ThietLapThoiGian();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_trang_chu:
                        Toast.makeText(TrangChu.this, "Trang chủ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_bao_cao:
                        Toast.makeText(TrangChu.this, "Báo cáo", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_nguoi_dung:
                        Toast.makeText(TrangChu.this, "Tôi", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void AnhXa() {
        tv_thoigian = (TextView) findViewById(R.id.tv_thoigian);
        tv_sobuoc = (TextView) findViewById(R.id.tv_sobuoc);
//        btn_batdau = (Button) findViewById(R.id.btn_batdau);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        // Tim kiem Sensor
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "Không tìm thấy cảm biến!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            tv_sobuoc.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void ThietLapThoiGian() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(calendar.getTime());

        tv_thoigian.setText("Hôm nay: " + date);
    }
}