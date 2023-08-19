package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.utills.ReadingParameters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private Activity activity;
    private List<ProfileItem> dataItems;

    public GridAdapter(Context context,Activity activity,List<ProfileItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView1 = convertView.findViewById(R.id.text1);
            viewHolder.textView2 = convertView.findViewById(R.id.text2);
            viewHolder.imgView = convertView.findViewById(R.id.img_gender);
            viewHolder.profileCard = convertView.findViewById(R.id.profile_card);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ProfileItem dataItem = dataItems.get(position);

        viewHolder.textView1.setText(dataItem.getName());


        String gender = dataItem.getGender();
        String age = dataItem.getAge();
        int id = dataItem.getId();

        if (gender.equals("Male") || gender.equals("male")){
            viewHolder.imgView.setImageResource(R.drawable.ic_male);
        }else{
            viewHolder.imgView.setImageResource(R.drawable.ic_female);
        }

        viewHolder.textView2.setText(gender+", " + age + " years");

        viewHolder.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUserData(id);
            }
        });



        return convertView;
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imgView;
        LinearLayout profileCard;
    }
    private void fetchUserData(int id){

        RequestParams params = new RequestParams();
        params.put("id", id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors_app/app_final/fetch_test_data_by_id.php?",params,
                new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(context, "Please wait");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    // Get the first (and only) JSON object from the array
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    // Extract the values from the JSON object
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String gender = jsonObject.getString("gender");
                    String age = jsonObject.getString("age");
                    String height = jsonObject.getString("height");
                    String weight = jsonObject.getString("weight");
                    String waterIntake = jsonObject.getString("water_intake");
                    String alcohol = jsonObject.getString("alcohol");
                    String smoking = jsonObject.getString("smoking");
                    String exercise = jsonObject.getString("exercise");
                    String exerciseTime = jsonObject.getString("exercise_time");
                    String foodName = jsonObject.getString("food_name");
                    String foodQuantity = jsonObject.getString("food_quantity");
                    String isHadBreakfast = jsonObject.getString("is_had_breakfast");
                    String isHadLunch = jsonObject.getString("is_had_lunch");
                    String isHadDinner = jsonObject.getString("is_had_dinner");
                    String foodCount = jsonObject.getString("food_count");
                    String sleepHours = jsonObject.getString("food_count");

                    SharedPreferences sharedPreferences = context.getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ReadingParameters.WATER_INTAKE, String.valueOf(waterIntake));
                    editor.putString(ReadingParameters.SMOKING_UNITS, String.valueOf(smoking));
                    editor.putString(ReadingParameters.IS_TAKEN_ALCOHOL,  String.valueOf(alcohol));
                    editor.putString(ReadingParameters.SLEEP_HOURS,  String.valueOf(sleepHours));
                    editor.putString(ReadingParameters.EXERCISE_IN_MINUTES, String.valueOf(exerciseTime));
                    editor.putString(ReadingParameters.EXERCISE, String.valueOf(exercise));
                    editor.putString(ReadingParameters.FOOD_NAME, String.valueOf(foodName));
                    editor.putString(ReadingParameters.FOOD_QUANTITY, String.valueOf(foodQuantity));
                    editor.putString(ReadingParameters.FOOD_COUNT, String.valueOf(foodCount));
                    editor.putString(ReadingParameters.IS_HAD_BREAKFAST,  String.valueOf(isHadBreakfast));
                    editor.putString(ReadingParameters.IS_HAD_LUNCH, String.valueOf(isHadLunch));
                    editor.putString(ReadingParameters.IS_HAD_DINNER, String.valueOf(isHadDinner));
                    editor.putString(ReadingParameters.NAME, name);
                    editor.putString(ReadingParameters.GENDER, gender);
                    editor.putString(ReadingParameters.HEIGHT, height);
                    editor.putString(ReadingParameters.AGE, age);
                    editor.putString(ReadingParameters.WEIGHT, weight);
                    editor.apply();

                    Dialogs.hideLoadingDialog();

                    Intent intent = new Intent(context, BeforeReading.class);
                    context.startActivity(intent);
                    activity.finish();

                }catch (Exception e){
                    Dialogs.hideLoadingDialog();
                    Log.d("TAG", e.getMessage());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialogs.hideLoadingDialog();
            }

        });
    }
}

