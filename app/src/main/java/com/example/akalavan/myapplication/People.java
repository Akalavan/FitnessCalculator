package com.example.akalavan.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Akalavan on 21.01.2019.
 */

public class People implements Parcelable {

    private int gender;
    private int age;
    private int growth;
    private int weight, targetWeight;

    public People() {
    }

    public People(int gender, int age, int growth, int weight, int targetWeight) {
        this.gender = gender;
        this.age = age;
        this.growth = growth;
        this.weight = weight;
        this.targetWeight = targetWeight;
    }

    protected People(Parcel in) {
        gender = in.readInt();
        age = in.readInt();
        growth = in.readInt();
        weight = in.readInt();
        targetWeight = in.readInt();
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gender);
        dest.writeInt(age);
        dest.writeInt(growth);
        dest.writeInt(weight);
        dest.writeInt(targetWeight);
    }
}
