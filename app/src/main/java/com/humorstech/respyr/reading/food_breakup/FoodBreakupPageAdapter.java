package com.humorstech.respyr.reading.food_breakup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FoodBreakupPageAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 5;
    List<FoodBreakupData> foodItemList;
    public FoodBreakupPageAdapter(FragmentManager fragmentManager, List<FoodBreakupData> foodItemList) {
        super(fragmentManager);
        this.foodItemList=foodItemList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FoodBreakupCalories(foodItemList);
            case 1:
                return new FoodBreakupCarboHydrate(foodItemList);
            case 2:
                return new FoodBreakupProtein(foodItemList);
            case 3:
                return new FoodBreakupFats(foodItemList);
            case 4:
                return new FoodBreakupFibre(foodItemList);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}