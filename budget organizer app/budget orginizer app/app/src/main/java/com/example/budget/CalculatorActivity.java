package com.example.budget;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class CalculatorActivity extends AppCompatActivity {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    TextView  result, spending, buttom;
    String tmp;
    Integer total = 0, Earn = 0, save = 0, car_gas_t, car_insurance_t, insurance_t, pets_t, groceries_t, electricity_t, rent_t, parking_t, hanging_t, health_t, loans_t, water_t, self_t, other_t;
    Spinner monthSpinner;
    Button back, done, refresh, update;
    TextWatcher watcher;
    View.OnClickListener back_listener, done_listener, refresh_listener, update_listener;
    TextInputLayout savings, earnings, car_gas, car_insurance, insurance, pets, groceries, electricity, rent, parking, hanging, health, loans, water, self, other;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        result = findViewById(R.id.textView);
        buttom = findViewById(R.id.textView3);
        earnings = findViewById(R.id.earnings);
        spending = findViewById(R.id.textView2);
        savings = findViewById(R.id.savings);
        rent = findViewById(R.id.textView5);
        car_gas = findViewById(R.id.textView7);
        car_insurance = findViewById(R.id.textView8);
        parking = findViewById(R.id.textView10);
        groceries = findViewById(R.id.textView11);
        hanging = findViewById(R.id.textView12);
        pets = findViewById(R.id.textView13);
        health = findViewById(R.id.textView14);
        other = findViewById(R.id.textView15);
        loans = findViewById(R.id.textView16);
        insurance = findViewById(R.id.textView17);
        electricity = findViewById(R.id.textView18);
        water = findViewById(R.id.textView19);
        self = findViewById(R.id.textView20);
        done = findViewById(R.id.button3);
        refresh = findViewById(R.id.button);
        back = findViewById(R.id.back);
        update = findViewById(R.id.update);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                update.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        back_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, MainActivity.class));
            }
        };
        done_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total = 0;
                try {
                    tmp = earnings.getEditText().getText().toString();
                    Earn = Integer.parseInt(tmp);
                } catch (Exception e) {
                    Earn = 0;
                    earnings.getEditText().setText("N/A");
                }
                try {
                    tmp = other.getEditText().getText().toString();
                    other_t = Integer.parseInt(tmp);
                    total += other_t;
                } catch (Exception e) {
                    other_t = 0;
                    other.getEditText().setText("N/A");
                }
                try {
                    tmp = car_gas.getEditText().getText().toString();
                    car_gas_t = Integer.parseInt(tmp);
                    total += car_gas_t;
                } catch (Exception e) {
                    car_gas_t = 0;
                    car_gas.getEditText().setText("N/A");
                }
                try {
                    tmp = car_insurance.getEditText().getText().toString();
                    car_insurance_t = Integer.parseInt(tmp);
                    total += car_insurance_t;
                } catch (Exception e) {
                    car_insurance_t = 0;
                    car_insurance.getEditText().setText("N/A");
                }
                try {
                    tmp = parking.getEditText().getText().toString();
                    parking_t = Integer.parseInt(tmp);
                    total += parking_t;
                } catch (Exception e) {
                    parking_t = 0;
                    parking.getEditText().setText("N/A");
                }
                try {
                    tmp = groceries.getEditText().getText().toString();
                    groceries_t = Integer.parseInt(tmp);
                    total += groceries_t;
                } catch (Exception e) {
                    groceries_t = 0;
                    groceries.getEditText().setText("N/A");
                }
                try {
                    tmp = hanging.getEditText().getText().toString();
                    hanging_t = Integer.parseInt(tmp);
                    total += hanging_t;
                } catch (Exception e) {
                    hanging_t = 0;
                    hanging.getEditText().setText("N/A");
                }
                try {
                    tmp = pets.getEditText().getText().toString();
                    pets_t = Integer.parseInt(tmp);
                    total += pets_t;
                } catch (Exception e) {
                    pets_t = 0;
                    pets.getEditText().setText("N/A");
                }
                try {
                    tmp = health.getEditText().getText().toString();
                    health_t = Integer.parseInt(tmp);
                    total += health_t;
                } catch (Exception e) {
                    health_t = 0;
                    health.getEditText().setText("N/A");
                }
                try {
                    tmp = rent.getEditText().getText().toString();
                    rent_t = Integer.parseInt(tmp);
                    total += rent_t;
                } catch (Exception e) {
                    rent_t = 0;
                    rent.getEditText().setText("N/A");
                }
                try {
                    tmp = loans.getEditText().getText().toString();
                    loans_t = Integer.parseInt(tmp);
                    total += loans_t;
                } catch (Exception e) {
                    loans_t = 0;
                    loans.getEditText().setText("N/A");
                }
                try {
                    tmp = insurance.getEditText().getText().toString();
                    insurance_t = Integer.parseInt(tmp);
                    total += insurance_t;
                } catch (Exception e) {
                    insurance_t = 0;
                    insurance.getEditText().setText("N/A");
                }
                try {
                    tmp = electricity.getEditText().getText().toString();
                    electricity_t = Integer.parseInt(tmp);
                    total += electricity_t;
                } catch (Exception e) {
                    electricity_t = 0;
                    electricity.getEditText().setText("N/A");
                }
                try {
                    tmp = water.getEditText().getText().toString();
                    water_t = Integer.parseInt(tmp);
                    total += water_t;
                } catch (Exception e) {
                    water_t = 0;
                    water.getEditText().setText("N/A");
                }
                try {
                    tmp = self.getEditText().getText().toString();
                    self_t = Integer.parseInt(tmp);
                    total += self_t;
                } catch (Exception e) {
                    self_t = 0;
                    self.getEditText().setText("N/A");
                }
                try {
                    tmp = savings.getEditText().getText().toString();
                    save = Integer.parseInt(tmp);
                } catch (Exception e) {
                    save = 0;
                    savings.getEditText().setText("N/A");
                }
                Integer left = Earn - total;
                result.setText("RESULTS");
                spending.setText("This month you spent: " + total.toString());
                buttom.setText("Monthly income: " + Earn.toString() + "\n");
                if (left >= 0) {
                    if (left < save) {
                        buttom.append("You have " + left.toString() + " left, savings goal (" + save.toString() + ") not reached!");
                    } else {
                        left = left - save;
                        buttom.append("Savings goal reached (" + save.toString() + ")!, " + left.toString() + " left to spend");
                    }
                } else {
                    left = -left;
                    buttom.append("Overspend, you need " + left.toString() + " to cover yourself!!");
                }
                update.setEnabled(true);
            }
        };
        update_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monthSpinner.getSelectedItem().toString().equals("Select Month")) {
                    Toast.makeText(CalculatorActivity.this, "You Must Select A Month To Update", Toast.LENGTH_LONG + 1).show();
                } else {
                    settings = getApplicationContext().getSharedPreferences(monthSpinner.getSelectedItem().toString(), MODE_PRIVATE);
                    editor = settings.edit();
                    editor.putInt("pets", pets_t);
                    editor.putInt("other", other_t);
                    editor.putInt("car_insurance", car_insurance_t);
                    editor.putInt("car_gas", car_gas_t);
                    editor.putInt("parking", parking_t);
                    editor.putInt("groceries", groceries_t);
                    editor.putInt("health", health_t);
                    editor.putInt("self", self_t);
                    editor.putInt("loans", loans_t);
                    editor.putInt("water", water_t);
                    editor.putInt("electricity", electricity_t);
                    editor.putInt("hanging", hanging_t);
                    editor.putInt("insurance", insurance_t);
                    editor.putInt("rent", rent_t);
                    editor.putInt("spent", total);
                    editor.putInt("earned", Earn);
                    editor.putInt("saved", save);
                    editor.commit();
                    updateAvg();
                }
            }
        };
        refresh_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                Earn = 0;
                save = 0;
                update.setEnabled(false);
                monthSpinner.setSelection(0);
                result.setText("Organize your budget");
                spending.setText("Enter How Much You Spend Monthly On:");
                buttom.setText("Fill out the following (monthly):");
                savings.getEditText().getText().clear();
                earnings.getEditText().getText().clear();
                car_gas.getEditText().getText().clear();
                car_insurance.getEditText().getText().clear();
                parking.getEditText().getText().clear();
                groceries.getEditText().getText().clear();
                hanging.getEditText().getText().clear();
                insurance.getEditText().getText().clear();
                electricity.getEditText().getText().clear();
                water.getEditText().getText().clear();
                self.getEditText().getText().clear();
                other.getEditText().getText().clear();
                pets.getEditText().getText().clear();
                health.getEditText().getText().clear();
                rent.getEditText().getText().clear();
                loans.getEditText().getText().clear();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        earnings.getEditText().addTextChangedListener(watcher);
        savings.getEditText().addTextChangedListener(watcher);
        rent.getEditText().addTextChangedListener(watcher);
        car_gas.getEditText().addTextChangedListener(watcher);
        car_insurance.getEditText().addTextChangedListener(watcher);
        parking.getEditText().addTextChangedListener(watcher);
        groceries.getEditText().addTextChangedListener(watcher);
        hanging.getEditText().addTextChangedListener(watcher);
        pets.getEditText().addTextChangedListener(watcher);
        health.getEditText().addTextChangedListener(watcher);
        other.getEditText().addTextChangedListener(watcher);
        loans.getEditText().addTextChangedListener(watcher);
        insurance.getEditText().addTextChangedListener(watcher);
        electricity.getEditText().addTextChangedListener(watcher);
        water.getEditText().addTextChangedListener(watcher);
        self.getEditText().addTextChangedListener(watcher);
        back.setOnClickListener(back_listener);
        done.setOnClickListener(done_listener);
        update.setOnClickListener(update_listener);
        refresh.setOnClickListener(refresh_listener);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CalculatorActivity.this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }

    private void updateAvg() {
        int spent_cnt = 0, saved_cnt = 0, earned_cnt = 0, car_gas_cnt = 0, car_insurance_cnt = 0, insurance_cnt = 0, pets_cnt = 0, groceries_cnt = 0,electricity_cnt = 0, rent_cnt = 0,parking_cnt = 0,hanging_cnt = 0, health_cnt = 0, loans_cnt = 0, water_cnt = 0, self_cnt = 0, other_cnt = 0, i;
        float spent_avg = 0, earned_avg = 0, saved_avg = 0, pets_avg = 0,insurance_avg = 0, car_insurance_avg = 0, car_gas_avg = 0, water_avg = 0, electricity_avg = 0, other_avg = 0, health_avg = 0, self_avg = 0, loans_avg = 0, rent_avg = 0, hanging_avg = 0, parking_avg = 0, groceries_avg = 0;
        String[] arr = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for(i=0;i<12;i++) {
            settings = getApplicationContext().getSharedPreferences(arr[i], MODE_PRIVATE);
            pets_avg += settings.getInt("pets",0);
            insurance_avg += settings.getInt("insurance",0);
            car_insurance_avg += settings.getInt("car_insurance",0);
            car_gas_avg += settings.getInt("car_gas",0);
            water_avg += settings.getInt("water",0);
            electricity_avg += settings.getInt("electricity",0);
            other_avg += settings.getInt("other",0);
            health_avg += settings.getInt("health",0);
            self_avg += settings.getInt("self",0);
            loans_avg += settings.getInt("loans",0);
            rent_avg += settings.getInt("rent",0);
            hanging_avg += settings.getInt("hanging",0);
            parking_avg += settings.getInt("parking",0);
            groceries_avg += settings.getInt("groceries",0);
            spent_avg += settings.getInt("spent",0);
            earned_avg += settings.getInt("earned",0);
            saved_avg += settings.getInt("saved",0);
            if(settings.getInt("pets",0) != 0) {
                pets_cnt ++;
            }
            if(settings.getInt("insurance",0) != 0) {
                insurance_cnt ++;
            }
            if(settings.getInt("car_gas",0) != 0) {
                car_gas_cnt ++;
            }
            if(settings.getInt("car_insurance",0) != 0) {
                car_insurance_cnt ++;
            }
            if(settings.getInt("water",0) != 0) {
                water_cnt ++;
            }
            if(settings.getInt("electricity",0) != 0) {
                electricity_cnt ++;
            }
            if(settings.getInt("other",0) != 0) {
                other_cnt ++;
            }
            if(settings.getInt("loans",0) != 0) {
                loans_cnt ++;
            }
            if(settings.getInt("parking",0) != 0) {
                parking_cnt ++;
            }
            if(settings.getInt("health",0) != 0) {
                health_cnt ++;
            }
            if(settings.getInt("self",0) != 0) {
                self_cnt ++;
            }
            if(settings.getInt("hanging",0) != 0) {
                hanging_cnt ++;
            }
            if(settings.getInt("groceries",0) != 0) {
                groceries_cnt ++;
            }
            if(settings.getInt("rent",0) != 0) {
                rent_cnt ++;
            }
            if(settings.getInt("saved",0) != 0) {
                saved_cnt ++;
            }
            if(settings.getInt("earned",0) != 0) {
                earned_cnt++;
            }
            if (settings.getInt("spent",0) != 0) {
                spent_cnt ++;
            }
        }
        try {
            pets_avg = pets_avg / pets_cnt;
        } catch (ArithmeticException e) {
            pets_avg = 0;
        }
        try {
            insurance_avg = insurance_avg / insurance_cnt;
        } catch (ArithmeticException e) {
            insurance_avg = 0;
        }
        try {
            car_insurance_avg = car_insurance_avg / car_insurance_cnt;
        } catch (ArithmeticException e) {
            car_insurance_avg = 0;
        }
        try {
            car_gas_avg = car_gas_avg / car_gas_cnt;
        } catch (ArithmeticException e) {
            car_gas_avg = 0;
        }
        try {
            water_avg = water_avg / water_cnt;
        } catch (ArithmeticException e) {
            water_avg = 0;
        }
        try {
            electricity_avg = electricity_avg / electricity_cnt;
        } catch (ArithmeticException e) {
            electricity_avg = 0;
        }
        try {
            other_avg = other_avg / other_cnt;
        } catch (ArithmeticException e) {
            other_avg = 0;
        }
        try {
            health_avg = health_avg / health_cnt;
        } catch (ArithmeticException e) {
            health_avg = 0;
        }
        try {
            self_avg = self_avg / self_cnt;
        } catch (ArithmeticException e) {
            self_avg = 0;
        }
        try {
            loans_avg = loans_avg / loans_cnt;
        } catch (ArithmeticException e) {
            loans_avg = 0;
        }
        try {
            rent_avg = rent_avg / rent_cnt;
        } catch (ArithmeticException e) {
            rent_avg = 0;
        }
        try {
            hanging_avg = hanging_avg / hanging_cnt;
        } catch (ArithmeticException e) {
            hanging_avg = 0;
        }
        try {
            parking_avg = parking_avg / parking_cnt;
        } catch (ArithmeticException e) {
            parking_avg = 0;
        }
        try {
            groceries_avg = groceries_avg / groceries_cnt;
        } catch (ArithmeticException e) {
            groceries_avg = 0;
        }
        try {
            spent_avg = spent_avg / spent_cnt;
        } catch (ArithmeticException e) {
            spent_avg = 0;
        }
        try {
            earned_avg = earned_avg / earned_cnt;
        } catch (ArithmeticException e) {
            earned_avg = 0;
        }
        try {
            saved_avg = saved_avg / saved_cnt;
        } catch (ArithmeticException e) {
            saved_avg = 0;
        }
        settings = getApplicationContext().getSharedPreferences("stats", MODE_PRIVATE);
        editor = settings.edit();
        editor.putFloat("pets", pets_avg);
        editor.putFloat("insurance", insurance_avg);
        editor.putFloat("car_insurance", car_insurance_avg);
        editor.putFloat("car_gas", car_gas_avg);
        editor.putFloat("water", water_avg);
        editor.putFloat("electricity", electricity_avg);
        editor.putFloat("other", other_avg);
        editor.putFloat("health", health_avg);
        editor.putFloat("self", self_avg);
        editor.putFloat("loans", loans_avg);
        editor.putFloat("rent", rent_avg);
        editor.putFloat("hanging", hanging_avg);
        editor.putFloat("parking", parking_avg);
        editor.putFloat("groceries", groceries_avg);
        editor.commit();
        settings = getApplicationContext().getSharedPreferences("AVG", MODE_PRIVATE);
        editor = settings.edit();
        editor.putFloat("spent", spent_avg);
        editor.putFloat("earned", earned_avg);
        editor.putFloat("saved", saved_avg);
        editor.commit();
    }
}
