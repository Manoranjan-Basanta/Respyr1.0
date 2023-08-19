package com.humorstech.respyr.authentication.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.KeyboardUtils;
import com.humorstech.respyr.MainActivity;
import com.humorstech.respyr.NetWork;
import com.humorstech.respyr.NetworkErrorSheet;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.profile_creation.personal.Gender;
import com.humorstech.respyr.utills.IntentUtils;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.humorstech.respyr.utills.ProfileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

public class Name extends AppCompatActivity {

    private Button buttonNext;
    private EditText etName, etEmail;
    private static final String TAG = "Name";

    private TextView txtEmailError, txtNameError;
    private boolean isEmailValid=false;
    private boolean isNameFill=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        StatusBarColor statusBarColor= new StatusBarColor(Name.this);
        statusBarColor.setColor(getResources().getColor(R.color.light_secondary));
        init();
    }

    private void init(){
        initVars();
        emailValidation();
        onClick();
    }
    private void initVars(){
        buttonNext=findViewById(R.id.button_next);
        etName=findViewById(R.id.et_name);
        etEmail=findViewById(R.id.et_email);
        txtEmailError=findViewById(R.id.txt_email_error);
        txtNameError=findViewById(R.id.txt_name_error);
    }

    private void emailValidation(){
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etEmail.getText().toString().isEmpty()){
                    txtEmailError.setText("Please enter email address");
                    isEmailValid=false;
                }else{
                    txtEmailError.setText("");
                    if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
                        txtEmailError.setText("");
                        isEmailValid=true;
                    }else{
                        txtEmailError.setText("Please enter valid email address");
                        isEmailValid=false;
                    }
                }

            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etName.getText().toString().isEmpty()){
                    txtNameError.setText("Please enter name");
                    isNameFill=false;
                }else{
                    txtNameError.setText("");
                    isNameFill=true;
                }
            }
        });
    }
    private void onClick(){

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWork.isNetworkAvailable(Name.this)){
                    if (isNameFill && isEmailValid){
                        checkEmail(etEmail.getText().toString());
                    }
                }else{
                    NetworkErrorSheet.show(Name.this, Name.this, Name.class);
                }
            }
        });
    }

    private void checkEmail(String email){
        RequestParams params = new RequestParams();
        params.put("email", email);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.checkEmailExist,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(Name.this, "Please wait...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody).trim();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        if (response.equals("exist")){
                            txtEmailError.setText("Entered email address is already exist");
                        }else{
                            txtEmailError.setText("");

                            // store login name for profile creation
                            SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(ProfileCreationData.NAME, etName.getText().toString());
                            editor.putString(ProfileCreationData.EMAIL, etEmail.getText().toString());
                            editor.apply();


                            Intent intent = new Intent(getApplicationContext(), Gender.class);
                            startActivity(intent);
                        }

                    }
                },1000);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialogs.hideLoadingDialog();
                if (error instanceof ConnectTimeoutException || error instanceof SocketTimeoutException) {
                    // Handle the timeout error
                    // Display an appropriate message to the user
                    Toast.makeText(Name.this, String.valueOf(error), Toast.LENGTH_SHORT).show();

                } else {
                    // Handle other types of errors (e.g., network error, server error)
                    // Display an appropriate message to the user
                    Toast.makeText(Name.this, String.valueOf("Something went wrong!!\nplease check your internet connection or try again later."), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtils.hideKeyboard(Name.this);

    }
}