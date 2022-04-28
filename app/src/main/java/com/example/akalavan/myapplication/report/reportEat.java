package com.example.akalavan.myapplication.report;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akalavan.myapplication.MainActivity;
import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.db.DataBaseHelper;
import com.example.akalavan.myapplication.eating.Eating;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.LineView;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.LineChartView;

public class reportEat extends AppCompatActivity {
    CalendarView calendarView;
    TextView textP, textF, textCa, textCar, textW;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rep_eat);
        textP = findViewById(R.id.textReportB);
        textF = findViewById(R.id.textReportF);
        textCa = findViewById(R.id.textReportCa);
        textCar = findViewById(R.id.textReportC);
        textW = findViewById(R.id.textReportW);
        calendarView = findViewById(R.id.calendarView2);
        //Настраиваем слушателя смены даты:
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            // Описываем метод выбора даты в календаре:
            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {
                DataBaseHelper databaseHelper = MainActivity.getDatabaseHelper();
                SQLiteDatabase db = databaseHelper.open();
                // При выборе любой даты отображаем Toast сообщение с данными о выбранной дате (Год, Месяц, День):
                month++;
                Toast.makeText(getApplicationContext(),
                        "Год: " + year + "\n" +
                                "Месяц: " + month + "\n" +
                                "День: " + dayOfMonth,
                        Toast.LENGTH_SHORT).show();
             //   Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

                Cursor userCursor;
                ContentValues cv;
                String selection = "date = ?";
                String[] selectionArgs = new String[0];
                try {
                    selectionArgs = new String[]{ formatForDateNow.format(formatForDateNow.parse(year + "-" + month + "-" + dayOfMonth)) };
                } catch (ParseException e) {
                    Log.d("DataBaseHelper", "report eat: ParseException");
                }
                // userCursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy);
                userCursor = db.query(DataBaseHelper.TABLE_CURRENT, null, selection, selectionArgs, null, null, null);
                if (userCursor != null) {
                    if (userCursor.moveToFirst()) {
                        String str;
                        int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_CURRENT);
                        int idProtein = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_PROTEIN_CURRENT);
                        int idFat = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_FAT_CURRENT);
                        int idCalorie = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_CALORIE_CURRENT);
                        int idCarbohydrate = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_CARBOHYDRATE_CURRENT);
                        int idWater = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_WATER_CURRENT);
                        do {
                            textP.setText("Белков: " + String.valueOf(userCursor.getDouble(idProtein)));
                            textF.setText("Жиров: " + String.valueOf(userCursor.getDouble(idFat)));
                            textCa.setText("Калорий: " + String.valueOf(userCursor.getDouble(idCalorie)));
                            textCar.setText("Углеводов: " +String.valueOf(userCursor.getDouble(idCarbohydrate)));
                            textW.setText("Воды: " +String.valueOf(userCursor.getDouble(idWater)));
                            str = "";
                          //  i = 0;
                            for (String cn : userCursor.getColumnNames()) {
                                str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                            }
                            Log.d("DataBaseHelper", "report eat:" + str);
                        } while (userCursor.moveToNext());
                    } else {
                        textP.setText("Белков: 0");
                        textF.setText("Жиров: 0");
                        textCa.setText("Калорий: 0");
                        textCar.setText("Углеводов: 0");
                        textW.setText("Воды: 0");
                    }
                    userCursor.close();
                } else Log.d("DataBaseHelper", "report eat: Cursor is null");
//                ArrayList<String> strList = new ArrayList<>();
//                ArrayList<Integer> dataList = new ArrayList<>();
//                strList.add("Белки");
//                strList.add("Жиры");
//                strList.add("Углеводы");
//                strList.add("Вода");
//                strList.add("Калории");
//                dataList.add(20);
//                dataList.add(40);
//                dataList.add(60);
//                dataList.add(150);
//                dataList.add(120);
//                BarView barView = (BarView)findViewById(R.id.bar_view);
//                barView.setScaleX(0.8f);
//                barView.setBottomTextList(strList);
//                barView.setDataList(dataList,150);
            }});


        Log.d("DataBaseHelper CURRENT", String.valueOf(calendarView.getDate()));
    }
}
