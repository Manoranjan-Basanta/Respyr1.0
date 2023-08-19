package com.humorstech.respyr.authentication.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gne.www.lib.PinView;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.utills.IntentUtils;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.humorstech.respyr.utills.ProfileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.Text;

import java.util.Objects;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import cz.msebera.android.httpclient.Header;

public class OtpVerification extends AppCompatActivity {

    private String phoneNumber, countryCode, dialCode;
    private TextView txtMessage;
    private Button buttonNext;

    private static final String TAG = "OtpVerification";

    private String response;
    private TextView textOTP;
    private boolean isOTPSent ;
    private PinView otpView;
    private Button resendOTP;

    public OtpVerification(){
        isOTPSent=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        StatusBarColor statusBarColor= new StatusBarColor(OtpVerification.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private void init(){
        initVars();
        onClick();
        Intent intent = getIntent();
        if (intent!=null){
            phoneNumber = intent.getStringExtra(IntentUtils.userPhoneNumber);
            countryCode = intent.getStringExtra(IntentUtils.userCountryCode);
            dialCode = intent.getStringExtra(IntentUtils.userDialCode);
            response = intent.getStringExtra("response");
            // set message
            sendOTP();
        }
    }
    private void initVars(){
        txtMessage=findViewById(R.id.txt_message);
        buttonNext=findViewById(R.id.button_next);
        textOTP=findViewById(R.id.otp);
        otpView=findViewById(R.id.otp_view);
        resendOTP =findViewById(R.id.button_resend_otp);
    }
    private void onClick(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOTPSent && !otpView.getText().toString().isEmpty()){
                   verifyOTP(otpView.getText().toString());
                }
            }
        });
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });
    }

    private void sendOTP(){
        
        RequestParams params = new RequestParams();
        params.put("phone_no", phoneNumber);
        params.put("country_code", countryCode);
        
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.sendOTP,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(OtpVerification.this, "Sending OTP");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response1) {
                // called when response HTTP status is "200 OK"
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        String response = new String(response1).trim();
                        String[] parts = response.split("\\$");

                        if (Objects.equals(parts[0], "otp_sent")) {
                            Spanned message = Html.fromHtml("<font color='#535359'>An OTP has been sent to</font> <font color='#308BF9'>"+dialCode+phoneNumber+"</font>");
                            txtMessage.setText(message);
                            textOTP.setText(parts[1]);
                            isOTPSent=true;
                            startTimer();

                        }else{
                            Toast.makeText(OtpVerification.this, "Something went wrong while sending OTP\nplease try again later.", Toast.LENGTH_SHORT).show();
                        }

                    }
                },1500);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
    private void verifyOTP(String OTP){

        RequestParams params = new RequestParams();
        params.put("phone_no",phoneNumber);
        params.put("country_code",countryCode);
        params.put("otp",OTP);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.checkOTP,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(OtpVerification.this, "Verifying OTP....");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response2) {
                // called when response HTTP status is "200 OK"
                    String response = new String(response2).trim();
                    String[] parts = response.split("\\$");
                    if (parts[0].equals("true")){
                       checkProfileCount(parts[1]);
                    }else{
                        Toast.makeText(OtpVerification.this, "OTP not matching", Toast.LENGTH_SHORT).show();
                        Dialogs.hideLoadingDialog();
                    }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
    private void startTimer(){
        CircularProgressIndicator progress =findViewById(R.id.progress_otp_time);
        TextView txtTime =findViewById(R.id.txts_otp_time);


        progress.setMaxProgress(30);
        progress.setCurrentProgress(0);
        disableOTPButton(true,resendOTP);
        txtTime.setText("30");

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                System.out.println("Remaining seconds: " + seconds);

                progress.setCurrentProgress(30-(int)seconds);
                txtTime.setText(String.valueOf((int)seconds));
            }

            public void onFinish() {
                disableOTPButton(false,resendOTP);
            }
        };

        countDownTimer.start();
    }


    private void disableOTPButton(boolean disable, Button button){
        if (disable){
            button.setEnabled(false);
            button.setBackground(getResources().getDrawable(R.drawable.eclips1));
            button.setTextColor(getColor(R.color.grey));

            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.light));

        }else{
            button.setEnabled(true);
            button.setBackground(getResources().getDrawable(R.drawable.eclips1));
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));
            button.setTextColor(getColor(R.color.white));
        }
    }

    private void checkProfileCount(String login_id){

        RequestParams params = new RequestParams();
        params.put("login_id",login_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.checkProfileCount,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response3) {
                // called when response HTTP status is "200 OK"

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        String response = new String(response3).trim();

                        SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.putString(LoginUtils.LOGIN_ID, login_id);
                        editor2.putString(LoginUtils.PHONE_NUMBER, phoneNumber);
                        editor2.apply();


                        Intent intent;
                        // if response equals = 0 means -->new user
                        if (response.equals("0")){
                            // store login id for profile creation
                            SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(ProfileCreationData.LOGIN_ID, login_id);
                            editor.apply();

                            intent = new Intent(getApplicationContext(), Name.class);

                        }else{
                            intent = new Intent(getApplicationContext(), SelectProfile.class);
                        }
                        startActivity(intent);

                    }
                },1500);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

        });
    }


}