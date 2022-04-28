package com.example.akalavan.myapplication.training;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akalavan.myapplication.R;

/**
 * Created by Akalavan on 09.03.2019.
 */

public class ExerciseLayout extends LinearLayout {
    private TextView textExercise, textApproaches, textExecutions, textDescription, editTextAppproaches;
    private Button buttonPlus, buttonMinus;
  //  private EditText editTextAppproaches;
    public ExerciseLayout(Context context) {
        super(context);
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.exercise, this);
        textExercise = findViewById(R.id.textExercise);
        textApproaches = findViewById(R.id.textApproaches);
        textExecutions = findViewById(R.id.textExecutions);
        textDescription = findViewById(R.id.textDescription);
        editTextAppproaches = findViewById(R.id.editTextAppproaches);
        editTextAppproaches.setText("0");
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus.setOnClickListener(buttonListener);
        buttonMinus.setOnClickListener(buttonListener);
        setSetting();
    }

    private final OnClickListener buttonListener = new OnClickListener() {
        public void onClick(View view) {
            int id = view.getId();
            String s = "";
            switch (id) {
                case R.id.buttonPlus:
                    s = String.valueOf(editTextAppproaches.getText());
                    editTextAppproaches.setText(String.valueOf(Integer.parseInt(s) + 1));
                    break;
                case R.id.buttonMinus:
                    s = String.valueOf(editTextAppproaches.getText());
                    editTextAppproaches.setText(String.valueOf(Integer.parseInt(s) - 1));
                    break;
            }
        }
    };

    public void setSetting(){
        textExercise.setTextColor(Color.BLACK);
        textApproaches.setTextColor(Color.BLACK);
        textExecutions.setTextColor(Color.BLACK);
        textDescription.setTextColor(Color.BLACK);
        editTextAppproaches.setTextColor(Color.BLACK);
        editTextAppproaches.setTextColor(Color.BLACK);
        textExecutions.setTextColor(Color.BLACK);
    }

    public void setTextExercise(String textExercise) {
        this.textExercise.setText(textExercise);
    }

    public void setTextApproaches(String textApproaches) {
        this.textApproaches.setText(textApproaches);
    }

    public void setTextExecutions(String textExecutions) {
        this.textExecutions.setText(textExecutions);
    }

    public void setTextDescription(String textDescription) {
        this.textDescription.setText(textDescription);
    }

    public int getEditTextAppproaches() {
        return Integer.parseInt(String.valueOf(editTextAppproaches.getText()));
    }

    public int getExecutions(){
        return Integer.parseInt(String.valueOf(textExecutions.getText()).split(" ")[0]);
    }
}

