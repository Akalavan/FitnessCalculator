package com.example.akalavan.myapplication.training;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Akalavan on 09.03.2019.
 */

public class Exercise implements Parcelable {
    private String name, description = "", sNumberApproaches, sNumberExecutions, day;
    private Integer weight, numberApproaches, numberExecutions, distance, time;

    public Exercise(String name, Integer weight, Integer numberApproaches, Integer numberExecutions) {
        this.name = name;
        this.weight = weight;
        this.numberApproaches = numberApproaches;
        this.numberExecutions = numberExecutions;
    }

    public Exercise(Integer numberApproaches, String name, Integer distance, Integer time) {
        this.numberApproaches = numberApproaches;
        this.name = name;
        this.distance = distance;
        this.time = time;
    }

    public Exercise(Integer numberApproaches, String name, Integer distance) {
        this.numberApproaches = numberApproaches;
        this.name = name;
        this.distance = distance;
    }

    public Exercise(String name, String sNumberExecutions, String sNumberApproaches, String description, String day) {
        this.name = name;
        this.sNumberExecutions = sNumberExecutions;
        this.sNumberApproaches = sNumberApproaches;
        this.description = description;
        this.day = day;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        description = in.readString();
        sNumberApproaches = in.readString();
        sNumberExecutions = in.readString();
        day = in.readString();
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readInt();
        }
        if (in.readByte() == 0) {
            numberApproaches = null;
        } else {
            numberApproaches = in.readInt();
        }
        if (in.readByte() == 0) {
            numberExecutions = null;
        } else {
            numberExecutions = in.readInt();
        }
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readInt();
        }
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readInt();
        }
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getNumberApproaches() {
        return numberApproaches;
    }

    public void setNumberApproaches(Integer numberApproaches) {
        this.numberApproaches = numberApproaches;
    }

    public Integer getNumberExecutions() {
        return numberExecutions;
    }

    public void setNumberExecutions(Integer numberExecutions) {
        this.numberExecutions = numberExecutions;
    }

    public String getsNumberApproaches() {
        return sNumberApproaches;
    }

    public void setsNumberApproaches(String sNumberApproaches) {
        this.sNumberApproaches = sNumberApproaches;
    }

    public String getsNumberExecutions() {
        return sNumberExecutions;
    }

    public void setsNumberExecutions(String sNumberExecutions) {
        this.sNumberExecutions = sNumberExecutions;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Упрожнение: '" + name + "', Вес =" + weight + ", подходов =" + numberApproaches +
                ", повторений =" + numberExecutions + ", описание =" + description + ", день =" + day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(sNumberApproaches);
        dest.writeString(sNumberExecutions);
        dest.writeString(day);
        if (weight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weight);
        }
        if (numberApproaches == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberApproaches);
        }
        if (numberExecutions == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberExecutions);
        }
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(distance);
        }
        if (time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(time);
        }
    }
}
