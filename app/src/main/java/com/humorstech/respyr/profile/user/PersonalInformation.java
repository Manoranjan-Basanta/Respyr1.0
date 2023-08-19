package com.humorstech.respyr.profile.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.humorstech.respyr.R;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class PersonalInformation extends AppCompatActivity {


    ImageButton buttonBack;

    private Button btnSave;

    ///////// personal information
    private int heightType=1; /// 1=cm, 2=feet;


    //// xml
    private EditText etUserName, etUserDateOfBirth, etUserAge, etHeight, etWeight,etWaterConsumption,etBMI;


    private static final String TAG = "PersonalInformation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnoal_information);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.primary));

        buttonBack =findViewById(R.id.button_back);

        init();

        scrollChange();

        showDatePicker();


    }
    private void init(){

        initVars();
        fetchUserInfo();
        radioButtonHeight();
        //ageCalculate();
    }

    private void initVars(){
        etUserName =findViewById(R.id.et_user_name);
        etUserDateOfBirth =findViewById(R.id.et_user_date_of_birth);
        etUserAge =findViewById(R.id.et_user_age);
        etHeight =findViewById(R.id.et_user_height);
        etWeight =findViewById(R.id.et_user_weight);
        etBMI =findViewById(R.id.et_user_bmi);
        etWaterConsumption =findViewById(R.id.et_user_water_consumption);
    }

    private void showDatePicker(){
        DatePickerSheet datePickerSheet =new  DatePickerSheet(PersonalInformation.this);

        ImageButton buttonShowPicker =findViewById(R.id.button_date_picker);
        buttonShowPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerSheet.show();
            }
        });

        btnSave=findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = datePickerSheet.getDate();

                Toast.makeText(PersonalInformation.this, String.valueOf(date), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void scrollChange(){

        ScrollView scrollView=findViewById(R.id.scrollview);
        TextView txtAppTitle =findViewById(R.id.txtPageTitle);
        LinearLayout llAppBar =findViewById(R.id.appbar);

        ColorStateList blackList = ColorStateList.valueOf(getResources().getColor(R.color.black));
        ColorStateList whiteList = ColorStateList.valueOf(getResources().getColor(R.color.white));

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Perform actions when the scroll position changes
                int scrollY = scrollView.getScrollY();
                // Add your logic here
                if (scrollY<=56){
                    txtAppTitle.setTextColor(getColor(R.color.white));
                    llAppBar.setBackgroundColor(getColor(R.color.primary));

                    buttonBack.setImageTintList(whiteList);

                }else{
                    txtAppTitle.setTextColor(getColor(R.color.black));
                    llAppBar.setBackgroundColor(getColor(R.color.white));

                    buttonBack.setImageTintList(blackList);
                }
            }
        });
    }


    private void radioButtonHeight(){
        RadioGroup radioGroupHeight =findViewById(R.id.radio_group_height);
        RadioButton radioButtonCm =findViewById(R.id.radio_button_cm);
        RadioButton radioButtonFeet =findViewById(R.id.radio_button_feet);

        if (heightType==1){
            radioButtonCm.setChecked(true);
            radioButtonFeet.setChecked(false);
        }else if(heightType==2){
            radioButtonCm.setChecked(false);
            radioButtonFeet.setChecked(true);
        }
    }
    private void fetchUserInfo(){
        String responseUserInfo = "{\"userId\":\"123456\",\"userName\":\"John Doe\",\"userGender\":\"Male\",\"userDateOfBirth\":\"1990-01-01\",\"userAge\":32,\"userHeight\":175.0,\"userWeight\":70,\"userWaterConsumption\":2000,\"userBMI\":22.86}";

        try {
            JSONObject userJson = new JSONObject(responseUserInfo);
            String userId = userJson.getString("userId");
            String userName = userJson.getString("userName");
            String userGender = userJson.getString("userGender");
            String userDateOfBirth = userJson.getString("userDateOfBirth");
            int userAge = userJson.getInt("userAge");
            double userHeight = userJson.getInt("userHeight");
            int userWeight = userJson.getInt("userWeight");
            int userWaterConsumption = userJson.getInt("userWaterConsumption");
            double userBMI = userJson.getDouble("userBMI");


            etUserName.setText(userName);
            etUserDateOfBirth.setText(userDateOfBirth);
            etUserAge.setText(String.valueOf(userAge));
            etHeight.setText(String.valueOf(userHeight));
            etWeight.setText(String.valueOf(userWeight));
            etWaterConsumption.setText(String.valueOf(userWaterConsumption));
            etBMI.setText(String.valueOf(userBMI));


        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }


    }

    private void ageCalculate(){
        // Get the current date
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate dob = LocalDate.of(2000, 12, 10);
            currentDate = LocalDate.now();
            // Calculate the age
            Period age = Period.between(dob, currentDate);
            etUserAge.setText(String.valueOf(age.getYears()));
        }
    }
}