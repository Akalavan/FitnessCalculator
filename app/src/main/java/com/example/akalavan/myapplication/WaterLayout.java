package com.example.akalavan.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaterLayout extends LinearLayout {
    private TextView textViewWater;
    private int water, idE;

    public WaterLayout(int idE, Context context) {
        super(context);
        this.idE = idE;
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.water, this);

        textViewWater = findViewById(R.id.textViewWater);

        setSetting();
    }

    public void setSetting() {
        textViewWater.setTextColor(Color.BLACK);
    }

    public String getTextViewWater() {
        return (String) textViewWater.getText();
    }

    public void setTextViewWater(String textViewWater) {
        water = Integer.parseInt(textViewWater);
        this.textViewWater.setText("Воды: " + textViewWater);
    }

    public int getWater() {
        return water;
    }

    public int getIdE() {
        return idE;
    }
}
