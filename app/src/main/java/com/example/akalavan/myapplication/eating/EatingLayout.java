package com.example.akalavan.myapplication.eating;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akalavan.myapplication.R;

/**
 * Created by Akalavan on 24.03.2019.
 */

public class EatingLayout extends LinearLayout {
    private TextView textNameEating, textAnnotation, textProteinEating, textFatEating, textCarbohydrateEating,
            textCalorieEating;
    private Eating eating;
    public EatingLayout(Context context) {
        super(context);
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.eating, this);
        textNameEating = findViewById(R.id.textNameEating);
        textAnnotation = findViewById(R.id.textAnnotation);
        textProteinEating = findViewById(R.id.textProteinEating);
        textFatEating = findViewById(R.id.textFatEating);
        textCarbohydrateEating = findViewById(R.id.textCarbohydrateEating);
        textCalorieEating = findViewById(R.id.textCalorieEating);
        setSetting();
    }

    public void setValue() {
        if (eating != null) {
            textNameEating.setText("Название продукта: " + eating.getName());
            textAnnotation.setText("В 100гр. продукта:");
            textProteinEating.setText("Белков: " + eating.getProtein());
            textFatEating.setText("Жиров: " + eating.getFat());
            textCarbohydrateEating.setText("Углеводов: " + eating.getCarbohydrate());
            textCalorieEating.setText("ккал: " + eating.getCalorie());
        }
    }

    public void setSetting(){
        textNameEating.setTextColor(Color.BLACK);
        textAnnotation.setTextColor(Color.BLACK);
        textProteinEating.setTextColor(Color.BLACK);
        textFatEating.setTextColor(Color.BLACK);
        textCarbohydrateEating.setTextColor(Color.BLACK);
        textCalorieEating.setTextColor(Color.BLACK);
    }

    public void setTextNameEating(String textNameEating) {
        this.textNameEating.setText(textNameEating);
    }

    public void setTextAnnotation(TextView textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    public void setTextProteinEating(TextView textProteinEating) {
        this.textProteinEating = textProteinEating;
    }

    public void setTextFatEating(TextView textFatEating) {
        this.textFatEating = textFatEating;
    }

    public void setTextCarbohydrateEating(TextView textCarbohydrateEating) {
        this.textCarbohydrateEating = textCarbohydrateEating;
    }

    public void setTextCalorieEating(TextView textCalorieEating) {
        this.textCalorieEating = textCalorieEating;
    }

    public void setTextNameEating(TextView textNameEating) {
        this.textNameEating = textNameEating;
    }

    public void setEating(Eating eating) {
        this.eating = eating;
    }

    public Eating getEating() {
        return eating;
    }
}
