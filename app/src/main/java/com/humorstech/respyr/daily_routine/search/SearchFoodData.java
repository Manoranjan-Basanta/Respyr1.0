package com.humorstech.respyr.daily_routine.search;

public class SearchFoodData {
    private int itemId;
    private String foodName;
    private String foodCategory;
    private String foodImageLink;

    public SearchFoodData(int itemId, String foodName, String foodCategory, String foodImageLink){
        this.itemId = itemId;
        this.foodName = foodName;
        this.foodCategory = foodCategory;
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

    public String getFoodImageLink() {
        return foodImageLink;
    }

    public void setFoodImageLink(String foodImageLink) {
        this.foodImageLink = foodImageLink;
    }
}
