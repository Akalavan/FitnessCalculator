package com.example.akalavan.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akalavan.myapplication.db.DataBaseHelper;
import com.example.akalavan.myapplication.eating.AddEatingLayout;
import com.example.akalavan.myapplication.eating.Eating;
import com.example.akalavan.myapplication.eating.EatingLayout;
import com.example.akalavan.myapplication.eating.EatingList;
import com.example.akalavan.myapplication.eating.ItemForEatingList;
import com.example.akalavan.myapplication.report.reportEat;
import com.example.akalavan.myapplication.report.reportTraining;
import com.example.akalavan.myapplication.training.ContainerTrainingDay;
import com.example.akalavan.myapplication.training.Exercise;
import com.example.akalavan.myapplication.training.TrainingDayLayout;
import com.example.akalavan.myapplication.training.TrainingLayout;
import com.example.akalavan.myapplication.training.TrainingPlan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    TrainingPlan trainingPlan;
    Context context;
    private ArrayList<Eating> eatings = new ArrayList<>();
    Eating water;
    private TextView textViewWater, textViewFat, textViewCarbohydrate, textViewProtein,
            textViewCalorie, textViewProgressWater, textViewProgressProtein, textViewProgressFat,
            textViewProgressCalorie, textViewProgressCarbohydrate;
    private FloatingActionButton floatingActionButtonWater, floatingActionButtonEat;
    private ProgressBar progressBarWater, progressBarProtein, progressBarFat, progressBarCalorie,
            progressBarCarbohydrate;
    static Target target;
    private LinearLayout framesContainer;
    private static  final int REQUEST_ACCESS_TYPE = 1;
    static DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        framesContainer = (LinearLayout) findViewById(R.id.containerEat);
        context = MainActivity.this;
        databaseHelper = new DataBaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        init();
        readBJU();
        readDish();
        cv = new ContentValues();
        readEating();
    }

    @Override
    public void onResume() {
        super.onResume();

        userCursor = db.query(DataBaseHelper.TABLE_CURRENT, null, null, null, null, null, null);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("DataBaseHelper CURRENT", str);
                } while (userCursor.moveToNext());
            }
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");
        // открываем подключение

        //db.execSQL("INSERT INTO "+ DataBaseHelper.TABLE_TRAINING_PROGRAM +" (" + DataBaseHelper.COLUMN_NAME_TRAINING_PROGRAM
         //       + ", " + DataBaseHelper.COLUMN_DESCRIPTION_TRAINING_PROGRAM  + ") VALUES ('День груди', 'Тренируем ноги');");
//        cv = new ContentValues();
//        cv.put(DataBaseHelper.COLUMN_ID_USER_EATING, 1);
//        cv.put(DataBaseHelper.COLUMN_ID_DISH_EATING, 1);
//        Date dateNow = new Date();
//        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
//     //   Log.d("DataBaseHelper", formatForDateNow.format(dateNow));
//        cv.put(DataBaseHelper.COLUMN_DATE_EATING, formatForDateNow.format(dateNow));
//        cv.put(DataBaseHelper.COLUMN_WEIGHT_EATING, 13);
//        db.insert(DataBaseHelper.TABLE_EATING, null, cv);
       // db.delete(DataBaseHelper.TABLE_TRAINING_PROGRAM, null, null);
        userCursor =  db.rawQuery("select * from "+ DataBaseHelper.TABLE_TRAINING_PROGRAM, null);
        Log.d("DataBaseHelper", "Найдено элементов: " + String.valueOf(userCursor.getCount()));

//
//                cv = new ContentValues();
//        cv.put(DataBaseHelper.COLUMN_ID_EATING_DISH, 4);
//        cv.put(DataBaseHelper.COLUMN_NAME_DISH, "Вода");
//     //   Log.d("DataBaseHelper", formatForDateNow.format(dateNow));
//        cv.put(DataBaseHelper.COLUMN_PROTEIN_DISH, 0);
//        cv.put(DataBaseHelper.COLUMN_FAT_DISH, 0);
//        cv.put(DataBaseHelper.COLUMN_CARBOHYDRATE_DISH, 0);
//        cv.put(DataBaseHelper.COLUMN_CALORIE_DISH, 0);
//        cv.put(DataBaseHelper.COLUMN_WEIGHT_DISH, 0);
//        db.insert(DataBaseHelper.TABLE_DISH, null, cv);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, null, null, null, null, null);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                        if (cn.equals("date")) {

                            try {
                                date = formatForDateNow.parse(userCursor.getString(userCursor.getColumnIndex(cn)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("DataBaseHelper", "TABLE_TRAINING " + formatForDateNow.format(date));
                        }
                    }
                    Log.d("DataBaseHelper ","TABLE_TRAINING: " + str);
                } while (userCursor.moveToNext());
            }
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");

        GregorianCalendar startCal = new GregorianCalendar( );
        int day = startCal.get( Calendar.DAY_OF_WEEK );
        startCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        GregorianCalendar endCal = new GregorianCalendar( );
        endCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endCal.add(Calendar.DAY_OF_WEEK, 6);

        // Calendar stay =

     //   Log.d("DataBaseHelper", "init start " + formatForDateNow.format(startCal.getTime()));
     //   Log.d("DataBaseHelper", "init end " + formatForDateNow.format(endCal.getTime()));
        String selection = "date >= ? AND id_day = ?";
        String[] selectionArgs = new String[]{ formatForDateNow.format(startCal.getTime()), "1" };
        userCursor = db.query(DataBaseHelper.TABLE_TRAINING, null, selection, selectionArgs, null, null, null);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("DataBaseHelper ","TABLE_TRAINING date >= " + formatForDateNow.format(startCal.getTime()) + " : " + str);
                } while (userCursor.moveToNext());
            } else Log.d("DataBaseHelper", "TABLE_TRAINING: 0");
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");

        //получаем данные из бд в виде курсора

        // определяем, какие столбцы из курсора будут выводиться в ListView
