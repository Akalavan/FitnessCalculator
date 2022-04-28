package com.example.akalavan.myapplication.training;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akalavan.myapplication.R;

public class TrainingDayLayout extends LinearLayout {
    private TextView textDay, textPercent;
    public TrainingDayLayout(Context context) {
        super(context);
        initComponent();
        this.setPadding(0, 0, 0, 20);
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.training_day_layout, this);
        textDay = findViewById(R.id.textViewDay);
        textPercent = findViewById(R.id.textViewPercent);
        setSetting();
    }

    public void setSetting(){
        textDay.setTextColor(Color.BLACK);
        textPercent.setTextColor(Color.BLACK);

    }

    public void setTextDay(String textDay) {
        this.textDay.setText(textDay);
    }

    public void setTextPercent(String textPercent) {
        this.textPercent.setText(textPercent);
    }

    public String getTextDay() {
        return (String) textDay.getText();
    }
}
