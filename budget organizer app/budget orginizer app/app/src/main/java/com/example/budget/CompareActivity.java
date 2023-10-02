package com.example.budget;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


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

public class CompareActivity extends AppCompatActivity {
    Button home;
    BarDataSet set;
    final List<String> xAxisLabels = Arrays.asList("Pets", "Health", "Self", "Water", "Electricity", "Other", "Car Ins","Rent", "Car Gas", "Loans", "Parking", "Groceries");
    ValueFormatter xAxisFormatter;
    BarData data;
    View.OnClickListener home_listener;
    Spinner month1spinner, month2spinner;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    String month1, month2;
    TextView data1, data2;
    int selected;
    SharedPreferences settings;
    AdapterView.OnItemSelectedListener listener1,listener2;
    ArrayList<BarEntry> entries1, entries2;
    private BarChart chart1, chart2;

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_compare);
        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        chart1 = findViewById(R.id.chart1_comp);
        chart2 = findViewById(R.id.chart2_comp);
        month1spinner = findViewById(R.id.month1spinner);
        month2spinner = findViewById(R.id.month2spinner);
        adapter1 = ArrayAdapter.createFromResource(CompareActivity.this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = ArrayAdapter.createFromResource(CompareActivity.this, R.array.months_array2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        home = findViewById(R.id.home1);
        xAxisFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // return the label at the corresponding index
                return xAxisLabels.get((int) value);
            }
        };
        home_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompareActivity.this, MainActivity.class));
            }
        };
        listener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month1 = month1spinner.getSelectedItem().toString();
                if (month1.equals("Select Month")) {
                    settings = getApplicationContext().getSharedPreferences("what", MODE_PRIVATE);
                    month1 = settings.getString("month", "N/A");
                    selected = settings.getInt("select", 1);
                    month1spinner.setSelection(selected);
                    if (!month1.equals("N/A")) {
                        updateChart(chart1, getApplicationContext().getSharedPreferences(month1, 0), 1);
                    }
                } else {
                    updateChart(chart1, getApplicationContext().getSharedPreferences(month1, 0),1);
                }
                settings = getApplicationContext().getSharedPreferences(month1, MODE_PRIVATE);
                data1.setText("Income: "+settings.getInt("earned",0)+"\nSpent: "+settings.getInt("spent",0)+"\nSaved: "+settings.getInt("saved",0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        listener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month2 = month2spinner.getSelectedItem().toString();
                if (month2.equals("AVG")) {
                    updateAvGChart(chart2, getApplicationContext().getSharedPreferences("stats",0));
                    settings = getApplicationContext().getSharedPreferences(month2, MODE_PRIVATE);
                    data2.setText("Income: "+settings.getFloat("earned",0)+"\nSpent: "+settings.getFloat("spent",0)+"\nSaved: "+settings.getFloat("saved",0));

                } else {
                    updateChart(chart2, getApplicationContext().getSharedPreferences(month2, 0),2);
                    settings = getApplicationContext().getSharedPreferences(month2, MODE_PRIVATE);
                    data2.setText("Income: "+settings.getInt("earned",0)+"\nSpent: "+settings.getInt("spent",0)+"\nSaved: "+settings.getInt("saved",0));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        chart2.clear();
        chart1.getXAxis().setEnabled(true);
        chart2.getXAxis().setEnabled(true);
        chart1.getXAxis().setValueFormatter(xAxisFormatter);
        chart2.getXAxis().setValueFormatter(xAxisFormatter);
        adapter1 = ArrayAdapter.createFromResource(CompareActivity.this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month1spinner.setAdapter(adapter1);
        month2spinner.setAdapter(adapter2);
        month1spinner.setOnItemSelectedListener(listener1);
        month2spinner.setOnItemSelectedListener(listener2);
        home.setOnClickListener(home_listener);
    }

    public void updateAvGChart(BarChart chart, SharedPreferences sett) {
        entries2.clear();
        entries2.add(new BarEntry(0, sett.getFloat("pets", 0)));
        entries2.add(new BarEntry(1, sett.getFloat("health", 0)));
        entries2.add(new BarEntry(2, sett.getFloat("self", 0)));
        entries2.add(new BarEntry(3, sett.getFloat("water", 0)));
        entries2.add(new BarEntry(4, sett.getFloat("electricity", 0)));
        entries2.add(new BarEntry(5, sett.getFloat("other", 0)));
        entries2.add(new BarEntry(6, sett.getFloat("car_gas", 0)));
        entries2.add(new BarEntry(7, sett.getFloat("car_insurance", 0)));
        entries2.add(new BarEntry(8, sett.getFloat("insurance", 0)));
        entries2.add(new BarEntry(9, sett.getFloat("rent", 0)));
        entries2.add(new BarEntry(10, sett.getFloat("hanging", 0)));
        entries2.add(new BarEntry(11, sett.getFloat("loans", 0)));
        entries2.add(new BarEntry(12, sett.getFloat("groceries", 0)));
        entries2.add(new BarEntry(13, sett.getFloat("parking", 0)));
        set = new BarDataSet(entries2, "Average Stats");
        set.setColors(Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED, Color.LTGRAY, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED);
        set.setValueFormatter(xAxisFormatter);
        BarData data = new BarData(set);
        chart.setData(data);
        chart.invalidate();

    }
    public void updateChart(BarChart chart, SharedPreferences sett, int a) {
        if(a == 1) {
            entries1.clear();
            entries1.add(new BarEntry(0, sett.getInt("pets", 0)));
            entries1.add(new BarEntry(1, sett.getInt("health", 0)));
            entries1.add(new BarEntry(2, sett.getInt("self", 0)));
            entries1.add(new BarEntry(3, sett.getInt("water", 0)));
            entries1.add(new BarEntry(4, sett.getInt("electricity", 0)));
            entries1.add(new BarEntry(5, sett.getInt("other", 0)));
            entries1.add(new BarEntry(6, sett.getInt("car_insurance", 0)));
            entries1.add(new BarEntry(7, sett.getInt("rent", 0)));
            entries1.add(new BarEntry(8, sett.getInt("car_gas", 0)));
            entries1.add(new BarEntry(9, sett.getInt("hanging", 0)));
            entries1.add(new BarEntry(10, sett.getInt("loans", 0)));
            entries1.add(new BarEntry(11, sett.getInt("parking", 0)));
            entries1.add(new BarEntry(12, sett.getInt("groceries", 0)));
            entries1.add(new BarEntry(13, sett.getInt("parking", 0)));
            set = new BarDataSet(entries1, month1);
        } else {
            entries2.clear();
            entries2.add(new BarEntry(0, sett.getInt("pets", 0)));
            entries2.add(new BarEntry(1, sett.getInt("health", 0)));
            entries2.add(new BarEntry(2, sett.getInt("self", 0)));
            entries2.add(new BarEntry(3, sett.getInt("water", 0)));
            entries2.add(new BarEntry(4, sett.getInt("electricity", 0)));
            entries2.add(new BarEntry(5, sett.getInt("other", 0)));
            entries2.add(new BarEntry(6, sett.getInt("car_insurance", 0)));
            entries2.add(new BarEntry(7, sett.getInt("rent", 0)));
            entries2.add(new BarEntry(8, sett.getInt("car_gas", 0)));
            entries2.add(new BarEntry(9, sett.getInt("hanging", 0)));
            entries2.add(new BarEntry(10, sett.getInt("loans", 0)));
            entries2.add(new BarEntry(11, sett.getInt("parking", 0)));
            entries2.add(new BarEntry(12, sett.getInt("groceries", 0)));
            entries2.add(new BarEntry(13, sett.getInt("parking", 0)));
            set = new BarDataSet(entries2, month2);
        }
        set.setColors(Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED, Color.LTGRAY, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED);
        set.setValueFormatter(xAxisFormatter);
        data = new BarData(set);
        chart.setData(data);
        chart.invalidate();
    }
}
