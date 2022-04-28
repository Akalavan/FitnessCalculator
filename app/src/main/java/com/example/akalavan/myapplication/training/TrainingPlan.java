package com.example.akalavan.myapplication.training;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.example.akalavan.myapplication.MainActivity;
import com.example.akalavan.myapplication.People;
import com.example.akalavan.myapplication.db.DataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akalavan on 09.03.2019.
 */

public class TrainingPlan implements Parcelable {
    private String name, description = "";
    private Integer day, timeout, actionSelection, id_plan;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public TrainingPlan(Integer gender, Integer actionSelection) {
      //  this.name = name;
      //  this.description = description;
     //   this.day = day;
        this.timeout = gender;
        this.actionSelection = actionSelection;

        init();
        Log.d("myLogs", exercises.size() + " TrainingPlan");
    }

    protected TrainingPlan(Parcel in) {
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            day = null;
        } else {
            day = in.readInt();
        }
        if (in.readByte() == 0) {
            timeout = null;
        } else {
            timeout = in.readInt();
        }
        if (in.readByte() == 0) {
            actionSelection = null;
        } else {
            actionSelection = in.readInt();
        }
        if (in.readByte() == 0) {
            id_plan = null;
        } else {
            id_plan = in.readInt();
        }
        final int N = in.readInt();
        for (int i = 0; i < N; i++) {
            Exercise dat = in.readParcelable(People.class.getClassLoader());;
            exercises.add(dat);
        }

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(description);
        if (day == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(day);
        }
        if (timeout == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(timeout);
        }
        if (actionSelection == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(actionSelection);
        }
        if (id_plan == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_plan);
        }
        if (exercises == null) {
            dest.writeByte((byte) 0);
        } else {
            final int N = exercises.size();
            dest.writeInt(N);
            if (N > 0) {
                for (Exercise exercise: exercises) {
                    dest.writeParcelable(exercise, flags);
                }
            }
        }
    }


    public static final Creator<TrainingPlan> CREATOR = new Creator<TrainingPlan>() {
        @Override
        public TrainingPlan createFromParcel(Parcel in) {
            return new TrainingPlan(in);
        }

        @Override
        public TrainingPlan[] newArray(int size) {
            return new TrainingPlan[size];
        }
    };

    public void init(){
        DataBaseHelper databaseHelper = MainActivity.getDatabaseHelper();
        SQLiteDatabase db = databaseHelper.open();
        Cursor userCursor;
        ContentValues cv;
        String selection = "id_program = ?";
        String orderBy = "day";
        String table = "training_day as TD inner join exercise as EX on TD.id_exercise = EX._id";
        String[] columns = {"EX.name as Name", "EX.number_approaches as approaches", "EX.number_executions as executions", "EX.comment as comment", "TD.day as day"};
        String[] selectionArgs = new String[0];
  //      selectionArgs[0] = "0";
        switch (timeout) {
            case 0:
                switch (actionSelection) {
                    case 0: selectionArgs = new String[]{"5"};
                        break;
                    case 1: selectionArgs = new String[]{"6"};
                        break;
                    case 2: selectionArgs = new String[]{"4"};
                        break;
                }
                break;
            case 1:
                switch (actionSelection) {
                    case 0: selectionArgs = new String[]{"2"};
                        break;
                    case 1: selectionArgs = new String[]{"3"};
                        break;
                    case 2: selectionArgs = new String[]{"1"};
                        break;
                }
                break;
        }
        if (selectionArgs.length > 0) {
            id_plan = Integer.parseInt(selectionArgs[0]);
            Log.d("DataBaseHelper", selectionArgs[0] + " init");
        }
        userCursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy);
        if (userCursor != null) {
            if (userCursor.moveToFirst()) {
                String str;
                String[] args;
                int i;
                do {
                    str = "";
                    args = new String[5];
                    i = 0;
                    for (String cn : userCursor.getColumnNames()) {
                        str = str.concat(cn + " = " + userCursor.getString(userCursor.getColumnIndex(cn)) + "; ");
                        args[i++] = userCursor.getString(userCursor.getColumnIndex(cn));
                    }
                    exercises.add(new Exercise(args[0], args[1], args[2], args[3], args[4]));
                    Log.d("DataBaseHelper", str);
                } while (userCursor.moveToNext());
            }
            userCursor.close();
        } else Log.d("DataBaseHelper", "Cursor is null");

        db.close();

//        if (actionSelection == 2) {
//            exercises.add(new Exercise(3, "Спринт", 100));
//            exercises.add(new Exercise("Пресс", 50, 3));
//            exercises.add(new Exercise("Бёрпи", 40, 3));
//        } else if (actionSelection == 1) {
//            exercises.add(new Exercise("Присяд", 50, 3, 10));
//            exercises.add(new Exercise("Жим штанги", 50, 3, 10));
//            exercises.add(new Exercise("Становая тяга", 50, 3, 10));
//        } else {
//            exercises.add(new Exercise("Присяд", 50, 3, 10));
//            exercises.add(new Exercise("Жим штанги", 50, 3, 10));
//            exercises.add(new Exercise("Становая тяга", 50, 3, 10));
//        }
        Log.d("myLogs", exercises.size() + " init");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Integer getActionSelection() {
        return actionSelection;
    }

    public void setActionSelection(Integer actionSelection) {
        this.actionSelection = actionSelection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getId_plan() {
        return id_plan;
    }

    @Override
    public String toString() {
        return "TrainingPlan{" +
                "name='" + name + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
