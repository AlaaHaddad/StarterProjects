package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    View.OnClickListener calculator_listener, calender_listener, stats_listener, comperator_listener;
    Button calender, calculator, stats, comperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comperator = findViewById(R.id.comperator);
        calculator = findViewById(R.id.calculator0);
        calender = findViewById(R.id.calender0);
        stats = findViewById(R.id.stats2);
        comperator_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("month",0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("what").commit();
                editor.remove("selected").commit();
                startActivity(new Intent(MainActivity.this, CompareActivity.class));
            }
        };
        stats_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
            }
        };
        calender_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalenderActivity.class));
            }
        };
        calculator_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        stats.setOnClickListener(stats_listener);
        calender.setOnClickListener(calender_listener);
        calculator.setOnClickListener(calculator_listener);
        comperator.setOnClickListener(comperator_listener);
    }
}
