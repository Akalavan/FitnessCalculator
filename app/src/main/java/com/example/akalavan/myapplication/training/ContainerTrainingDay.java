package com.example.akalavan.myapplication.training;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.akalavan.myapplication.MainActivity;
import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.Target;
import com.example.akalavan.myapplication.TargetLayout;
import com.example.akalavan.myapplication.db.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class ContainerTrainingDay extends AppCompatActivity {
    private LinearLayout framesContainer;
    Set<String> set;
    final ArrayList<String> exerciseArray = new ArrayList<>();
    Target target = null;

    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = MainActivity.getDatabaseHelper();
        db = databaseHelper.open();
        Bundle arguments = getIntent().getExtras();
        Log.d("myLogs", arguments.getParcelable(Target.class.getSimpleName()).toString());
        if(arguments != null){
            target = arguments.getParcelable(Target.class.getSimpleName());
            for (Exercise exercise:
                    target.getTrainingPlan().getExercises()) {
                Exercise exercise1 = exercise;
                Log.d("myLogs", "VALUE: " + exercise1);
                exerciseArray.add(exercise1.getDay());

              // exerciseArray.add(exercise1);
            }

        }

        setContentView(R.layout.container_trainig_day_layout);
        framesContainer = (LinearLayout) findViewById(R.id.trDL);
        //textView = findViewById(R.id.textViewDescription);
        init();
    }

    void init(){
        GregorianCalendar startCal = new GregorianCalendar( );
        int day = startCal.get( Calendar.DAY_OF_WEEK );
        startCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        GregorianCalendar endCal = new GregorianCalendar( );
        endCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endCal.add(Calendar.DAY_OF_WEEK, 6);

       // Calendar stay =
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("DataBaseHelper", "init start " + formatForDateNow.format(startCal.getTime()));
        Log.d("DataBaseHelper", "init end " + formatForDateNow.format(endCal.getTime()));
        set = new LinkedHashSet<>(exerciseArray);
        Cursor userCursor;
        ContentValues cv;
        //String selection = "id_day = ?";
        Iterator iterator = set.iterator();
        final ArrayList<Exercise> exercises = new ArrayList<>();
        while (iterator.hasNext()){
            Log.d("myLogs", "size: " + iterator.hasNext());
            final TrainingDayLayout frame = new TrainingDayLayout(getApplicationContext());
            String s = (String) iterator.next();
            String selection = "date >= ? AND id_day = ?";
            String[] selectionArgs = new String[]{ formatForDateNow.format(startCal.getTime()), s };
            userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, selectionArgs, null, null, null);
            //userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, new String[]{s} , null, null, null);
            frame.setTextDay("День " + s);
            if (!userCursor.moveToFirst())
                frame.setTextPercent("Выполнено на 0%");
            else {
                int idPercent = userCursor.getColumnIndex(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING);
                userCursor.moveToLast();
                frame.setTextPercent("Выполнено на " + userCursor.getInt(idPercent) + "%");
                userCursor.close();
            }
            final Target finalTarget = target;
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openExercise(frame.getTextDay().split(" ")[1], finalTarget);
                }
            });
            framesContainer.addView(frame);
        }
//        for (int i = 0; i < set.size(); i++) {
//            TrainingDayLayout frame = new TrainingDayLayout(getApplicationContext());
//            frame.setTextDay(set.g);
//            frame.setTextPercent("0%");
//            set.remove(set.first());
//            framesContainer.addView(frame);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor userCursor;
        ContentValues cv;
       // String selection = "id_day = ?";
        String day = "";
        // открываем подключение
        GregorianCalendar startCal = new GregorianCalendar( );
        startCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Calendar stay
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("DataBaseHelper", "init start " + formatForDateNow.format(startCal.getTime()));
        for (int i = 0; i < framesContainer.getChildCount(); i++){

            TrainingDayLayout frame = (TrainingDayLayout) framesContainer.getChildAt(i);
            Log.d("myLogs", "onResume s = " + frame.getTextDay().split(" ")[1]);
            day = frame.getTextDay().split(" ")[1];
            String selection = "date >= ? AND id_day = ?";
            String[] selectionArgs = new String[]{ formatForDateNow.format(startCal.getTime()), day };
            userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, selectionArgs, null, null, null);

            if (!userCursor.moveToFirst())
                frame.setTextPercent("Выполнено на 0%");
            else {
                int idPercent = userCursor.getColumnIndex(DataBaseHelper.COLUMN_PERCENTAGE_COMPLETION_TRAINING);
                userCursor.moveToLast();
                frame.setTextPercent("Выполнено на " + userCursor.getInt(idPercent) + "%");
                userCursor.close();
            }

        }

    }


    void openExercise(String day, Target target){
        Log.d("myLogs", "openExercise: " + day);
        Intent intent = new Intent(this, TrainingLayout.class);
        intent.putExtra("day", day);
        intent.putExtra(Target.class.getSimpleName(), target);
        startActivity(intent);
    }

}
