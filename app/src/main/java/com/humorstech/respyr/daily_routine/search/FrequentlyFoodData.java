package com.humorstech.respyr.daily_routine.search;

public class FrequentlyFoodData {

    private int itemId;
    private String foodName;
    private String foodCategory;
    private String foodQuantity;
    private String foodImageLink;

    public FrequentlyFoodData(int itemId, String foodName, String foodCategory, String foodQuantity, String foodImageLink){
        this.itemId = itemId;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodQuantity = foodQuantity;
        this.foodImageLink = foodImageLink;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public String getFoodImageLink() {
        return foodImageLink;
    }

    public void setFoodImageLink(String foodImageLink) {
        this.foodImageLink = foodImageLink;
    }
}
