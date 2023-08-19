package com.humorstech.respyr.daily_routine.added;

public class AddedFoodData {
    private int id;
    private String foodName;
    private String foodCategory;
    private String foodQuantity;
    private String imageLink1;
    private String imageLink2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getImageLink1() {
        return imageLink1;
    }

    public void setImageLink1(String imageLink1) {
        this.imageLink1 = imageLink1;
    }

    public String getImageLink2() {
        return imageLink2;
    }

    public void setImageLink2(String imageLink2) {
        this.imageLink2 = imageLink2;
    }

    public AddedFoodData(int id, String foodName, String foodCategory, String foodQuantity, String imageLink1, String imageLink2) {
        this.id = id;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodQuantity = foodQuantity;
        this.imageLink1 = imageLink1;
        this.imageLink2 = imageLink2;
    }
}
