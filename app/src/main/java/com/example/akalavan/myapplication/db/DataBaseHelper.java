package com.example.akalavan.myapplication.db;

/**
 * Created by Akalavan on 11.04.2019.
 */


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Akalavan on 07.04.2019.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static final String DATABASE_NAME = "mydb.db"; // название бд
    private static final int SCHEMA = 4; // версия базы данных
    private Context myContext;

    // ТАБИЛЦА USER
    // название таблицы в бд
    public static final String TABLE_USER = "user";
    // названия столбцов
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_ID_PROGRAM_USER = "id_program";
    public static final String COLUMN_CURRENT_WEIGHT_USER  = "currentWeight";
    public static final String COLUMN_TARGET_WEIGHT_USER = "targetWeight";
    public static final String COLUMN_TARGET_PROTEIN_USER  = "targetProtein";
    public static final String COLUMN_TARGET_FAT_USER = "targetFat";
    public static final String COLUMN_TARGET_WATER_USER = "targetWater";
    public static final String COLUMN_TARGET_CARBOHYDRATE_USER = "targetCarbohydrate";
    public static final String COLUMN_TARGET_CALORIE_USER = "targetCalorie";
    public static final String COLUMN_GENDER_USER = "gender";
    public static final String COLUMN_AGE_USER = "age";
    public static final String COLUMN_GROWTH_USER = "growth";
    public static final String COLUMN_ACTIVE_USER = "active";

    // ТАБИЛЦА DISH
    // название таблицы в бд
    public static final String TABLE_DISH = "dish";
    // названия столбцов
    public static final String COLUMN_ID_DISH = "_id";
    public static final String COLUMN_ID_EATING_DISH = "id_eating";
    public static final String COLUMN_NAME_DISH  = "name";
    public static final String COLUMN_PROTEIN_DISH = "protein";
    public static final String COLUMN_FAT_DISH  = "fat";
    public static final String COLUMN_CARBOHYDRATE_DISH  = "carbohydrate";
    public static final String COLUMN_CALORIE_DISH  = "calorie";
    public static final String COLUMN_WEIGHT_DISH  = "weight";

    // ТАБИЛЦА EATING
    // название таблицы в бд
    public static final String TABLE_EATING = "eating";
    // названия столбцов
    public static final String COLUMN_ID_EATING = "_id";
    public static final String COLUMN_ID_USER_EATING = "id_user";
    public static final String COLUMN_ID_DISH_EATING = "id_dish";
    public static final String COLUMN_DATE_EATING = "date";
    public static final String COLUMN_WEIGHT_EATING = "weight";

    // ТАБИЛЦА CURRENT
    // название таблицы в бд
    public static final String TABLE_CURRENT = "current";
    // названия столбцов
    public static final String COLUMN_ID_CURRENT = "_id";
    public static final String COLUMN_ID_USER_CURRENT = "id_user";
    public static final String COLUMN_CURRENT_PROTEIN_CURRENT  = "currentProtein";
    public static final String COLUMN_CURRENT_FAT_CURRENT = "currentFat";
    public static final String COLUMN_CURRENT_WATER_CURRENT  = "currentWater";
    public static final String COLUMN_CURRENT_CARBOHYDRATE_CURRENT  = "currentCarbohydrate";
    public static final String COLUMN_CURRENT_CALORIE_CURRENT  = "currentCalorie";
    public static final String COLUMN_DATE_CURRENT  = "date";

    // ТАБИЛЦА TRAINING
    // название таблицы в бд
    public static final String TABLE_TRAINING = "training";
    // названия столбцов
    public static final String COLUMN_ID_TRAINING = "_id";
    public static final String COLUMN_ID_DAY_TRAINING = "id_day";
    public static final String COLUMN_USER_TRAINING = "id_user";
    public static final String COLUMN_DATE_TRAINING = "date";
    public static final String COLUMN_PERCENTAGE_COMPLETION_TRAINING = "percentage_completion";

    // ТАБИЛЦА TRAINING_PROGRAM
    // название таблицы в бд
    public static final String TABLE_TRAINING_PROGRAM = "training_program";
    // названия столбцов
    public static final String COLUMN_ID_TRAINING_PROGRAM = "_id";
    public static final String COLUMN_NAME_TRAINING_PROGRAM = "name";
    public static final String COLUMN_DESCRIPTION_TRAINING_PROGRAM = "description";

    // ТАБИЛЦА EXERCISE
    // название таблицы в бд
    public static final String TABLE_EXERCISE = "exercise";
    // названия столбцов
    public static final String COLUMN_ID_EXERCISE = "_id";
    public static final String COLUMN_NAME_EXERCISE = "name";
    public static final String COLUMN_NUMBER_APPROACHES_EXERCISE = "number_approaches";
    public static final String COLUMN_NUMBER_EXECUTIONS_EXERCISE = "number_executions";
    public static final String COLUMN_COMMENT_EXERCISE = "comment";

    // ТАБИЛЦА TRAINING_DAY
    // название таблицы в бд
    public static final String TABLE_TRAINING_DAY = "training_day";
    // названия столбцов
    public static final String COLUMN_ID_TRAINING_DAY = "_id";
    public static final String COLUMN_DAY_TRAINING_DAY = "day";
    public static final String COLUMN_ID_EXERCISE_TRAINING_DAY = "id_exercise";
    public static final String COLUMN_ID_PROGRAM_TRAINING_DAY = "id_program";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = context.getFilesDir().getAbsolutePath() + "/" +DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        // создаем таблицу USER
