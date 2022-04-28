package com.example.akalavan.myapplication.eating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.training.ExerciseLayout;

/**
 * Created by Akalavan on 24.03.2019.
 */

public class AddEatingLayout extends AppCompatActivity {
    private LinearLayout framesContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_eating);
        framesContainer = (LinearLayout) findViewById(R.id.addEating);
        EatingLayout frame = new EatingLayout(getApplicationContext());
        framesContainer.addView(frame);
    }
}
