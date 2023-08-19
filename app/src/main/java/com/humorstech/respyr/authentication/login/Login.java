package com.humorstech.respyr.authentication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.profile_creation.TermsConditions;
import com.humorstech.respyr.utills.IntentUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yesterselga.countrypicker.CountryPicker;
import com.yesterselga.countrypicker.CountryPickerListener;
import com.yesterselga.countrypicker.Theme;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    private LinearLayout countryCodeSelector;
    private TextView txtCountryCode;
    private ImageView imgCountryFlag;
    private Button buttonContinue;
    private EditText etPhoneNo;
    private String countryCode;
    private String diaCode;
    private static final String TAG = "Login";
    private TextView txtError;
    private boolean isValidNumber=false;

    public Login(){
        countryCode = "+91$IN";
        diaCode = "+91";
    }
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarColor statusBarColor= new StatusBarColor(Login.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        init();

    }
    private void init(){
        initVars();
        onClicks();
    }
    private void initVars(){
        countryCodeSelector=findViewById(R.id.country_code_selector);
        imgCountryFlag=findViewById(R.id.img_country_flag);
        txtCountryCode=findViewById(R.id.txt_country_code);
        buttonContinue=findViewById(R.id.button_continue);
        etPhoneNo=findViewById(R.id.et_phone_no);
        txtError=findViewById(R.id.txt_error);
    }
    private void onClicks(){

        CountryPicker picker = CountryPicker.newInstance("Select Country", Theme.LIGHT);  // dialog title and theme

        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                picker.dismiss();
                txtCountryCode.setText(dialCode);
                imgCountryFlag.setImageResource(flagDrawableResID);
                countryCode = dialCode+"$"+code;
                diaCode = dialCode;

            }
        });

        countryCodeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "Select Country");

            }
        });

        imgCountryFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        txtCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtError.setVisibility(View.GONE);

                if (!etPhoneNo.getText().toString().isEmpty()){

                    if (isValidNumber){
                        checkPhoneNumber();
                    }
                }else{
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Please enter a 10-digit mobile number.");
                }
            }
        });

        etPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String phoneNumber = s.toString();
                if (s.length()==10){
                    if (matchMobilePhoneNumberPattern(phoneNumber)){
                        txtError.setVisibility(View.GONE);
                        txtError.setText("");
                        isValidNumber=true;
                    }else{
                        txtError.setVisibility(View.VISIBLE);
                        txtError.setText("The mobile number you entered is not valid. Please make sure you provide a 10-digit mobile number without any additional characters or spaces.");

                    }
                }else if(s.length() <=0){
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Please enter a 10-digit mobile number.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TextView txtTerms =findViewById(R.id.txt_terms_conditions);
        txtTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsConditions.class);
                startActivity(intent);
            }
        });

    }

    private void checkPhoneNumber(){

        countryCode = countryCode.replace("+", "");

        RequestParams params = new RequestParams();
        params.put("phone_no", etPhoneNo.getText().toString());
        params.put("country_code", countryCode);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.checkPhoneNumber, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                Dialogs.showLoadingDialog(Login.this, "Verifying..");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        String response = new String(responseBody).trim();
                        if (response.equals("existing_user")|| response.equals("new_user")){
                            Intent intent = new Intent(getApplicationContext(), OtpVerification.class);
                            intent.putExtra(IntentUtils.userPhoneNumber, etPhoneNo.getText().toString());
                            intent.putExtra(IntentUtils.userCountryCode, countryCode);
                            intent.putExtra(IntentUtils.userDialCode, diaCode);
                            intent.putExtra("response", response);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                        }


                    }
                },2000);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                checkPhoneNumber();
            }

            @Override
            public void onRetry(int retryNo) {
                checkPhoneNumber();
            }
        });
    }

    private static boolean matchMobilePhoneNumberPattern(String phoneNumber) {
        // Define the regular expression pattern for mobile phone numbers
        String pattern = "^\\d{10}$"; // Matches a ten-digit number

        // Create a Pattern object
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(phoneNumber);

        // Check if the phone number matches the pattern
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000); // Time window for double press in milliseconds (2 seconds in this example)

    }
}