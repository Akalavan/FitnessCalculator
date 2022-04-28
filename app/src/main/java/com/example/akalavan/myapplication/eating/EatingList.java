package com.example.akalavan.myapplication.eating;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

import com.example.akalavan.myapplication.R;
import com.example.akalavan.myapplication.db.DataBaseHelper;

public class EatingList extends AppCompatActivity {
    private LinearLayout framesContainer;
    SQLiteDatabase db;
    Cursor userCursor;
    DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eating_list);
        databaseHelper = new DataBaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        framesContainer = (LinearLayout) findViewById(R.id.eatingList);

        userCursor = db.query(DataBaseHelper.TABLE_DISH, null, null, null, null, null, null);
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
                if (!userCursor.getString(idName).equals("Вода")){
                    ItemForEatingList frame = new ItemForEatingList(getApplicationContext(), new Eating(userCursor.getInt(idIndex), userCursor.getString(idName), userCursor.getInt(idProtein),
                            userCursor.getInt(idFat), 0, userCursor.getInt(idCarbohydrate),  userCursor.getInt(idCalorie)));
                    framesContainer.addView(frame);
                }
            } while (userCursor.moveToNext());
        }
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("DataBaseHelper onCreate", "EatingList " + str);


                } while (userCursor.moveToNext());
            }
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");
    }
}