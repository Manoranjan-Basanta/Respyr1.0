package com.humorstech.respyr.reading.food_breakup;

public class FoodBreakupData {

    int foodItemId;
    String foodName;
    String foodQuantity;
    String foodCategory;
    String foodImageLink;

    String foodCalories;
    String foodCarboHydrate;
    String foodProtein;
    String foodFats;
    String foodFibre;

    public FoodBreakupData(int foodItemId, String foodName, String foodQuantity, String foodCategory, String foodImageLink, String foodCalories, String foodCarboHydrate, String foodProtein, String foodFats, String foodFibre) {
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.foodCategory = foodCategory;
        this.foodImageLink = foodImageLink;
        this.foodCalories = foodCalories;
        this.foodCarboHydrate = foodCarboHydrate;
        this.foodProtein = foodProtein;
        this.foodFats = foodFats;
        this.foodFibre = foodFibre;
    }


    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodImageLink() {
        return foodImageLink;
    }

    public void setFoodImageLink(String foodImageLink) {
        this.foodImageLink = foodImageLink;
    }

    public String getFoodCalories() {
        return foodCalories;
    }

    public void setFoodCalories(String foodCalories) {
        this.foodCalories = foodCalories;
    }

    public String getFoodCarboHydrate() {
        return foodCarboHydrate;
    }

    public void setFoodCarboHydrate(String foodCarboHydrate) {
        this.foodCarboHydrate = foodCarboHydrate;
    }

    public String getFoodProtein() {
        return foodProtein;
    }

    public void setFoodProtein(String foodProtein) {
        this.foodProtein = foodProtein;
    }

    public String getFoodFats() {
        return foodFats;
    }

    public void setFoodFats(String foodFats) {
        this.foodFats = foodFats;
    }

    public String getFoodFibre() {
        return foodFibre;
    }

    public void setFoodFibre(String foodFibre) {
        this.foodFibre = foodFibre;
    }
}
