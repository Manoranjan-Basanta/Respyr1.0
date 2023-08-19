package com.humorstech.respyr.daily_routine.search;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.humorstech.respyr.daily_routine.added.AddedFoodData;

import java.util.ArrayList;
import java.util.List;

public class FoodDataBase extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "food.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "added_food_items";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_FOOD_NAME = "food_name";
        private static final String COLUMN_FOOD_CATEGORY = "food_category";
        private static final String COLUMN_FOOD_QUANTITY = "food_quantity";
        private static final String COLUMN_IMAGE_LINK1 = "image_link1";
        private static final String COLUMN_IMAGE_LINK2 = "image_link2";
        private Context context;

        public FoodDataBase(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
                this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER, " +
                        COLUMN_FOOD_NAME + " TEXT," +
                        COLUMN_FOOD_CATEGORY + " TEXT," +
                        COLUMN_FOOD_QUANTITY + " TEXT," +
                        COLUMN_IMAGE_LINK1 + " TEXT," +
                        COLUMN_IMAGE_LINK2 + " TEXT" +
                        ")";
                db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
        }


        public boolean insertFoodItem(int id, String foodName, String foodCategory, String foodQuantity, String imageLink1, String imageLink2) {
                SQLiteDatabase db = getWritableDatabase();

                // Check if the food name already exists in the database
                String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_FOOD_NAME + "=?";
                String[] selectionArgs = {foodName};
                Cursor cursor = db.rawQuery(query, selectionArgs);
                if (cursor.moveToFirst()) {
                        int count = cursor.getInt(0);
                        if (count > 0) {
                                // Food name already exists, return false indicating failure
                                cursor.close();
                                db.close();
                                return false;
                        }
                }
                cursor.close();

                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, id);
                values.put(COLUMN_FOOD_NAME, foodName);
                values.put(COLUMN_FOOD_CATEGORY, foodCategory);
                values.put(COLUMN_FOOD_QUANTITY, foodQuantity);
                values.put(COLUMN_IMAGE_LINK1, imageLink1);
                values.put(COLUMN_IMAGE_LINK2, imageLink2);
                long result = db.insert(TABLE_NAME, null, values);
                // db.close();

                if (result != -1) {
                        return true;
                } else {
                        return false;
                }
        }

        @SuppressLint("Range")
        public List<AddedFoodData> getAllFood() {
                List<AddedFoodData> foodList = new ArrayList<>();

                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

                if (cursor.moveToFirst()) {
                        do {
                                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME));
                                String category = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_CATEGORY));
                                String quantity = cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_QUANTITY));
                                String imageLink1 = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_LINK1));
                                String imageLink2 = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_LINK2));

                                AddedFoodData food = new AddedFoodData(id, name, category, quantity, imageLink1, imageLink2);
                                foodList.add(food);
                        } while (cursor.moveToNext());
                }

                cursor.close();
                //  db.close();

                return foodList;
        }

        public int getDataCount() {
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
                Cursor cursor = db.rawQuery(query, null);

                int count = 0;
                if (cursor.moveToFirst()) {
                        count = cursor.getInt(0);
                }

                cursor.close();
                db.close();

                return count;
        }

        public void clearTable() {
                SQLiteDatabase db = getWritableDatabase();
                db.execSQL("DELETE FROM " + TABLE_NAME);
                db.close();
        }

}

