package com.example.akalavan.myapplication.report;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akalavan.myapplication.MainActivity;
import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.db.DataBaseHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class reportTraining extends AppCompatActivity {
    DataBaseHelper databaseHelper = MainActivity.getDatabaseHelper();
    SQLiteDatabase db = databaseHelper.open();
    TextView textViewDateStart, textViewDateEnd;
    Button btnDateStart, btnDateEnd;
    Calendar dateAndTime = Calendar.getInstance();
    private int idV;
    private LineChart chart;
    ArrayList<String> BarEntryLabels ;
    Spinner spinnerMonth;
    int month;
    SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<Training> trainings = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_training);
        textViewDateStart = findViewById(R.id.textViewDateStart);
        textViewDateEnd = findViewById(R.id.textViewDateEnd);
        btnDateStart = findViewById(R.id.buttonDateStart);
        btnDateEnd = findViewById(R.id.buttonDateEnd);
    }

    // установка начальных даты и времени
    private void setInitialDateTime(TextView textView) {

        textView.setText(formatForDateNow.format(dateAndTime.getTimeInMillis()));
    }

    public void setDateEnd(View view) {
        idV = btnDateEnd.getId();
        new DatePickerDialog(reportTraining.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setDateStart(View view) {
        idV = btnDateStart.getId();
        new DatePickerDialog(reportTraining.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            switch (idV) {
                case R.id.buttonDateStart:{
                    setInitialDateTime(textViewDateStart);
                    break;
                }
                case R.id.buttonDateEnd:{
                    setInitialDateTime(textViewDateEnd);
                    break;
                }
            }

        }
    };

    public void showReport(View view) {
        Cursor userCursor;
        ContentValues cv;
        Log.d("DataBaseHelper", String.valueOf(textViewDateStart.getText()));
        Log.d("DataBaseHelper", String.valueOf(textViewDateEnd.getText()));

        String selection = "date >= ? AND date <= ?";
        String[] selectionArgs = new String[]{ String.valueOf(textViewDateStart.getText()), String.valueOf(textViewDateEnd.getText()) };
        userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, selectionArgs, null, null, null);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                trainings = new ArrayList<>();
                int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_TRAINING);
                int idDay = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_DAY_TRAINING);
                int idDate = userCursor.getColumnIndex(DataBaseHelper.COLUMN_DATE_TRAINING);
                int idPercent = userCursor.getColumnIndex(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING);
                String str;
                do {
                    str = "";
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                    }
                    trainings.add(new Training(userCursor.getInt(idIndex), userCursor.getInt(idDay), userCursor.getInt(idPercent), userCursor.getString(idDate)));
                    Log.d("DataBaseHelper ","showReport date >= " + String.valueOf(textViewDateStart.getText()) + " AND " + String.valueOf(textViewDateEnd.getText()) + " =< date :" + str);
                } while (userCursor.moveToNext());
            } else Log.d("DataBaseHelper", "showReport: 0");
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");

        if (!trainings.isEmpty()) {
            ArrayList<Entry> entries = new ArrayList<>();
            int i = 1;
            for (Training training :
                    trainings) {
                entries.add(new Entry(i++, training.getPercent()));
            }

            chart = findViewById(R.id.chart1);
            chart.clear();
            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            LineDataSet set1 = new LineDataSet(entries, "tr");
            set1.setCubicIntensity(1);
            set1.setLineWidth(4f);
            set1.setValueTextSize(15);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            chart.setData(data);

        }
    }
}


class Training{
    int id;
    int day;
    int percent;
    String date;

    public Training(int id, int day, int percent, String date) {
        this.id = id;
        this.day = day;
        this.percent = percent;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getPercent() {
        return percent;
    }

    public String getDate() {
        return date;
    }
}
