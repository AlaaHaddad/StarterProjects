package com.example.budget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class CalenderActivity extends AppCompatActivity {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    BarChart chart;
    final List<String> xAxisLabels = Arrays.asList("Pets", "Health", "Self", "Water", "Electricity", "Other", "Car Ins","Rent", "Car Gas", "Loans", "Parking", "Groceries");
    ValueFormatter xAxisFormatter;
    ArrayList<BarEntry> entries;
    TextView month, new_text, after_spend, after_saving;
    Button back, next, prev, compare;
    Integer car_insurance, car_gas, insurance, water, electricity, self, health, hanging, parking, rent, loans, groceries, other, pets;
    View.OnClickListener back_listener, next_listener, prev_listener, compare_listener;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        new_text = findViewById(R.id.newtext);
        chart = findViewById(R.id.chart2);
        prev = findViewById(R.id.prevmonth);
        month = findViewById(R.id.textView4);
        next = findViewById(R.id.nextmonth);
        back = findViewById(R.id.back3);
        compare = findViewById(R.id.compare);
        after_spend = findViewById(R.id.textView6);
        after_saving = findViewById(R.id.dont_know);
        entries = new ArrayList<>();
        xAxisFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                // return the label at the corresponding index
                return xAxisLabels.get((int) value);
            }
        };
        compare_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getApplicationContext().getSharedPreferences("what", MODE_PRIVATE);
                editor = settings.edit();
                month = findViewById(R.id.textView4);
                editor.putString("month",month.getText().toString());
                switch (month.getText().toString()) {
                    case ("January"):
                        editor.putInt("selected", 1);
                        break;
                    case ("February"):
                        editor.putInt("selected", 2);
                        break;
                    case ("March"):
                        editor.putInt("selected", 3);
                        break;
                    case ("April"):
                        editor.putInt("selected", 4);
                        break;
                    case ("May"):
                        editor.putInt("selected", 5);
                        break;
                    case ("June"):
                        editor.putInt("selected", 6);
                        break;
                    case ("July"):
                        editor.putInt("selected", 7);
                        break;
                    case ("August"):
                        editor.putInt("selected", 8);
                        break;
                    case ("September"):
                        editor.putInt("selected", 9);
                        break;
                    case ("October"):
                        editor.putInt("selected", 10);
                        break;
                    case ("November"):
                        editor.putInt("selected", 11);
                        break;
                    case ("December"):
                        editor.putInt("selected", 12);
                        break;
                    default:
                        editor.remove("selected");
                }
                editor.commit();
                startActivity(new Intent(CalenderActivity.this, CompareActivity.class));
            }
        };
        back_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalenderActivity.this, MainActivity.class));
            }
        };
        next_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (month.getText().toString()) {
                    case "January":
                        next.setText("MAR");
                        month.setText("February");
                        prev.setText("JAN");
                        break;
                    case "February":
                        next.setText("APR");
                        month.setText("March");
                        prev.setText("FEB");
                        break;
                    case "March":
                        next.setText("MAY");
                        month.setText("April");
                        prev.setText("MAR");
                        break;
                    case "April":
                        next.setText("JUN");
                        month.setText("May");
                        prev.setText("APR");
                        break;
                    case "May":
                        next.setText("JUL");
                        month.setText("June");
                        prev.setText("MAY");
                        break;
                    case "June":
                        next.setText("AUG");
                        month.setText("July");
                        prev.setText("JUN");
                        break;
                    case "July":
                        next.setText("SEP");
                        month.setText("August");
                        prev.setText("JUL");
                        break;
                    case "August":
                        next.setText("OCT");
                        month.setText("September");
                        prev.setText("AUG");
                        break;
                    case "September":
                        next.setText("NOV");
                        month.setText("October");
                        prev.setText("SEP");
                        break;
                    case "October":
                        next.setText("DEC");
                        month.setText("November");
                        prev.setText("OCT");
                        break;
                    case "November":
                        next.setText("JAN");
                        month.setText("December");
                        prev.setText("NOV");
                        break;
                    case "December":
                        next.setText("FEB");
                        month.setText("January");
                        prev.setText("DEC");
                        break;
                }
                settings = getApplicationContext().getSharedPreferences(month.getText().toString(), 0);
                new_text.setText("Income: " + settings.getInt("earned", 0));
                after_spend.setText("Spent: " + settings.getInt("spent", 0));
                after_saving.setText("Saved: " + settings.getInt("saved", 0));
                updateChart(month.getText().toString());

            }
        };
        prev_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (month.getText().toString()) {
                    case "March":
                        next.setText("MAR");
                        month.setText("February");
                        prev.setText("JAN");
                        break;
                    case "April":
                        next.setText("APR");
                        month.setText("March");
                        prev.setText("FEB");
                        break;
                    case "May":
                        next.setText("MAY");
                        month.setText("April");
                        prev.setText("MAR");
                        break;
                    case "June":
                        next.setText("JUN");
                        month.setText("May");
                        prev.setText("APR");
                        break;
                    case "July":
                        next.setText("JUL");
                        month.setText("June");
                        prev.setText("MAY");
                        break;
                    case "August":
                        next.setText("AUG");
                        month.setText("July");
                        prev.setText("JUN");
                        break;
                    case "September":
                        next.setText("SEP");
                        month.setText("August");
                        prev.setText("JUL");
                        break;
                    case "October":
                        next.setText("OCT");
                        month.setText("September");
                        prev.setText("AUG");
                        break;
                    case "November":
                        next.setText("NOV");
                        month.setText("October");
                        prev.setText("SEP");
                        break;
                    case "December":
                        next.setText("DEC");
                        month.setText("November");
                        prev.setText("OCT");
                        break;
                    case "January":
                        next.setText("JAN");
                        month.setText("December");
                        prev.setText("NOV");
                        break;
                    case "February":
                        next.setText("FEB");
                        month.setText("January");
                        prev.setText("DEC");
                        break;
                }
                settings = getApplicationContext().getSharedPreferences(month.getText().toString(), 0);
                new_text.setText("Income: " + settings.getInt("earned", 0));
                after_spend.setText("Spent: " + settings.getInt("spent", 0));
                after_saving.setText("Saved: " + settings.getInt("saved", 0));
                updateChart(month.getText().toString());
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setValueFormatter(xAxisFormatter);
        compare.setOnClickListener(compare_listener);
        back.setOnClickListener(back_listener);
        next.setOnClickListener(next_listener);
        prev.setOnClickListener(prev_listener);
        settings = getApplicationContext().getSharedPreferences(month.getText().toString(), MODE_PRIVATE);
        new_text.setText("Income: " + (settings.getInt("earned", 0)));
        after_spend.setText("Spent: " + settings.getInt("spent", 0));
        after_saving.setText("Saved: " + settings.getInt("saved", 0));
        updateChart(month.getText().toString());
    }

    public void updateChart(String month) {

        settings = getApplicationContext().getSharedPreferences(month, 0);
        pets = settings.getInt("pets", 0);
        health = settings.getInt("health",0);
        self = settings.getInt("self",0);
        water = settings.getInt("water",0);
        electricity = settings.getInt("electricity", 0);
        other = settings.getInt("other",0);
        car_insurance = settings.getInt("car_insurance", 0);
        car_gas = settings.getInt("car_gas",0);
        insurance = settings.getInt("insurance",0);
        rent = settings.getInt("rent",0);
        hanging = settings.getInt("hanging",0);
        loans = settings.getInt("loans",0);
        groceries = settings.getInt("groceries",0);
        parking = settings.getInt("parking",0);
        entries.add(new BarEntry(0,pets));
        entries.add(new BarEntry(1,health));
        entries.add(new BarEntry(2,self));
        entries.add(new BarEntry(3,water));
        entries.add(new BarEntry(4,electricity));
        entries.add(new BarEntry(5,other));
        entries.add(new BarEntry(6,car_insurance));
        entries.add(new BarEntry(7,rent));
        entries.add(new BarEntry(8,car_gas));
        entries.add(new BarEntry(9,hanging));
        entries.add(new BarEntry(10,loans));
        entries.add(new BarEntry(11,parking));
        entries.add(new BarEntry(12,groceries));
        entries.add(new BarEntry(13,parking));
        BarDataSet set = new BarDataSet(entries, "Spending");
        set.setValueFormatter(xAxisFormatter);
        set.setColors(Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED, Color.LTGRAY, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.GRAY, Color.YELLOW, Color.RED);
        BarData data = new BarData(set);
        chart.setData(data);
        chart.invalidate();
    }
}

