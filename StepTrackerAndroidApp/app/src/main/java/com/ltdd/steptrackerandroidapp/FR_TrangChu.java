package com.ltdd.steptrackerandroidapp;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ServiceLoader;

public class FR_TrangChu extends Fragment implements SensorEventListener {
    TextView tv_thoigian, tv_sobuoc, tv_stepdetector;
    SensorManager sensorManager;
    boolean running = false;
    BottomNavigationView bottomNavigationView;
    Sensor mStepCounter, mStepDetector;
    boolean isCounterSensorPresent, isDetectorSensorPresent;
    int demBuoc = 0, stepDetect = 0;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f_r__trang_chu, container, false);
        AnhXa();
        ThietLapThoiGian();

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }
        else {
            Toast.makeText(getActivity(), "Bộ đếm không hoạt động!", Toast.LENGTH_SHORT).show();
            isCounterSensorPresent = false;
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorSensorPresent = true;
        } else {
            Toast.makeText(getActivity(), "Cảm biến không hoạt động!", Toast.LENGTH_SHORT).show();
            isDetectorSensorPresent = false;
        }

        return view;
    }

    private void AnhXa() {
        tv_thoigian = (TextView) view.findViewById(R.id.tv_thoigian);
        tv_sobuoc = (TextView) view.findViewById(R.id.tv_sobuoc);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_nav);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.unregisterListener(this, mStepCounter);
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.unregisterListener(this, mStepCounter);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == mStepCounter) {
            demBuoc = (int) sensorEvent.values[0];
            tv_sobuoc.setText(String.valueOf(demBuoc));
        } else if (sensorEvent.sensor == mStepDetector) {
            stepDetect = (int) (stepDetect + sensorEvent.values[0]);
            tv_stepdetector.setText(String.valueOf(stepDetect));
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