//        String[] headers = new String[] {DataBaseHelper.TABLE_DISH, DataBaseHelper.COLUMN_ID_DISH,
//                DataBaseHelper.COLUMN_ID_EATING_DISH, DataBaseHelper.COLUMN_NAME_DISH,
//                DataBaseHelper.COLUMN_PROTEIN_DISH, DataBaseHelper.COLUMN_FAT_DISH,
//                DataBaseHelper.COLUMN_CARBOHYDRATE_DISH, DataBaseHelper.COLUMN_CALORIE_DISH,
//                DataBaseHelper.COLUMN_WEIGHT_DISH,};
//        // создаем адаптер, передаем в него курсор
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
//        userList.setAdapter(userAdapter);
    }

    void readEating(){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

        Cursor userCursor;
        ContentValues cv;
        String selection = "date = ?";
        String orderBy = "day";
        String table = "training_day as TD inner join exercise as EX on TD.id_exercise = EX._id";
        String[] columns = {"EX.name as Name", "EX.number_approaches as approaches", "EX.number_executions as executions", "EX.comment as comment", "TD.day as day"};
        String[] selectionArgs = new String[]{ formatForDateNow.format(dateNow) };
       // userCursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy);
        userCursor = db.query(DataBaseHelper.TABLE_EATING, null, selection, selectionArgs, null, null, null);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                int i;
                int idWeight = userCursor.getColumnIndex(DataBaseHelper.COLUMN_WEIGHT_EATING);
                do {
                    str = "";
                    i = 0;
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                        if (cn.equals("id_dish"))
                        for (Eating eating : eatings) {
                            if (eating.getId() == userCursor.getInt(userCursor.getColumnIndex(cn))) {
                                if (eating.getName().equals("Вода"))
                                    addWater(userCursor.getInt(idWeight));
                                else
                                    addEat(eating, String.valueOf(userCursor.getInt(idWeight)));
                                readProgressBJU(eating, userCursor.getInt(idWeight));
                            }
                        }
                        if (cn.equals("date")) {
                            Date date = null;
                            try {
                                date = formatForDateNow.parse(userCursor.getString(userCursor.getColumnIndex(cn)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("DataBaseHelper", "readEating " + String.valueOf(date));
                        }
                    }
                    Log.d("DataBaseHelper", str);
                } while (userCursor.moveToNext());
            }
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");
    }

    private void readDish() {
//        eatings.add(new Eating("Гречка", 13, 20, 0, 40, 50));
//        eatings.add(new Eating("Перловка", 15, 17, 0, 50, 80));
//        eatings.add(new Eating("Овсянка", 12, 22, 0, 30, 70));
        userCursor =  db.query(DataBaseHelper.TABLE_DISH, null, null, null, null, null, null);
        Log.d("DataBaseHelper", "readDish " + userCursor.getCount());
        if (userCursor.moveToFirst()) {
            int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_DISH);
            int idEating = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_EATING_DISH);
            int idName = userCursor.getColumnIndex(DataBaseHelper.COLUMN_NAME_DISH);
            int idProtein = userCursor.getColumnIndex(DataBaseHelper.COLUMN_PROTEIN_DISH);
            int idFat = userCursor.getColumnIndex(DataBaseHelper.COLUMN_FAT_DISH);
            int idCarbohydrate = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CARBOHYDRATE_DISH);
            int idCalorie = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CALORIE_DISH);
            int idWeight = userCursor.getColumnIndex(DataBaseHelper.COLUMN_WEIGHT_DISH);
            do {
                eatings.add(new Eating(userCursor.getInt(idIndex), userCursor.getString(idName), userCursor.getInt(idProtein),
                        userCursor.getInt(idFat), 0, userCursor.getInt(idCarbohydrate),  userCursor.getInt(idCalorie)));
                if (userCursor.getString(idName).equals("Вода")){
                    water = new Eating(userCursor.getInt(idIndex), userCursor.getString(idName), userCursor.getInt(idProtein),
                            userCursor.getInt(idFat), 0, userCursor.getInt(idCarbohydrate),  userCursor.getInt(idCalorie));
                }
            } while (userCursor.moveToNext());
        }
    }

    void readBJU(){
        Log.d("DataBaseHelper", "readBJU");
        userCursor =  db.query(DataBaseHelper.TABLE_USER, null, null, null, null, null, null);
        Log.d("DataBaseHelper", "readBJU " + userCursor.getCount());
        if (userCursor.moveToFirst()) {
            int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_USER);
            int idProtein = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_PROTEIN_USER);
            int idFat = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_FAT_USER);
            int idWater = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_WATER_USER);
            int idtCarbohydrate = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_CARBOHYDRATE_USER);
            int idCalorie = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_CALORIE_USER);
            int idProgram = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_PROGRAM_USER);
            int idGender= userCursor.getColumnIndex(DataBaseHelper.COLUMN_GENDER_USER);
            int idAge = userCursor.getColumnIndex(DataBaseHelper.COLUMN_AGE_USER);
            int idGrowth = userCursor.getColumnIndex(DataBaseHelper.COLUMN_GROWTH_USER);
            int idWeight = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CURRENT_WEIGHT_USER);
            int idTargetWeight = userCursor.getColumnIndex(DataBaseHelper.COLUMN_TARGET_WEIGHT_USER);
            int idActive = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ACTIVE_USER);
            do {
                People people = new People(userCursor.getInt(idGender), userCursor.getInt(idAge), userCursor.getInt(idGrowth),
                        userCursor.getInt(idWeight), userCursor.getInt(idTargetWeight));
                TrainingPlan trainingPlan = new TrainingPlan(userCursor.getInt(idGender), userCursor.getInt(idProgram));
                target = new Target(people, userCursor.getInt(idTargetWeight), userCursor.getInt(idActive), 1, 1, trainingPlan);
                target.setTargetProtein(userCursor.getInt(idProtein));
                target.setTargetFat(userCursor.getInt(idFat));
                target.setTargetWater(userCursor.getInt(idWater));
                target.setTargetCarbohydrate(userCursor.getInt(idtCarbohydrate));
                target.setTargetCalorie(userCursor.getInt(idCalorie));
                setBJU();
            } while (userCursor.moveToNext());
        }

    }

    void readProgressBJU(Eating eating, int weight){
        double weightEat = weight / 100.0;
        int protein = (int) Math.round(eating.getProtein() * weightEat);
        int fat = (int) Math.round(eating.getFat() * weightEat);
        if (eating.getName().equals("Вода")) {
            target.setCurrentWater(target.getCurrentWater() + weight);
            textViewProgressWater.setText(target.getCurrentWater() + "");
            progressBarWater.setProgress(target.getCurrentWater());
        }
        Log.d("DataBaseHelper", "readProgressBJU water: " + water);
        int carbohydrate = (int) Math.round(eating.getCarbohydrate() * weightEat);
        int calorie = (int) Math.round(eating.getCalorie() * weightEat);

        target.setCurrentProtein(target.getCurrentProtein() + protein);
        textViewProgressProtein.setText(target.getCurrentProtein() + "");
        progressBarProtein.setProgress(target.getCurrentProtein());

        target.setCurrentFat(target.getCurrentFat() + fat);
        textViewProgressFat.setText(target.getCurrentFat() + "");
        progressBarFat.setProgress(target.getCurrentFat());

        target.setCurrentCarbohydrate(target.getCurrentCarbohydrate() + carbohydrate);
        textViewProgressCarbohydrate.setText(target.getCurrentCarbohydrate() + "");
        progressBarCarbohydrate.setProgress(target.getCurrentCarbohydrate());

        target.setCurrentCalorie(target.getCurrentCalorie() + calorie);
        textViewProgressCalorie.setText(target.getCurrentCalorie() + "");
        progressBarCalorie.setProgress(target.getCurrentCalorie());
    }

    private void addEat(final Eating eating, final String weight){
        Log.d("DataBaseHelper", "addEat: ");

        double weightEat = Double.parseDouble(weight) / 100.0;
        int protein = (int) Math.round(eating.getProtein() * weightEat);
        int fat = (int) Math.round(eating.getFat() * weightEat);
        int carbohydrate = (int) Math.round(eating.getCarbohydrate() * weightEat);
        int calorie = (int) Math.round(eating.getCalorie() * weightEat);

        final EatLayout frame = new EatLayout(eating.getId(), getApplicationContext(), eating);
        initFrame(frame);
        frame.setTextViewNameEat(eating.getName());
        frame.setTextViewWeight(weight);
        frame.setTextViewCalorie(String.valueOf(calorie));
        frame.setTextViewFat(String.valueOf(fat));
        frame.setTextViewCarbohydrate(String.valueOf(carbohydrate));
        frame.setTextViewProtein(String.valueOf(protein));
        frame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder ad;
                String title = "Выбор есть всегда";
                String message = "Удалить пищу";
                String button1String = "Да";
                String button2String = "Нет";
                Log.d("DataBaseHelper", "onLongClick");
                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        target.setCurrentProtein(target.getCurrentProtein() - frame.getProtein());
                        textViewProgressProtein.setText(target.getCurrentProtein() + "");
                        progressBarProtein.setProgress(target.getCurrentProtein());

                        target.setCurrentFat(target.getCurrentFat() - frame.getFat());
                        textViewProgressFat.setText(target.getCurrentFat() + "");
                        progressBarFat.setProgress(target.getCurrentFat());

                        target.setCurrentCarbohydrate(target.getCurrentCarbohydrate() - frame.getCarbohydrate());
                        textViewProgressCarbohydrate.setText(target.getCurrentCarbohydrate() + "");
                        progressBarCarbohydrate.setProgress(target.getCurrentCarbohydrate());

                        target.setCurrentCalorie(target.getCurrentCalorie() - frame.getCalorie());
                        textViewProgressCalorie.setText(target.getCurrentCalorie() + "");
                        progressBarCalorie.setProgress(target.getCurrentCalorie());
                        db.delete(DataBaseHelper.TABLE_EATING, "id_dish = ?", new String[] {String.valueOf(frame.getIdE())});
                        recordCurrent();
                        framesContainer.removeView(v);
                        Toast.makeText(context, "Удалено",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();
                return true;
            }

        });
        framesContainer.addView(frame);
    }

    private void initFrame(EatLayout frame) {
        frame.setTextMainProgressProtein(textViewProgressProtein);
        frame.setTextMainProgressCalorie(textViewProgressCalorie);
        frame.setTextMainProgressCarbohydrate(textViewProgressCarbohydrate);
        frame.setTextMainProgressFat(textViewProgressFat);

        frame.setProgressMainBarProtein(progressBarProtein);
        frame.setProgressMainBarCalorie(progressBarCalorie);
        frame.setProgressMainBarCarbohydrate(progressBarCarbohydrate);
        frame.setProgressMainBarFat(progressBarFat);

    }

    void recordEat(Eating eating, String weightS){
       // int weight = Integer.parseInt(weightS);
        double weightEat = Double.parseDouble(weightS) / 100.0;
//        double protein = new BigDecimal(eating.getProtein() * weightEat).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        double fat = new BigDecimal(eating.getFat() * weightEat).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        double carbohydrate = new BigDecimal(eating.getCarbohydrate() * weightEat).setScale(2, RoundingMode.HALF_UP).doubleValue();
//        double calorie = new BigDecimal(eating.getCalorie() * weightEat).setScale(2, RoundingMode.HALF_UP).doubleValue();


        cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_ID_USER_EATING, 1);
        cv.put(DataBaseHelper.COLUMN_ID_DISH_EATING, eating.getId());
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("DataBaseHelper", "recordEat: " + formatForDateNow.format(dateNow) + "; eating.getName()");
        cv.put(DataBaseHelper.COLUMN_DATE_EATING, formatForDateNow.format(dateNow));
        cv.put(DataBaseHelper.COLUMN_WEIGHT_EATING, Integer.parseInt(weightS));
        db.insert(DataBaseHelper.TABLE_EATING, null, cv);
        recordCurrent();

    }

    void recordCurrent(){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_ID_USER_CURRENT, 1);
        cv.put(DataBaseHelper.COLUMN_CURRENT_PROTEIN_CURRENT, target.getCurrentProtein());
        cv.put(DataBaseHelper.COLUMN_CURRENT_FAT_CURRENT, target.getCurrentFat());
        cv.put(DataBaseHelper.COLUMN_CURRENT_WATER_CURRENT, target.getCurrentWater());
        cv.put(DataBaseHelper.COLUMN_CURRENT_CARBOHYDRATE_CURRENT, target.getCurrentCarbohydrate());
        cv.put(DataBaseHelper.COLUMN_CURRENT_CALORIE_CURRENT, target.getCurrentCalorie());
        cv.put(DataBaseHelper.COLUMN_DATE_CURRENT, formatForDateNow.format(dateNow));

        Log.d("DataBaseHelper", "recordEat; updated = " + target.getCurrentProtein());
        Log.d("DataBaseHelper", "recordEat; updated = " + target.getCurrentFat());
        Log.d("DataBaseHelper", "recordEat; updated = " + target.getCurrentWater());
        Log.d("DataBaseHelper", "recordEat; updated = " + target.getCurrentCarbohydrate());
        Log.d("DataBaseHelper", "recordEat; updated = " + target.getCurrentCalorie());

        // не обновлять а вычитать или добавлять
        userCursor =  db.rawQuery("select * from "+ DataBaseHelper.TABLE_CURRENT, null);
        if (!userCursor.moveToFirst()){
            db.insert(DataBaseHelper.TABLE_CURRENT, null, cv);
        } else {
            String selection = "date = ?";
            String[] selectionArgs = new String[]{ formatForDateNow.format(dateNow) };
            userCursor = db.query(DataBaseHelper.TABLE_CURRENT, null, selection, selectionArgs, null, null, null);
            Log.d("DataBaseHelper", "recordEat; updated rows count = " + userCursor.getCount());
            if (userCursor != null) {
                if (!userCursor.moveToFirst()) {
                    db.insert(DataBaseHelper.TABLE_CURRENT, null, cv);
                } else {
                    int updCount = db.update(DataBaseHelper.TABLE_CURRENT, cv, selection,
                            selectionArgs);
                    Log.d("DataBaseHelper", "recordEat; else updated rows count = " + updCount);
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("DataBaseHelper",  "onActivityResult " + requestCode + ", " + resultCode);
        Log.d("DataBaseHelper",  "onActivityResult " + (resultCode == (REQUEST_ACCESS_TYPE - 2)));
        if (resultCode == (REQUEST_ACCESS_TYPE - 2)){
            target = data.getParcelableExtra(Target.class.getSimpleName());
            target.calculateBJU();
            Log.d("DataBaseHelper",  "target.calculateBJU() " + target.getTargetWeight() + ", " + target.getTargetCalorie() + ", " + target.getTargetCarbohydrate() +
                    ", " + target.getTargetFat() + ", " + target.getTargetProtein());
            db = databaseHelper.open();
            cv = new ContentValues();
            cv.put(DataBaseHelper.COLUMN_ID_USER, 1);
            cv.put(DataBaseHelper.COLUMN_ID_PROGRAM_USER, target.getTrainingPlan().getId_plan());
            cv.put(DataBaseHelper.COLUMN_CURRENT_WEIGHT_USER, target.getPeople().getWeight());
            cv.put(DataBaseHelper.COLUMN_TARGET_WEIGHT_USER, target.getTargetWeight());
            cv.put(DataBaseHelper.COLUMN_TARGET_PROTEIN_USER, target.getTargetProtein());
            cv.put(DataBaseHelper.COLUMN_TARGET_FAT_USER, target.getTargetFat());
            cv.put(DataBaseHelper.COLUMN_TARGET_WATER_USER, target.getTargetWater());
            cv.put(DataBaseHelper.COLUMN_TARGET_CARBOHYDRATE_USER, target.getTargetCarbohydrate());
            cv.put(DataBaseHelper.COLUMN_TARGET_CALORIE_USER, target.getTargetCalorie());
            cv.put(DataBaseHelper.COLUMN_GENDER_USER, target.getPeople().getGender());
            cv.put(DataBaseHelper.COLUMN_AGE_USER, target.getPeople().getAge());
            cv.put(DataBaseHelper.COLUMN_GROWTH_USER, target.getPeople().getGrowth());
            cv.put(DataBaseHelper.COLUMN_ACTIVE_USER, target.getActive());
            userCursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_USER, null);
            Log.d("DataBaseHelper", String.valueOf(userCursor.getCount()));
            if (userCursor.getCount() == 0) {
                db.insert(DataBaseHelper.TABLE_USER, null, cv);
//                db.execSQL("INSERT INTO "+ DataBaseHelper.TABLE_USER +" (" + DataBaseHelper.COLUMN_ID_USER
//                        + ", " + DataBaseHelper.COLUMN_ID_PROGRAM_USER + ", " + DataBaseHelper.COLUMN_CURRENT_WEIGHT_USER
//                        + ", " + DataBaseHelper.COLUMN_TARGET_WEIGHT_USER + ", " + DataBaseHelper.COLUMN_TARGET_PROTEIN_USER
//                        + ", " + DataBaseHelper.COLUMN_TARGET_FAT_USER + ", " + DataBaseHelper.COLUMN_TARGET_WATER_USER
//                        + ", " + DataBaseHelper.COLUMN_TARGET_CARBOHYDRATE_USER + ", " + DataBaseHelper.COLUMN_TARGET_CALORIE_USER
//                        + ", " + DataBaseHelper.COLUMN_GENDER_USER + ", " + DataBaseHelper.COLUMN_AGE_USER
//                        + ", " + DataBaseHelper.COLUMN_GROWTH_USER + ", " + DataBaseHelper.COLUMN_ACTIVE_USER + ") VALUES (1, 1, " + target.getPeople().getWeight() +
//                        ", " + target.getTargetWeight() + ", " + target.getTargetProtein() + ", " + target.getTargetFat() + ", " + target.getTargetWater()
//                        + ", " + target.getTargetCarbohydrate() + ", " + target.getTargetCalorie() + ", " + target.getPeople().getGender() +
//                        ", " + target.getPeople().getAge() + ", " + target.getPeople().getGrowth() + ", " + target.getActive() + ");");
                Log.d("DataBaseHelper", "insert");
            } else {

                int updCount = db.update(DataBaseHelper.TABLE_USER, cv, "_id = ?",
                        new String[]{"1"});
                Log.d("DataBaseHelper", "updated rows count = " + updCount);
                userCursor = db.query(DataBaseHelper.TABLE_USER, null, null, null, null, null, null);
                if (userCursor != null) {
                    if (userCursor.moveToFirst()) {
                        String str;
                        do {
                            str = "";
                            for (String cn : userCursor.getColumnNames()) {
                                str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                            }
                            Log.d("DataBaseHelper", str);
                        } while (userCursor.moveToNext());
                    } else Log.d("DataBaseHelper", "TABLE_TRAINING: 0");
                    userCursor.close();
                } else Log.d("DataBaseHelper", "Cursor is null");
            }

            setBJU();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.target: {
                Intent intent = new Intent(this, TargetLayout.class);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
                break;
            }
            case R.id.help: {
                Intent intent = new Intent(this, Help.class);
                startActivity(intent);
                break;
            }
            case R.id.training: {
                if (target != null) {
                    Intent intent = new Intent(this, ContainerTrainingDay.class);
                    intent.putExtra(Target.class.getSimpleName(), target);
                    startActivity(intent);
                }
                break;
            }
            case R.id.reportForEat: {
                if (target != null) {
                    Intent intent = new Intent(this, reportEat.class);
              //      intent.putExtra(Target.class.getSimpleName(), target);
                    startActivity(intent);
                }
                break;
            }
            case R.id.reportForTraining: {
                if (target != null) {
                    Intent intent = new Intent(this, reportTraining.class);
                    //      intent.putExtra(Target.class.getSimpleName(), target);
                    startActivity(intent);
                }
                break;
            }
            case R.id.eatingList: {
                if (target != null) {
                    Intent intent = new Intent(this, EatingList.class);
                    //      intent.putExtra(Target.class.getSimpleName(), target);
                    startActivity(intent);
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setBJU(){
        textViewWater.setText(target.getTargetWater() + "");
        textViewCalorie.setText(target.getTargetCalorie() + "");
        textViewFat.setText(target.getTargetFat() + "");
        textViewCarbohydrate.setText(target.getTargetCarbohydrate() + "");
        textViewProtein.setText(String.valueOf(target.getTargetProtein()));
        progressBarWater.setMax(target.getTargetWater());
        progressBarProtein.setMax(target.getTargetProtein());
        progressBarFat.setMax(target.getTargetFat());
        progressBarCarbohydrate.setMax(target.getTargetCarbohydrate());
        progressBarCalorie.setMax(target.getTargetCalorie());
    }

    static void progressBJU(int progress, TextView textView, ProgressBar progressBar){
        int id = textView.getId();
        switch (id){
            case R.id.progressWater: {
                target.setCurrentWater(target.getCurrentWater() + progress);
                textView.setText(target.getCurrentWater() + "");
                progressBar.setProgress(target.getCurrentWater());
                break;
            }
            case R.id.progressProtein: {
                target.setCurrentProtein(target.getCurrentProtein() + progress);
                textView.setText(target.getCurrentProtein() + "");
                progressBar.setProgress(target.getCurrentProtein());
                break;
            }
            case R.id.progressFat: {
                target.setCurrentFat(target.getCurrentFat() + progress);
                textView.setText(target.getCurrentFat() + "");
                progressBar.setProgress(target.getCurrentFat());
                break;
            }
            case R.id.progressCarbohydrate: {
                target.setCurrentCarbohydrate(target.getCurrentCarbohydrate() + progress);
                textView.setText(target.getCurrentCarbohydrate() + "");
                progressBar.setProgress(target.getCurrentCarbohydrate());
                break;
            }
            case R.id.progressCalorie: {
                target.setCurrentCalorie(target.getCurrentCalorie() + progress);
                textView.setText(target.getCurrentCalorie() + "");
                progressBar.setProgress(target.getCurrentCalorie());
                break;
            }
        }
//        Toast.makeText(context, target.getCurrentWater() + "", Toast.LENGTH_LONG)
//                .show();

    }

    private void init(){
        progressBarWater = findViewById(R.id.progressWaterBar);
        progressBarProtein = findViewById(R.id.progressProteinBar);
        progressBarFat = findViewById(R.id.progressFatBar);
        progressBarCalorie = findViewById(R.id.progressCalorieBar);
        progressBarCarbohydrate = findViewById(R.id.progressCarbohydrateBar);

        textViewProgressWater = findViewById(R.id.progressWater);
        textViewProgressProtein = findViewById(R.id.progressProtein);
        textViewProgressFat = findViewById(R.id.progressFat);
        textViewProgressCalorie = findViewById(R.id.progressCalorie);
        textViewProgressCarbohydrate = findViewById(R.id.progressCarbohydrate);

        textViewWater = findViewById(R.id.targetWater);
        textViewCalorie = findViewById(R.id.targetCalorie);
        textViewFat = findViewById(R.id.targetFat);
        textViewCarbohydrate = findViewById(R.id.targetCarbohydrate);
        textViewProtein = findViewById(R.id.targetProtein);
        floatingActionButtonWater = findViewById(R.id.floatingActionButtonWater);
        floatingActionButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View form = MainActivity.this.getLayoutInflater().inflate(R.layout.water_layout,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Вода:")
                        .setView(form).setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (target != null) {
                            EditText et = (EditText) form.findViewById(R.id.enterWater);
                            int progress = Integer.parseInt(et.getText().toString());
                            if (progress != 0) {
                                progressBJU(progress, textViewProgressWater, progressBarWater);
                                addWater(progress);
                                recordEat(water, String.valueOf(et.getText()));
                           //     addEat(, String.valueOf(etGr.getText()));
                            //    recordEat(frame[0].getEating(), String.valueOf(etGr.getText()));
                            }
                        }
                    }
                })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create();
                builder.show();
                // showRatingDialog();

            }
        });

        floatingActionButtonEat = findViewById(R.id.floatingActionButtonEat);
        floatingActionButtonEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View form = MainActivity.this.getLayoutInflater().inflate(R.layout.add_eating,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LinearLayout framesContainer = (LinearLayout) form.findViewById(R.id.addEating);
                final EatingLayout[] frame = {null};
                //framesContainer.addView(frame);
                builder.setTitle("Еда:")
                        .setView(form).setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (frame != null && target != null) {
                            EditText etGr = (EditText) form.findViewById(R.id.editTextGram);
                            if (etGr != null && !String.valueOf(etGr.getText()).equals("")) {
                                int progress[] = new int[4];
                                TextView progressText[] = new TextView[4];
                                ProgressBar progressBar[] = new ProgressBar[4];

                                double weightEat = Double.parseDouble(String.valueOf(etGr.getText())) / 100.0;
                                int protein = (int) Math.round(frame[0].getEating().getProtein() * weightEat);
                                int fat = (int) Math.round(frame[0].getEating().getFat() * weightEat);
                                int carbohydrate = (int) Math.round(frame[0].getEating().getCarbohydrate() * weightEat);
                                int calorie = (int) Math.round(frame[0].getEating().getCalorie() * weightEat);

                                progress[0] = protein;
                                progress[1] = fat;
                                progress[2] = carbohydrate;
                                progress[3] = calorie;
                                progressText[0] = textViewProgressProtein;
                                progressText[1] = textViewProgressFat;
                                progressText[2] = textViewProgressCarbohydrate;
                                progressText[3] = textViewProgressCalorie;
                                progressBar[0] = progressBarProtein;
                                progressBar[1] = progressBarFat;
                                progressBar[2] = progressBarCarbohydrate;
                                progressBar[3] = progressBarCalorie;
                                for (int i = 0; i < 4; i++) {
                                    progressBJU(progress[i], progressText[i], progressBar[i]);
                                }
                                addEat(frame[0].getEating(), String.valueOf(etGr.getText()));
                                recordEat(frame[0].getEating(), String.valueOf(etGr.getText()));
                            }
//                            cv = new ContentValues();
//                            cv.put(DataBaseHelper.COLUMN_ID_PROGRAM_USER, 1); cv.put(DataBaseHelper.COLUMN_CURRENT_WEIGHT_USER, target.getPeople().getWeight());
//                            cv.put(DataBaseHelper.COLUMN_TARGET_WEIGHT_USER, target.getTargetWeight()); cv.put(DataBaseHelper.COLUMN_TARGET_PROTEIN_USER, target.getTargetProtein());
//                            cv.put(DataBaseHelper.COLUMN_TARGET_FAT_USER, target.getTargetFat()); cv.put(DataBaseHelper.COLUMN_TARGET_WATER_USER, target.getTargetWater());
//                            cv.put(DataBaseHelper.COLUMN_TARGET_CARBOHYDRATE_USER, target.getTargetCarbohydrate()); cv.put(DataBaseHelper.COLUMN_TARGET_CALORIE_USER, target.getTargetCalorie());
//                            cv.put(DataBaseHelper.COLUMN_GENDER_USER, target.getPeople().getGender()); cv.put(DataBaseHelper.COLUMN_AGE_USER, target.getPeople().getAge());
//                            cv.put(DataBaseHelper.COLUMN_GROWTH_USER, target.getPeople().getGrowth()); cv.put(DataBaseHelper.COLUMN_ACTIVE_USER, target.getActive());
//                            userCursor =  db.rawQuery("select * from "+ DataBaseHelper.TABLE_TRAINING_PROGRAM, null);
//                            Log.d("DataBaseHelper", String.valueOf(userCursor.getCount()));
//                            db.insert(DataBaseHelper.TABLE_USER, null, cv);
                        }
                    }
                })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                final EditText etS = (EditText)form.findViewById(R.id.editTextScreech);
                etS.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.d("myLogs", s.toString()  + " ; beforeTextChanged");
                        if (frame[0] != null)
                            framesContainer.removeView(frame[0]);



                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d("myLogs", s.toString() + " ; onTextChanged");
                        Eating e = null;
                        userCursor =  db.rawQuery("select * from " + DataBaseHelper.TABLE_DISH + " where " +
                                DataBaseHelper.COLUMN_NAME_DISH + " like ?", new String[]{"%" + s.toString() + "%"});
                        if (userCursor.moveToFirst()) {
                            int idIndex = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_DISH);
                            int idEating = userCursor.getColumnIndex(DataBaseHelper.COLUMN_ID_EATING_DISH);
                            int idName = userCursor.getColumnIndex(DataBaseHelper.COLUMN_NAME_DISH);
                            int idProtein = userCursor.getColumnIndex(DataBaseHelper.COLUMN_PROTEIN_DISH);
                            int idFat = userCursor.getColumnIndex(DataBaseHelper.COLUMN_FAT_DISH);
                            int idCarbohydrate = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CARBOHYDRATE_DISH);
                            int idCalorie = userCursor.getColumnIndex(DataBaseHelper.COLUMN_CALORIE_DISH);
                            int idWeight = userCursor.getColumnIndex(DataBaseHelper.COLUMN_WEIGHT_DISH);
                            do {
                                if (!userCursor.getString(idName).equals("Вода")) {
                                    e = new Eating(userCursor.getInt(idIndex), userCursor.getString(idName), userCursor.getInt(idProtein),
                                            userCursor.getInt(idFat), 0, userCursor.getInt(idCarbohydrate), userCursor.getInt(idCalorie));
                                }
                            } while (userCursor.moveToNext());
                        }

                        if (e != null) {
                            frame[0] = new EatingLayout(getApplicationContext());
                            frame[0].setEating(e);
                            frame[0].setValue();
                            framesContainer.addView(frame[0]);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.d("myLogs", s.toString()  + " ; afterTextChanged");
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    private void addWater(int progress) {
        final WaterLayout frame = new WaterLayout(water.getId(), getApplicationContext());
        frame.setTextViewWater(String.valueOf(progress));
        frame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder ad;
                String title = "Выбор есть всегда";
                String message = "Удалить воду";
                String button1String = "Да";
                String button2String = "Нет";
                Log.d("DataBaseHelper", "onLongClick");
                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        target.setCurrentWater((int) (target.getCurrentWater() - frame.getWater()));
                        textViewProgressWater.setText(target.getCurrentWater() + "");
                        progressBarWater.setProgress((int) target.getCurrentWater());

                        db.delete(DataBaseHelper.TABLE_EATING, "id_dish = ?", new String[] {String.valueOf(frame.getIdE())});
                        recordCurrent();
                        framesContainer.removeView(v);
                        Toast.makeText(context, "Удалено",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();
                return true;
            }

        });
        framesContainer.addView(frame);
    }


    private void showRatingDialog(){
//
//        final AlertDialog.Builder ratingdialog = new AlertDialog.Builder(MainActivity.this);
//
//        ratingdialog.setIcon(android.R.drawable.btn_star_big_on);
//        ratingdialog.setTitle("Выпил воды");
//
//        final View linearlayout = getLayoutInflater().inflate(R.layout.water_layout, null);
//        ratingdialog.setView(linearlayout);
//
//
//        //final RatingBar rating = (RatingBar)linearlayout.findViewById(R.id.ratingbar);
//
//        ratingdialog.setPositiveButton("Готово",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        final EditText enter = (EditText)findViewById(R.id.enterWater);
//                        Toast.makeText(context, enter.getClass().getSimpleName(), Toast.LENGTH_LONG)
//                                .show();
//                       // int progress = Integer.parseInt(enter.getText().toString());
//                      //  TextView textViewProgressWater = findViewById(R.id.progressWater);
//                      //  ProgressBar progressBarWater = findViewById(R.id.progressWaterBar);
//                      //  progressBJU(progress, textViewProgressWater, progressBarWater);
//                        dialog.dismiss();
//                    }
//                })
//
//                .setNegativeButton("Отмена",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        ratingdialog.create();
//        ratingdialog.show();
    }

    public static DataBaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
