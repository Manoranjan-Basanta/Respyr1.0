package com.humorstech.respyr.authentication.profile_creation.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.utills.ProfileCreationData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Age extends AppCompatActivity {

    // buttons
    private Button buttonBack, buttonNext;
    private SingleDateAndTimePicker singleDateAndTimePicker;
    private static final String TAG = "Age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        StatusBarColor statusBarColor = new StatusBarColor(Age.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    private void init(){
        setReferences();
        setDatePicker();
        onClicks();
    }
    private void setReferences(){
        buttonBack=findViewById(R.id.button_back);
        buttonNext=findViewById(R.id.button_next);

        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,2);
    }

    private void setDatePicker(){
        singleDateAndTimePicker= findViewById(R.id.single_day_picker);

        singleDateAndTimePicker.setDisplayMinutes(false);
        singleDateAndTimePicker.setDisplayHours(false);
        singleDateAndTimePicker.setDisplayDays(false);
        singleDateAndTimePicker.setDisplayMonths(true);
        singleDateAndTimePicker.setDisplayYears(true);
        singleDateAndTimePicker.setDisplayDaysOfMonth(true);

        // Get the current date
        Date currentDate = new Date();
        singleDateAndTimePicker.setDefaultDate(currentDate);
    }

    private Date SelectedDate(){
        return  singleDateAndTimePicker.getDate();
    }
    private void onClicks(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date selectedDate = SelectedDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dateOfBirth = dateFormat.format(selectedDate);
                int age = calculateAge(selectedDate);

                if (age >=10 && age <=100){
                    try{
                        // store gender id for profile creation
                        SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE,
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(ProfileCreationData.DOB, dateOfBirth);
                        editor.putString(ProfileCreationData.AGE, String.valueOf(age));
                        editor.apply();

                        Intent intent= new Intent(getApplicationContext(), Height.class);
                        startActivity(intent);


                    }catch (Exception e){
                        Log.d(TAG, e.getMessage());
                    }
                }else
                    Toast.makeText(Age.this, "Age should be more than 10\nor less than 100", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int calculateAge(Date birthDate) {
        Calendar currentDate = Calendar.getInstance();
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        int age = currentDate.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (birthCalendar.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH) ||
                (birthCalendar.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                        birthCalendar.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

}