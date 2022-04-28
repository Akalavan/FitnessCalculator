package com.example.akalavan.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.akalavan.myapplication.eating.Eating;
import com.example.akalavan.myapplication.training.TrainingPlan;

/**
 * Created by Akalavan on 21.01.2019.
 */

public class Target implements Parcelable {

    private Eating eating;
    private TrainingPlan trainingPlan;
    private People people;
    private int targetWeight, targetProtein, targetFat, targetWater, targetCarbohydrate, targetCalorie,
            currentWeight, currentProtein, currentFat, currentWater, currentCarbohydrate, currentCalorie;
    private int active, actionSelection, step, coefficientGender;
    private double coefficientActive;



    protected Target(Parcel in) {
        people = in.readParcelable(People.class.getClassLoader());
        targetWeight = in.readInt();
//        targetProtein = in.readInt();
//        targetFat = in.readInt();
//        targetWater = in.readInt();
//        targetCarbohydrate = in.readInt();
//        targetCalorie = in.readInt();
//        currentWeight = in.readInt();
//        currentProtein = in.readInt();
//        currentFat = in.readInt();
//        currentWater = in.readInt();
//        currentCarbohydrate = in.readInt();
//        currentCalorie = in.readInt();
        active = in.readInt();
        step = in.readInt();
        actionSelection = in.readInt();
        trainingPlan = in.readParcelable(TrainingPlan.class.getClassLoader());
    }

    public Target(People people, int targetWeight, int active, int step, int actionSelection, TrainingPlan trainingPlan) {
      //  this.eating = eating;
        this.people = people;
        this.targetWeight = targetWeight;
        this.active = active;
        this.step = step;
        this.actionSelection = actionSelection;
        this.trainingPlan = trainingPlan;
    }


    public static final Creator<Target> CREATOR = new Creator<Target>() {
        @Override
        public Target createFromParcel(Parcel in) {
            return new Target(in);
        }

        @Override
        public Target[] newArray(int size) {
            return new Target[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(people, flags);
        dest.writeInt(targetWeight);
//        dest.writeInt(targetProtein);
//        dest.writeInt(targetFat);
//        dest.writeInt(targetWater);
//        dest.writeInt(targetCarbohydrate);
//        dest.writeInt(targetCalorie);
//        dest.writeInt(currentWeight);
//        dest.writeInt(currentProtein);
//        dest.writeInt(currentFat);
//        dest.writeInt(currentWater);
//        dest.writeInt(currentCarbohydrate);
//        dest.writeInt(currentCalorie);
        dest.writeInt(active);
        dest.writeInt(step);
        dest.writeInt(actionSelection);
        dest.writeParcelable(trainingPlan, flags);
    }

    void calculateBJU(){
        int weight = people.getWeight();
        int growth = people.getGrowth();
        int age = people.getAge();

        switch (active){
            case 0: coefficientActive = 1.7;
                break;
            case 1: coefficientActive = 1.6;
                break;
            case 2: coefficientActive = 1.2;
                break;
        }

        switch (step) {
            case 0: coefficientGender = 5;
                break;
            case 1: coefficientGender = -161;
                break;
        }
        Log.d("DataBaseHelper", String.valueOf(coefficientGender) + " SELECT");
        targetWater = weight * 30;
        double percentCarbohydrate = 0.0;
        double percentFat = 0.0;
        double percentProtein = 0.0;
        switch (actionSelection) {
            case 0:
                targetCalorie = (int) Math.round((10 * weight + 6.75 * growth - 5 * age + coefficientGender) * coefficientActive * 1.1);
                percentCarbohydrate = 0.55;
                percentFat = 0.15;
                percentProtein = 0.3;
                break;
            case 1:
                targetCalorie = (int) Math.round((10 * weight + 6.75 * growth - 5 * age + coefficientGender) * coefficientActive);
                percentCarbohydrate = 0.5;
                percentFat = 0.3;
                percentProtein = 0.2;
                break;
            case 2:
                targetCalorie = (int) Math.round((10 * weight + 6.75 * growth - 5 * age + coefficientGender) * coefficientActive * 0.9);
                percentCarbohydrate = 0.3;
                percentFat = 0.4;
                percentProtein = 0.3;
                break;
        }

        targetCarbohydrate = (int) (targetCalorie * percentCarbohydrate / 4);
        targetFat = (int) (targetCalorie * percentFat / 9);
        targetProtein = (int) (targetCalorie * percentProtein / 4);

    }

    public Eating getEating() {
        return eating;
    }

    public void setEating(Eating eating) {
        this.eating = eating;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getTargetProtein() {
        return targetProtein;
    }

    public void setTargetProtein(int targetProtein) {
        this.targetProtein = targetProtein;
    }

    public int getTargetFat() {
        return targetFat;
    }

    public void setTargetFat(int targetFat) {
        this.targetFat = targetFat;
    }

    public int getTargetWater() {
        return targetWater;
    }

    public void setTargetWater(int targetWater) {
        this.targetWater = targetWater;
    }

    public int getTargetCarbohydrate() {
        return targetCarbohydrate;
    }

    public void setTargetCarbohydrate(int targetCarbohydrate) {
        this.targetCarbohydrate = targetCarbohydrate;
    }

    public int getTargetCalorie() {
        return targetCalorie;
    }

    public void setTargetCalorie(int targetCalorie) {
        this.targetCalorie = targetCalorie;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getCurrentProtein() {
        return currentProtein;
    }

    public void setCurrentProtein(int currentProtein) {
        this.currentProtein = currentProtein;
    }

    public int getCurrentFat() {
        return currentFat;
    }

    public void setCurrentFat(int currentFat) {
        this.currentFat = currentFat;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public int getCurrentCarbohydrate() {
        return currentCarbohydrate;
    }

    public void setCurrentCarbohydrate(int currentCarbohydrate) {
        this.currentCarbohydrate = currentCarbohydrate;
    }

    public int getCurrentCalorie() {
        return currentCalorie;
    }

    public void setCurrentCalorie(int currentCalorie) {
        this.currentCalorie = currentCalorie;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public int getActionSelection() {
        return actionSelection;
    }

    public void setActionSelection(int actionSelection) {
        this.actionSelection = actionSelection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Target{" +
                "trainingPlan=" + trainingPlan +
                '}';
    }
}