//        db.execSQL("CREATE TABLE user (" + COLUMN_ID_USER
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_PROGRAM_USER
//                + " INTEGER, " + COLUMN_CURRENT_WEIGHT_USER + " INTEGER, " + COLUMN_TARGET_WEIGHT_USER
//                + " INTEGER, " + COLUMN_TARGET_PROTEIN_USER + " INTEGER, " + COLUMN_TARGET_FAT_USER
//                + " INTEGER, " + COLUMN_TARGET_WATER_USER + " INTEGER, " + COLUMN_TARGET_CARBOHYDRATE_USER
//                + " INTEGER, " + COLUMN_TARGET_CALORIE_USER + " INTEGER, " + COLUMN_GENDER_USER
//                + " INTEGER, " + COLUMN_AGE_USER + " INTEGER, " + COLUMN_GROWTH_USER
//                + " INTEGER, " + COLUMN_ACTIVE_USER + " INTEGER);");
//
//        // создаем таблицу DISH
//        db.execSQL("CREATE TABLE dish (" + COLUMN_ID_DISH
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_EATING_DISH
//                + " INTEGER, " + COLUMN_NAME_DISH + " TEXT, " + COLUMN_PROTEIN_DISH
//                + " INTEGER, " + COLUMN_FAT_DISH + " INTEGER, " + COLUMN_CARBOHYDRATE_DISH
//                + " INTEGER, " + COLUMN_CALORIE_DISH + " INTEGER, " + COLUMN_WEIGHT_DISH + " INTEGER);");
//
//        // создаем таблицу EATING
//        db.execSQL("CREATE TABLE eating (" + COLUMN_ID_EATING
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_USER_EATING
//                + " INTEGER, " + COLUMN_ID_DISH_EATING + " INTEGER, " + COLUMN_DATE_EATING
//                + " TEXT, " + COLUMN_WEIGHT_EATING + " INTEGER);");
//
//        // создаем таблицу CURRENT
//        db.execSQL("CREATE TABLE current (" + COLUMN_ID_CURRENT
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_USER_CURRENT
//                + " INTEGER, " + COLUMN_CURRENT_PROTEIN_CURRENT + " INTEGER, " + COLUMN_CURRENT_FAT_CURRENT
//                + " INTEGER, " + COLUMN_CURRENT_WATER_CURRENT + " INTEGER, " + COLUMN_CURRENT_CARBOHYDRATE_CURRENT
//                + " INTEGER, " + COLUMN_CURRENT_CALORIE_CURRENT + " INTEGER, " + COLUMN_DATE_CURRENT + " TEXT);");
//
//        // создаем таблицу TRAINING
//        db.execSQL("CREATE TABLE training (" + COLUMN_ID_TRAINING
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_DAY_TRAINING
//                + " INTEGER, " + COLUMN_USER_TRAINING + " INTEGER, " + COLUMN_DATE_TRAINING
//                + " TEXT, " + COLUMN_PERCENTAGE_COMPLETION_TRAINING + " INTEGER);");
//
//        // создаем таблицу TRAINING_PROGRAM
//        db.execSQL("CREATE TABLE training_program (" + COLUMN_ID_TRAINING_PROGRAM
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_TRAINING_PROGRAM
//                + " TEXT, " + COLUMN_DESCRIPTION_TRAINING_PROGRAM + " TEXT);");
//
//        // создаем таблицу EXERCISE
//        db.execSQL("CREATE TABLE exercise (" + COLUMN_ID_EXERCISE
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_EXERCISE
//                + " TEXT, " + COLUMN_NUMBER_APPROACHES_EXERCISE + " INTEGER, " + COLUMN_NUMBER_EXECUTIONS_EXERCISE
//                + " INTEGER);");
//
//        // создаем таблицу TRAINING_DAY
//        db.execSQL("CREATE TABLE training_day (" + COLUMN_ID_TRAINING_DAY
//                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID_EXERCISE_TRAINING_DAY
//                + " INTEGER, " + COLUMN_ID_PROGRAM_TRAINING_DAY + " INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EATING);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_PROGRAM);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_DAY);
//        onCreate(db);
    }

    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        Log.d("DataBaseHelper", DB_PATH);
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DATABASE_NAME);
                Log.d("DataBaseHelper", myContext.getFilesDir().getPath());
                // Путь к новой бд
                String outFileName = DB_PATH;
                Log.d("DataBaseHelper", DB_PATH);
                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DataBaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
