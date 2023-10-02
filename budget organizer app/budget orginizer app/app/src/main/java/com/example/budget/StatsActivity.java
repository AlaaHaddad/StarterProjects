package com.example.budget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StatsActivity extends AppCompatActivity {
    private BarChart chart;
    final List<String> xAxisLabels = Arrays.asList("Pets", "Health", "Self", "Water", "Electricity", "Other", "Car Ins","Rent", "Car Gas", "Loans", "Parking", "Groceries");
    Button back2, calender;
    ValueFormatter xAxisFormatter;
    View.OnClickListener back2_listener, calender_listener;
    SharedPreferences settings;
    List<BarEntry> entries;
    float pets, health, self, water, electricity, car_insurance, car_gas, insurance, rent, loans, hanging, groceries, other, parking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        back2 = findViewById(R.id.back2);
        chart = findViewById(R.id.chart1);
        calender = findViewById(R.id.calender);
        settings = getApplicationContext().getSharedPreferences("stats", MODE_PRIVATE);
        entries = new ArrayList<>();
        xAxisFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // return the label at the corresponding index
                return xAxisLabels.get((int) value);
            }
        };
        calender_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatsActivity.this, CalenderActivity.class));
            }
        };
        back2_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatsActivity.this, MainActivity.class));
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setValueFormatter(xAxisFormatter);
        calender.setOnClickListener(calender_listener);
        back2.setOnClickListener(back2_listener);
        updateAVG();
    }


    public void updateAVG() {
        pets = settings.getFloat("pets", 0);
        health = settings.getFloat("health", 0);
        self = settings.getFloat("self", 0);
        water = settings.getFloat("water", 0);
        electricity = settings.getFloat("electricity", 0);
        other = settings.getFloat("other", 0);
        car_insurance = settings.getFloat("car_insurance", 0);
        car_gas = settings.getFloat("car_gas", 0);
        insurance = settings.getFloat("insurance", 0);
        rent = settings.getFloat("rent", 0);
        hanging = settings.getFloat("hanging", 0);
        loans = settings.getFloat("loans", 0);
        groceries = settings.getFloat("groceries", 0);
        parking = settings.getFloat("parking", 0);
        entries.add(new BarEntry(0, pets));
        entries.add(new BarEntry(1, health));
        entries.add(new BarEntry(2, self));
        entries.add(new BarEntry(3, water));
        entries.add(new BarEntry(4, electricity));
        entries.add(new BarEntry(5, other));
        entries.add(new BarEntry(6, car_insurance));
        entries.add(new BarEntry(7, rent));
        entries.add(new BarEntry(8, car_gas));
        entries.add(new BarEntry(9, hanging));
        entries.add(new BarEntry(10, loans));
        entries.add(new BarEntry(11, parking));
        entries.add(new BarEntry(12, groceries));
        entries.add(new BarEntry(13, parking));
        BarDataSet set = new BarDataSet(entries, "Average Spending");
        set.setColors(Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED, Color.LTGRAY, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED);
        set.setValueFormatter(xAxisFormatter);
        BarData data = new BarData(set);
        chart.setData(data);
        chart.invalidate();
    }
}
