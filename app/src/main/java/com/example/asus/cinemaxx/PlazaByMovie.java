package com.example.asus.cinemaxx;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class PlazaByMovie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ImageView btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_by_movie);

        btnCalendar = (ImageView)findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textView = (TextView)findViewById(R.id.dateText);
        textView.setText(currentDateString);
    }
}
