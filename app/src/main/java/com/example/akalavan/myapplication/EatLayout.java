package com.example.akalavan.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akalavan.myapplication.db.DataBaseHelper;
import com.example.akalavan.myapplication.eating.Eating;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EatLayout extends LinearLayout {
    private TextView textViewWater, textViewFat, textViewCarbohydrate, textViewProtein,
            textViewCalorie, textViewProgressWater, textViewNameEat, textViewWeight,
            textViewProgressCalorie, textViewProgressCarbohydrate, textMainProgressProtein,
            textMainProgressFat, textMainProgressCarbohydrate, textMainProgressCalorie;
    private ProgressBar progressMainBarWater, progressMainBarProtein, progressMainBarFat, progressMainBarCalorie,
            progressMainBarCarbohydrate;
    private Button buttonChang;
    private EditText editTextWeight;
    private Eating eating;
    private int  idE;
    private int fat, protein, calorie, carbohydrate, weight;

    //  private EditText editTextAppproaches;
    public EatLayout(int idE, Context context, Eating eating) {
        super(context);
        this.idE = idE;
        this.eating = eating;
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.eat, this);

        buttonChang = findViewById(R.id.buttonChang);
        textViewNameEat = findViewById(R.id.textViewNameEat);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewCalorie = findViewById(R.id.textViewCalorie);
        textViewFat = findViewById(R.id.textViewFat);
        textViewCarbohydrate = findViewById(R.id.textViewCarbohydrate);
        textViewProtein = findViewById(R.id.textViewProtein);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonChang.setOnClickListener(buttonListener);

        setSetting();
    }

    private final OnClickListener buttonListener = new OnClickListener() {
        public void onClick(View view) {
            int id = view.getId();
            String s = "";
            switch (id) {
                case R.id.buttonChang:
                    s = String.valueOf(editTextWeight.getText());
                    if (s != null && !s.equals("0") && !s.equals("")) {
                        textViewWeight.setText(String.valueOf(Integer.parseInt(s)));
                        chang(s);
                    } else {
                        Toast.makeText(getContext(),"Неправильно введены данные",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void chang(String weight){

            SQLiteDatabase db;
            DataBaseHelper databaseHelper;

            databaseHelper = MainActivity.getDatabaseHelper();
            databaseHelper.create_db();
            db = databaseHelper.open();

            MainActivity.target.setCurrentProtein(MainActivity.target.getCurrentProtein() - getProtein());
            MainActivity.target.setCurrentFat(MainActivity.target.getCurrentFat() - getFat());
            MainActivity.target.setCurrentCarbohydrate(MainActivity.target.getCurrentCarbohydrate() - getCarbohydrate());
            MainActivity.target.setCurrentCalorie(MainActivity.target.getCurrentCalorie() - getCalorie());

            double weightEat = Double.parseDouble(weight) / 100.0;
            setProtein((int) Math.round(eating.getProtein() * weightEat));
            setFat((int) Math.round(eating.getFat() * weightEat));
            setCarbohydrate((int) Math.round(eating.getCarbohydrate() * weightEat));
            setCalorie((int) Math.round(eating.getCalorie() * weightEat));

            MainActivity.target.setCurrentProtein(MainActivity.target.getCurrentProtein() + getProtein());
            textMainProgressProtein.setText(MainActivity.target.getCurrentProtein() + "");
            progressMainBarProtein.setProgress(MainActivity.target.getCurrentProtein());

            MainActivity.target.setCurrentFat(MainActivity.target.getCurrentFat() + getFat());
            textMainProgressFat.setText(MainActivity.target.getCurrentFat() + "");
            progressMainBarFat.setProgress(MainActivity.target.getCurrentFat());

            MainActivity.target.setCurrentCarbohydrate(MainActivity.target.getCurrentCarbohydrate() + getCarbohydrate());
            textMainProgressCarbohydrate.setText(MainActivity.target.getCurrentCarbohydrate() + "");
            progressMainBarCarbohydrate.setProgress(MainActivity.target.getCurrentCarbohydrate());

            MainActivity.target.setCurrentCalorie(MainActivity.target.getCurrentCalorie() + getCalorie());
            textMainProgressCalorie.setText(MainActivity.target.getCurrentCalorie() + "");
            progressMainBarCalorie.setProgress(MainActivity.target.getCurrentCalorie());

            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");

            ContentValues cv = new ContentValues();
            cv.put(DataBaseHelper.COLUMN_ID_USER_EATING, 1);
            cv.put(DataBaseHelper.COLUMN_ID_DISH_EATING, eating.getId());
            Log.d("DataBaseHelper", "chang: " + formatForDateNow.format(dateNow));
            cv.put(DataBaseHelper.COLUMN_DATE_EATING, formatForDateNow.format(dateNow));
            cv.put(DataBaseHelper.COLUMN_WEIGHT_EATING, Integer.parseInt(weight));

            db.update(DataBaseHelper.TABLE_EATING, cv, "id_dish = ?", new String[]{String.valueOf(getIdE())});

            cv = new ContentValues();
            cv.put(DataBaseHelper.COLUMN_ID_USER_CURRENT, 1);
            cv.put(DataBaseHelper.COLUMN_CURRENT_PROTEIN_CURRENT, MainActivity.target.getCurrentProtein());
            cv.put(DataBaseHelper.COLUMN_CURRENT_FAT_CURRENT, MainActivity.target.getCurrentFat());
            cv.put(DataBaseHelper.COLUMN_CURRENT_WATER_CURRENT, MainActivity.target.getCurrentWater());
            cv.put(DataBaseHelper.COLUMN_CURRENT_CARBOHYDRATE_CURRENT, MainActivity.target.getCurrentCarbohydrate());
            cv.put(DataBaseHelper.COLUMN_CURRENT_CALORIE_CURRENT, MainActivity.target.getCurrentCalorie());
            cv.put(DataBaseHelper.COLUMN_DATE_CURRENT, formatForDateNow.format(dateNow));

            Cursor userCursor;

            String selection = "date = ?";
            String[] selectionArgs = new String[]{formatForDateNow.format(dateNow)};
            userCursor = db.query(DataBaseHelper.TABLE_CURRENT, null, selection, selectionArgs, null, null, null);
            Log.d("DataBaseHelper", "chang; updated rows count = " + userCursor.getCount());
            if (userCursor != null) {
                if (!userCursor.moveToFirst()) {
                    db.insert(DataBaseHelper.TABLE_CURRENT, null, cv);
                } else {
                    int updCount = db.update(DataBaseHelper.TABLE_CURRENT, cv, selection,
                            selectionArgs);
                    Log.d("DataBaseHelper", "chang; else updated rows count = " + updCount);
                }
            }

            setTextViewNameEat(eating.getName());
            setTextViewWeight(weight);
            setTextViewCalorie(String.valueOf(calorie));
            setTextViewFat(String.valueOf(fat));
            setTextViewCarbohydrate(String.valueOf(carbohydrate));
            setTextViewProtein(String.valueOf(protein));

    }


    public void setSetting(){
       // progressBarProtein.setBackgroundColor(Color.BLACK);
      //  progressBarProtein.setDrawingCacheBackgroundColor(Color.BLACK);
//        progressBarProtein.setMax(50);
//
//        progressBarProtein.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
//        progressBarFat.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
//        progressBarCalorie.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
//        progressBarCarbohydrate.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        textViewNameEat.setTextColor(Color.BLACK);
        textViewWeight.setTextColor(Color.BLACK);
      //  textViewProgressCalorie.setTextColor(Color.BLACK);
        editTextWeight.setTextColor(Color.BLACK);
        textViewCalorie.setTextColor(Color.BLACK);
        textViewFat.setTextColor(Color.BLACK);
        textViewCarbohydrate.setTextColor(Color.BLACK);
        textViewProtein.setTextColor(Color.BLACK);
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }


    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public TextView getTextViewWater() {
        return textViewWater;
    }

    public void setTextViewWater(TextView textViewWater) {
        this.textViewWater = textViewWater;
    }

    public String getTextViewFat() {
        return String.valueOf(textViewFat.getText());
    }

    public void setTextViewFat(String textViewFat) {
        fat = Integer.parseInt(textViewFat);
        this.textViewFat.setText("Жиров: " + textViewFat);
    }

    public String getTextViewCarbohydrate() {
        return (String) textViewCarbohydrate.getText();
    }

    public void setTextViewCarbohydrate(String textViewCarbohydrate) {
        carbohydrate = Integer.parseInt(textViewCarbohydrate);
        this.textViewCarbohydrate.setText("Углеводов: " + textViewCarbohydrate);
    }

    public String getTextViewProtein() {
        return String.valueOf(textViewProtein.getText());
    }

    public void setTextViewProtein(String textViewProtein) {
        protein = Integer.parseInt(textViewProtein);
        this.textViewProtein.setText("Белков: " + textViewProtein);
    }

    public String getTextViewCalorie() {
        return (String) textViewCalorie.getText();
    }

    public void setTextViewCalorie(String textViewCalorie) {
        calorie = Integer.parseInt(textViewCalorie);
        this.textViewCalorie.setText("Ккал: " + textViewCalorie);
    }

    public TextView getTextViewProgressWater() {
        return textViewProgressWater;
    }

    public void setTextViewProgressWater(TextView textViewProgressWater) {
        this.textViewProgressWater = textViewProgressWater;
    }

    public TextView getTextViewNameEat() {
        return textViewNameEat;
    }

    public void setTextViewNameEat(String textViewNameEat) {
        this.textViewNameEat.setText(textViewNameEat);
    }

    public TextView getTextViewWeight() {
        return textViewWeight;
    }

    public void setTextViewWeight(String textViewWeight) {
        weight = Integer.parseInt(textViewWeight);
        this.textViewWeight.setText("Вес: " + textViewWeight);
    }

    public TextView getTextViewProgressCalorie() {
        return textViewProgressCalorie;
    }

    public void setTextViewProgressCalorie(TextView textViewProgressCalorie) {
        this.textViewProgressCalorie = textViewProgressCalorie;
    }

    public TextView getTextViewProgressCarbohydrate() {
        return textViewProgressCarbohydrate;
    }

    public void setTextViewProgressCarbohydrate(TextView textViewProgressCarbohydrate) {
        this.textViewProgressCarbohydrate = textViewProgressCarbohydrate;
    }

    public EditText getEditTextWeight() {
        return editTextWeight;
    }

    public void setEditTextWeight(EditText editTextWeight) {
        this.editTextWeight = editTextWeight;
    }

    public int getIdE() {
        return idE;
    }

    public void setTextMainProgressProtein(TextView textMainProgressProtein) {
        this.textMainProgressProtein = textMainProgressProtein;
    }

    public void setTextMainProgressFat(TextView textMainProgressFat) {
        this.textMainProgressFat = textMainProgressFat;
    }

    public void setTextMainProgressCarbohydrate(TextView textMainProgressCarbohydrate) {
        this.textMainProgressCarbohydrate = textMainProgressCarbohydrate;
    }

    public void setTextMainProgressCalorie(TextView textMainProgressCalorie) {
        this.textMainProgressCalorie = textMainProgressCalorie;
    }

    public void setProgressMainBarWater(ProgressBar progressMainBarWater) {
        this.progressMainBarWater = progressMainBarWater;
    }

    public void setProgressMainBarProtein(ProgressBar progressMainBarProtein) {
        this.progressMainBarProtein = progressMainBarProtein;
    }

    public void setProgressMainBarFat(ProgressBar progressMainBarFat) {
        this.progressMainBarFat = progressMainBarFat;
    }

    public void setProgressMainBarCalorie(ProgressBar progressMainBarCalorie) {
        this.progressMainBarCalorie = progressMainBarCalorie;
    }

    public void setProgressMainBarCarbohydrate(ProgressBar progressMainBarCarbohydrate) {
        this.progressMainBarCarbohydrate = progressMainBarCarbohydrate;
    }
}
