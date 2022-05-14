package com.ltdd.steptrackerandroidapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class baocao extends AppCompatActivity {
    Button ngay, thang, nam;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocao);
        ngay = findViewById(R.id.btnNgay);
        thang = findViewById(R.id.btnThang);
        nam = findViewById(R.id.btnNam);
        DB = new DBHelper(this);
        ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();

                getChart(DB.reportNgay((String) b.get("keyNam")));
            }
        });
        thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();
                getChart2(DB.reportThang((String) b.get("keyNam")));
            }
        });
        nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iin = getIntent();
                Bundle b = iin.getExtras();
                getChart3(DB.reportNam((String) b.get("keyNam")));
            }
        });
    }

    public void getChart(List<BaoCaoNgay> lbaocaongay) {
        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> bar = new ArrayList<>();

        for (int i = 0; i < lbaocaongay.size(); i++) {

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date k = sdf1.parse(lbaocaongay.get(i).getNgay());
                bar.add(new BarEntry(k.getDate(), (float) lbaocaongay.get(i).getSoBuoc()));
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        BarDataSet barDataSet = new BarDataSet(bar, "Bar");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Số bước trong tuần");
        barChart.animateY(1400);
    }

//    public void getChart2(List<BaoCaoNgay> lbaocaongay) {
//        BarChart barChart = findViewById(R.id.barchart);
//        ArrayList<BarEntry> bar = new ArrayList<>();
//
//        for (int i = 0; i < lbaocaongay.size(); i++) {
//            bar.add(new BarEntry((float) lbaocaongay.get(i).getQuangDuong(), (float) lbaocaongay.get(i).getSoBuoc()));
//        }
//
//
//        BarDataSet barDataSet = new BarDataSet(bar, "Bar");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
//
//        BarData barData = new BarData(barDataSet);
//        barChart.setFitBars(true);
//        barChart.setData(barData);
//        barChart.getDescription().setText("OK BAR CHART");
//        barChart.animateY(1400);
//    }
    public void getChart2(List<BaoCaoNgay> lbaocaongay) {
        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> bar = new ArrayList<>();


        for (int i = 0; i < lbaocaongay.size(); i++) {
            bar.add(new BarEntry(Float.parseFloat(lbaocaongay.get(i).getNgay()), (float) lbaocaongay.get(i).getSoBuoc()));
        }


        BarDataSet barDataSet = new BarDataSet(bar, "Bar");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Số m trong tháng");
        barChart.animateY(1400);
    }
    public void getChart3(List<BaoCaoNgay> lbaocaongay) {
        BarChart barChart = findViewById(R.id.barchart);
        ArrayList<BarEntry> bar = new ArrayList<>();


        for (int i = 0; i < lbaocaongay.size(); i++) {
            bar.add(new BarEntry((int)Math.round(Double.parseDouble(lbaocaongay.get(i).getNgay())), (int) Math.round(lbaocaongay.get(i).getSoBuoc())));
        }


        BarDataSet barDataSet = new BarDataSet(bar, "Bar");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Số m trong tháng");
        barChart.animateY(1400);
    }
}