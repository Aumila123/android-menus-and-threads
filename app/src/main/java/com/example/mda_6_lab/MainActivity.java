package com.example.mda_6_lab;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvFirst, tvSecond, tvOutput;
    private TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFirst = findViewById(R.id.tvFirst);
        tvSecond = findViewById(R.id.tvSecond);
        tvOutput = findViewById(R.id.tvOutput);

        // Užregistruojame kontekstinį meniu
        registerForContextMenu(tvFirst);
        registerForContextMenu(tvSecond);

        // PADAROME, KAD MENIU IŠŠOKTŲ IR PAPRASTU PASPAUDIMU (click), O NE TIK ILGU (long click)
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTextView = (TextView) v;
                v.showContextMenu(); // Iškviečia kontekstinį meniu
            }
        };

        tvFirst.setOnClickListener(clickListener);
        tvSecond.setOnClickListener(clickListener);
    }

    // ==========================================
    // 1. ACTIONBAR (VIRŠUTINIS) MENIU
    // ==========================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_iseiti) {
            finish(); // Baigia darbą
            return true;
        } else if (id == R.id.submenu_valandu_skirtumas) {
            showTimePicker(); // Atidaro laikrodį
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int currentTotalMinutes = currentHour * 60 + currentMinute;
                        int selectedTotalMinutes = hourOfDay * 60 + minute;
                        int diffMinutes = Math.abs(currentTotalMinutes - selectedTotalMinutes);

                        String message = "Skirtumas tarp dabar ir nurodyto laiko yra " + diffMinutes + " minutes";

                        tvFirst.setText(message);

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Skirtumas minutemis")
                                .setMessage(message)
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

    // ==========================================
    // 2. KONTEKSTINIS MENIU IR GIJOS (THREADS)
    // ==========================================

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v instanceof TextView) {
            selectedTextView = (TextView) v;
        }

        menu.add(0, 1, 0, "Simbolių skaičius šiame tekste");
        menu.add(0, 2, 1, "Simbolių vardijimas po vieną");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (selectedTextView == null) return super.onContextItemSelected(item);

        String currentText = selectedTextView.getText().toString();

        if (item.getItemId() == 1) {
            int charCount = currentText.length();
            String message = "Tekste yra " + charCount + " simboliu";

            tvSecond.setText(message);

            new AlertDialog.Builder(this)
                    .setTitle("Simboliu skaicius")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
            return true;

        } else if (item.getItemId() == 2) {
            startCharByCharThread(currentText);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void startCharByCharThread(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < text.length(); i++) {
                    final char currentChar = text.charAt(i);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvOutput.setText(String.valueOf(currentChar));
                        }
                    });

                    try {
                        Thread.sleep(1000); // 1 sekundės pauzė
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}