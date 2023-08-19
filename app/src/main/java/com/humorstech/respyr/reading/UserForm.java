package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.daily_routine.DailyRoutinForm;
import com.humorstech.respyr.utills.FriendsReadingParameters;
import com.humorstech.respyr.utills.ReadingParameters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class UserForm extends AppCompatActivity {


    private EditText editTextName;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextAge;
    private Button buttonSubmit;

    private RadioButton radioHeightType;

    private boolean heightInFt=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        StatusBarColor statusBarColor= new StatusBarColor(UserForm.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        // Reference the views by their IDs
        editTextName = findViewById(R.id.editTextName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        // Set a click listener for the Submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                //   Validate the entered values
                if (isFormValid()) {
                    // Retrieve the entered values
                    String name = editTextName.getText().toString();
                    String gender = radioButtonMale.isChecked() ? "Male" : "Female";
                    String weight = editTextWeight.getText().toString();
                    String age = editTextAge.getText().toString();


                    double heightCm ;

                    if (heightInFt){
                        double heightFeet = Double.parseDouble(editTextHeight.getText().toString());
                        heightCm = heightFeet * 30.48;
                    }else{
                        heightCm = Double.parseDouble(editTextHeight.getText().toString());
                    }

                    String height = String.valueOf(heightCm);

                    addDataToServer(name,gender,age,height,weight);
                } else {
                    // Show an error message if the form is not valid
                    Toast.makeText(UserForm.this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radio_height_type);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                if (checkedRadioButton != null) {
                    String selectedOption = checkedRadioButton.getText().toString();
                    if (selectedOption.equals("Cm")){
                        heightInFt=false;
                    }else if(selectedOption.equals("Ft")){
                        heightInFt=true;
                    }
                }
            }
        });

    }

    private boolean isFormValid() {
        // Perform form validation
        boolean isValid = true;

        String name = editTextName.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || age.isEmpty()) {
            isValid = false;
        }

        return isValid;
    }

    private void addDataToServer(String name, String gender, String age, String height, String weight){

        // https://humorstech.com//humors_app/app_final/
        // test_insert_data.php?name=John&gender=male&age=25&height=180&weight=75&water_intake=2000&alcohol=0&smoking=hjs&exercise=yes&exercise_time=30&meals=3


        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("gender", gender);
        params.put("age", age);
        params.put("height", height);
        params.put("weight", weight);
        params.put("water_intake", "null");
        params.put("alcohol", "null");
        params.put("smoking","null");
        params.put("exercise", "null");
        params.put("exercise_time", "null");
        params.put("alcohol", "null");
        params.put("meals", "null");


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com//humors_app/app_final/test_insert_data.php?",params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(UserForm.this, "Please wait....");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Dialogs.hideLoadingDialog();
                        String response = new String(responseBody).trim();
                        if (!response.contains("Error")){

                            String[] parts = response.split("\\$");

                            SharedPreferences sharedPreferences = getSharedPreferences(FriendsReadingParameters.TITLE,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(FriendsReadingParameters.NAME, name);
                            editor.putString(FriendsReadingParameters.USER_ID, parts[1]);
                            editor.putString(FriendsReadingParameters.GENDER, gender);
                            editor.putString(FriendsReadingParameters.AGE, age);
                            editor.putString(FriendsReadingParameters.WEIGHT, weight);
                            editor.putString(FriendsReadingParameters.HEIGHT, height);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), DailyRoutinForm.class);
                            intent.putExtra("data", 2);
                            startActivity(intent);
                        }

                    }
                },1000);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Dialogs.hideLoadingDialog();
            }

        });
    }

}