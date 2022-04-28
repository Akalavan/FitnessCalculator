package com.example.akalavan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akalavan.myapplication.training.TrainingPlan;

/**
 * Created by Akalavan on 23.01.2019.
 */

public class TargetLayout extends AppCompatActivity {

    private RadioGroup radioGroupGender, radioGroupStep;
    private Spinner spinner, spinnerSelect;
    private int gender, active, actionSelection;
    private double step;
    private EditText age;
    private EditText growth;
    private EditText weight;
    private EditText targetWeight;
    private TrainingPlan trainingPlan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_layout);
        radioGroupGender = findViewById(R.id.gender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioMale: gender = 0;
                        break;
                    case R.id.radioWomen: gender = 1;
                        break;
                }
                Log.d("DataBaseHelper", gender + " radioGroupGender");
            }
        });

        radioGroupStep = findViewById(R.id.radioGroupStep);
        radioGroupStep.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.firstStep: step = 0.3;
                        break;
                    case R.id.secondStep: step = 0.4;
                        break;
                    case R.id.thirdStep: step = 0.5;
                        break;
                    case R.id.fourStep: step = 0.6;
                        break;
                    case R.id.fiveStep: step = 0.7;
                        break;
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DataBaseHelper", String.valueOf(position) + " ACTIVE");
                switch (position) {
                    case 0: active = 0;
                        break;
                    case 1: active = 1;
                        break;
                    case 2: active = 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerSelect = (Spinner) findViewById(R.id.spinnerSelect);
        spinnerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DataBaseHelper", String.valueOf(id) + " SELECT");
                actionSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        age = findViewById(R.id.editAge);
        growth = findViewById(R.id.editGrowth);
        weight = findViewById(R.id.editWeight);
        targetWeight = findViewById(R.id.editTargetWeight);
    }


    public void buttonClick(View view) {
        boolean b = true;
        int id = view.getId();
        int age, growth, weight, targetWeight;
        weight = Integer.parseInt(this.weight.getText().toString());
        targetWeight = Integer.parseInt(this.targetWeight.getText().toString());
        age = Integer.parseInt(this.age.getText().toString());
        growth = Integer.parseInt(this.growth.getText().toString());
        if (age > 0 && weight > 0 && targetWeight > 0 && growth > 0)
            if (actionSelection == 0) {
                if (weight < targetWeight) b = false;
            } else if (actionSelection == 1) {
                if (weight == targetWeight) b = false;
            } else if (actionSelection == 2) {
                if (weight > targetWeight) b = false;
            }
        if (!b)
            switch (id) {
                case R.id.buttonOk: {
                    People user = new People(gender, age, growth, weight, targetWeight);
                    trainingPlan = new TrainingPlan(gender, actionSelection);
                    Target target = new Target(user, targetWeight, active, gender, actionSelection, trainingPlan);
                    Intent date = new Intent();
                    date.putExtra(Target.class.getSimpleName(), target);
                    setResult(RESULT_OK, date);
                    finish();
                }
                default:
                    setResult(RESULT_CANCELED);
                    super.onBackPressed();
                    break;
            }
        else Toast.makeText(getApplicationContext(), "Неправильно введены значения" ,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
