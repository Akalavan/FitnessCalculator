package com.example.akalavan.myapplication.training;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akalavan.myapplication.MainActivity;
import com.example.akalavan.myapplication.People;
import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.Target;
import com.example.akalavan.myapplication.db.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Akalavan on 09.03.2019.
 */

public class TrainingLayout extends AppCompatActivity {
    private TextView textView;
    private LinearLayout framesContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        Target target = null;
        String day = "";
        ArrayList<Exercise> exerciseArray = new ArrayList<>();
      //  Log.d("myLogs", );
        if(arguments != null){
            day = arguments.get("day").toString();
            target = arguments.getParcelable(Target.class.getSimpleName());
            for (Exercise exercise:
                    target.getTrainingPlan().getExercises()) {
                Exercise exercise1 = exercise;
                Log.d("myLogs", "VALUE: " + exercise1);
                if (exercise1.getDay().equals(day))
                    exerciseArray.add(exercise1);
            }

        }
        setContentView(R.layout.training);
        framesContainer = (LinearLayout) findViewById(R.id.trL);
        //textView = findViewById(R.id.textViewDescription);
        Log.d("myLogs", "size: " + exerciseArray.size());
        for (int i = 0; i < exerciseArray.size(); i++) {
            ExerciseLayout frame = new ExerciseLayout(getApplicationContext());
            if (!(exerciseArray.get(i).getWeight() == null))
                frame.setTextExercise("Упражнение: " + exerciseArray.get(i).getName() + ". Вес: " + exerciseArray.get(i).getWeight());
            else frame.setTextExercise("Упражнение: " + exerciseArray.get(i).getName());
            frame.setTextApproaches(exerciseArray.get(i).getsNumberApproaches());
            if (exerciseArray.get(i).getsNumberExecutions() != null)
                frame.setTextExecutions(exerciseArray.get(i).getsNumberExecutions());
            if (exerciseArray.get(i).getDescription() != null)
                frame.setTextDescription("Описание: " + exerciseArray.get(i).getDescription());
            framesContainer.addView(frame);
        }
        Button enter = new Button(getApplicationContext());
        enter.setText("Принять");
        final String finalDay = day;
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int percent = 0;
                int allExecutions = 0;
                int realExecutions = 0;
                for (int i = 0; i < framesContainer.getChildCount(); i++){
                    Log.d("myLogs", "enter onClick " + framesContainer.getChildAt(i).getClass().getName());
                    if(!framesContainer.getChildAt(i).getClass().getName().equals(Button.class.getName())) {
                        ExerciseLayout layout = (ExerciseLayout) framesContainer.getChildAt(i);
                        allExecutions += layout.getExecutions();
                        realExecutions += layout.getEditTextAppproaches();

                    }
                }
                percent = realExecutions * 100 / allExecutions;
                Log.d("myLogs", "enter onClick " + percent);

                DataBaseHelper databaseHelper = MainActivity.getDatabaseHelper();
                SQLiteDatabase db = databaseHelper.open();
                Cursor userCursor;
                ContentValues cv;

                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Log.d("DataBaseHelper", "recordTraining: " + formatForDateNow.format(dateNow) + "; date");

                userCursor =  db.rawQuery("select * from "+ DataBaseHelper.TABLE_TRAINING, null);
                Log.d("DataBaseHelper", String.valueOf(userCursor.getCount()));
                if (userCursor.getCount() == 0) {

                    cv = new ContentValues();
                    cv.put(DataBaseHelper.COLUMN_ID_DAY_TRAINING, Integer.parseInt(finalDay));
                    cv.put(DataBaseHelper.COLUMN_USER_TRAINING, 1);
                    cv.put(DataBaseHelper.COLUMN_DATE_TRAINING, formatForDateNow.format(dateNow));
                    cv.put(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING, percent);

                    db.insert(DataBaseHelper.TABLE_TRAINING, null, cv);
                } else {
                    String selection = "date = ?";
                    String[] selectionArgs = new String[]{ formatForDateNow.format(dateNow) };
                    userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, selectionArgs, null, null, null);
                    if (userCursor != null) {
                        if (userCursor.moveToFirst()) {
                            int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_TRAINING);

                            cv = new ContentValues();
                            cv.put(DataBaseHelper.COLUMN_ID_DAY_TRAINING, Integer.parseInt(finalDay));
                            cv.put(DataBaseHelper.COLUMN_USER_TRAINING, 1);
                            cv.put(DataBaseHelper.COLUMN_DATE_TRAINING, formatForDateNow.format(dateNow));
                            cv.put(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING, percent);

                            int updCount = db.update(DataBaseHelper.TABLE_TRAINING, cv, "_id = ?",
                                    new String[]{String.valueOf(userCursor.getInt(idIndex))});
                            Log.d("DataBaseHelper", "updated rows count = " + updCount);
                        } else {
                            cv = new ContentValues();
                            cv.put(DataBaseHelper.COLUMN_ID_DAY_TRAINING, Integer.parseInt(finalDay));
                            cv.put(DataBaseHelper.COLUMN_USER_TRAINING, 1);
                            cv.put(DataBaseHelper.COLUMN_DATE_TRAINING, formatForDateNow.format(dateNow));
                            cv.put(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING, percent);

                            db.insert(DataBaseHelper.TABLE_TRAINING, null, cv);
                        }
                        userCursor.close();
                    } else Log.d("DataBaseHelper", "Cursor is null");
                }
                onBackPressed();
                //userCursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy);
            }
        });
        framesContainer.addView(enter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void buttonClick(View view) {
        int id = view.getId();
        int age, growth, weight, targetWeight;

    }

}
