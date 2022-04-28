package com.example.akalavan.myapplication.eating;

/**
 * Created by Akalavan on 21.01.2019.
 */

public class Eating {

    private int id;
    private String name;
    private int protein;
    private int fat;
    private int water;
    private int carbohydrate;
    private int calorie;

    public Eating() {
    }

    public Eating(int id, String name, int protein, int fat, int water, int carbohydrate, int calorie) {
        this.id = id;
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.carbohydrate = carbohydrate;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getId() {
        return id;
    }
}
