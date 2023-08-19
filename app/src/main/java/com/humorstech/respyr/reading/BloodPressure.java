package com.humorstech.respyr.reading;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.LoginData;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.reading.Math.Fft;
import com.humorstech.respyr.reading.Math.Fft2;
import com.humorstech.respyr.utills.BloodPressureResults;
import com.humorstech.respyr.utills.ReadingParameters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Set;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import cz.msebera.android.httpclient.Header;

public class BloodPressure extends AppCompatActivity {


    private UsbService usbService;
    private MyHandler mHandler;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    highFlag();
                    storeTime();
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    lowFlag();
                    break;
            }
        }
    };

    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    private String user_id="null";
    private String weight;
    private String height;
    private String waterIntake;
    private String gender;
    private String age;
    private String alcohol;
    private String smokingUnits;
    private String excr;
    private String exerciseInMinutes;
    private String foodName;
    private String foodQuantity;
    private String foodCount;
    private String isHadBreakfast;
    private String isHadLunch;
    private String isHadDinner;


    private void highFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", true);
        myEdit.apply();
    }
    private void storeTime(){

    }

    private void lowFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", false);
        myEdit.putBoolean("bflag2", false);
        myEdit.apply();

    }



    // references

    private TextView txtLog;
    private Button buttonStartTest;

    // stuffs

    // bp calculation variables
    double Agg=26, Hei = 176, Wei=65, Gen=1;
    double Q=4.5;

    private boolean stopPulseFlag=false;


    /// ppg variables
    private ArrayList<Double> GreenAvgList = new ArrayList<Double>();
    private ArrayList<Double> RedAvgList = new ArrayList<Double>();
    private int counter = 0;
    long startTime = 0 , endTime = 0, startWrongTime=0 , endWrongTime=0;
    private boolean startTimeFlag=false, endWrongTimeFlag=false, startWrongTimeFlag=false;
    private double SamplingFreq=0;
    private double totalTimeInSecs=0, wrongTime=0, wrongTime1=0;

    private  double bufferAvgB = 0, bufferAvgBr =0;
    private int Beats=0, Breath=0;

    private double HRFreq=0,HRFreq1=0;

    ArrayList<Double> wrongTimeArray =  new ArrayList<Double>();

    ArrayList<Double> tempBPM= new ArrayList<Double>();


    private boolean bpmCompletedFlag=false;
    private boolean isFirstTime=false;
    private boolean isFirstTime2=false;


    private int firstDataCounter;
    private String name;

    private CircularProgressIndicator circularProgressIndicator;


    // SPO2 variables
    private static final double RedBlueRatio = 0;
    double Stdr = 0;
    double Stdb = 0;
    double sumred = 0;
    double sumblue = 0;
    public int o2;

    double bpm1, bpm2;


    private  Intent intent2;

    private LinearLayout layoutGetStarted, layoutBloodPressure, layoutDoNotRemoveFinger, layoutRemoveFinger;

    private RelativeLayout layoutKeepYourFinger;
    private boolean removeFinger=false;



    private TextView txtVitalStatus;
    private TextView txtWaitingForYourFinger;
    private TextView txtWaitingForYourFinger2;


    boolean dataFlag = false;

    StringBuilder stringBuilder = new StringBuilder();

    private String loginId, profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        StatusBarColor statusBarColor= new StatusBarColor(BloodPressure.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        LoginData loginData = new LoginData(this);
        loginId = loginData.getLoginId();
        profileId = loginData.getProfileId();


        mHandler =new MyHandler(this);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();

        GreenAvgList.clear();
        RedAvgList.clear();

        Intent intent = getIntent();
        if (intent!=null){
            user_id=intent.getStringExtra("user_id");
        }


        SharedPreferences sharedPreferences = getSharedPreferences(ReadingParameters.TITLE,
                Context.MODE_PRIVATE);
        weight = sharedPreferences.getString(ReadingParameters.WEIGHT, null);
        height = sharedPreferences.getString(ReadingParameters.HEIGHT, null);
        gender = sharedPreferences.getString(ReadingParameters.GENDER, null);
        age = sharedPreferences.getString(ReadingParameters.AGE, null);
        waterIntake = sharedPreferences.getString(ReadingParameters.AGE, null);
        alcohol = sharedPreferences.getString(ReadingParameters.AGE, null);
        smokingUnits = sharedPreferences.getString(ReadingParameters.AGE, null);
        excr = sharedPreferences.getString(ReadingParameters.AGE, null);
        exerciseInMinutes = sharedPreferences.getString(ReadingParameters.AGE, null);
        foodName = sharedPreferences.getString(ReadingParameters.AGE, null);
        foodQuantity = sharedPreferences.getString(ReadingParameters.AGE, null);
        isHadBreakfast = sharedPreferences.getString(ReadingParameters.AGE, null);
        isHadLunch = sharedPreferences.getString(ReadingParameters.AGE, null);
        isHadDinner = sharedPreferences.getString(ReadingParameters.AGE, null);
        foodCount = sharedPreferences.getString(ReadingParameters.AGE, null);

        Hei = Double.parseDouble(height);
        Wei = Double.parseDouble(weight);
        Agg = Double.parseDouble(age);

        if (gender.equals("Female") || gender.equals("female")){
            Gen=2;
        }else{
            Gen=1;
        }
        if (Gen==1){
            Q=5;
        }

//        insertDailyRoutine();
//        updateTable();

    }

    private void init(){
        setReferences();
        onClicks();
    }


    private void setReferences(){
        txtLog=findViewById(R.id.txtLog);
        txtLog.setMovementMethod(new ScrollingMovementMethod());
        txtWaitingForYourFinger=findViewById(R.id.txt_waiting_for_your_finger);
        txtWaitingForYourFinger2=findViewById(R.id.txt_waiting_for_your_finger2);
        txtVitalStatus=findViewById(R.id.txt_vital_percent);

        buttonStartTest=findViewById(R.id.button_start_test);
        buttonStartTest.setEnabled(true);


        circularProgressIndicator=findViewById(R.id.circular_progress1);
        circularProgressIndicator.setMaxProgress(100);

        // layouts
        layoutGetStarted=findViewById(R.id.layout_start_test);
        layoutBloodPressure=findViewById(R.id.layout_blood_pressure);
        layoutKeepYourFinger=findViewById(R.id.layout_keep_finger);
        layoutDoNotRemoveFinger=findViewById(R.id.layout_do_not_remove_finger);
        layoutRemoveFinger=findViewById(R.id.layout_remove_finger);

    }

    private void onClicks(){
        buttonStartTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String data = "?";
                if (usbService != null) {
                    //Send data
                    usbService.write(data.getBytes());

                    // hide start layout
                    layoutGetStarted.setVisibility(View.GONE);
                    layoutKeepYourFinger.setVisibility(View.VISIBLE);

                }

            }
        });

        ImageButton buttonCancel=findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTest.show(BloodPressure.this, BloodPressure.this);
            }
        });
    }

    private void stopPPG(){

        endTime = System.currentTimeMillis()/1000;
        totalTimeInSecs = endTime - startTime;

        for (int i=0; i<wrongTimeArray.size(); i++){
            totalTimeInSecs = totalTimeInSecs - wrongTimeArray.get(i);
            txtLog.append("difference array-->"+String.valueOf(wrongTimeArray.get(i))+"\n");
        }

        txtLog.append("totalTimeInSecs-->"+String.valueOf(totalTimeInSecs)+"\n");
        txtLog.append("counter-->"+String.valueOf(counter)+"\n");

        int n = counter;
        int progress = (int) ((n * 100.0) / 1200);

        txtLog.append("progress-->"+String.valueOf(progress)+"\n");
        circularProgressIndicator.setCurrentProgress(progress);
        txtVitalStatus.setText(String.valueOf(progress)+"%");
        System.out.println(progress);



        if (counter == 1200){

            txtLog.append("start time-->"+String.valueOf(startTime)+"\n");
            txtLog.append("end time-->"+String.valueOf(endTime)+"\n");
            txtLog.append("counter-->"+String.valueOf(counter)+"\n");
            txtLog.append("Red Length-->"+String.valueOf(RedAvgList.size())+"\n");
            txtLog.append("Green Length-->"+String.valueOf(GreenAvgList.size())+"\n");


            bpmProcess();

        }

    }

    private void bpmProcess(){

        // pulse reading is completed
        bpmCompletedFlag=true;

        Double[] Green = GreenAvgList.toArray(new Double[GreenAvgList.size()]);
        Double[] Red = RedAvgList.toArray(new Double[RedAvgList.size()]);

        SamplingFreq = ( counter / 24.0);

        HRFreq = Fft.FFT(Green, counter, SamplingFreq);
        HRFreq1 = Fft.FFT(Red, counter, SamplingFreq);


        bpm1 = (int) Math.ceil(HRFreq * 60);
        bpm2 = (int) Math.ceil(HRFreq1 * 60);


        double finalBPM = 0;

        if (bpm1 >= 50 && bpm1 <= 200) {
            if (bpm2 >= 50 && bpm2 <= 200) {
                if (bpm1 == bpm2) {
                    finalBPM = (bpm1 + bpm2) / 2;
                } else {
                    double difference1 = bpm1 - bpm2;
                    double difference2 = bpm2 - bpm1;
                    if ((difference1 > 0 && difference1 < 30) || (difference2 > 0 && difference2 < 30)) {
                        finalBPM = (bpm1 + bpm2) / 2;
                    } else if (bpm1 > 60 && bpm1 < 120) {
                        finalBPM = bpm1;
                    } else if (bpm2 > 60 && bpm2 < 120) {
                        finalBPM = bpm2;
                    } else {
                        finalBPM = getNearestBPM(bpm1, bpm2);
                    }
                }
            } else {
                if (bpm1 >= 50 && bpm1 <= 140) {
                    finalBPM = bpm1;
                } else if (bpm2 >= 50 && bpm2 <= 140) {
                    finalBPM = bpm2;
                } else {
                    finalBPM = getNearestBPM(bpm1, bpm2);
                }
            }
        } else {
            if (bpm1 >= 50 && bpm1 <= 140) {
                finalBPM = bpm1;
            } else if (bpm2 >= 50 && bpm2 <= 140) {
                finalBPM = bpm2;
            } else {
                finalBPM = getNearestBPM(bpm1, bpm2);
            }
        }


        Beats = (int) finalBPM;

        ////////////////////////////////////////////////////////////////////////   Breath Rate

        double RRFreq = Fft2.FFT(Green, counter, SamplingFreq);
        double rrBPM1 = (int) Math.ceil(RRFreq * 24);
        double RR1Freq = Fft2.FFT(Red, counter, SamplingFreq);
        double rrBPM2 = (int) Math.ceil(RR1Freq * 24);


        if ((rrBPM1 > 10 || rrBPM1 < 24)) {
            if ((rrBPM2 > 10 || rrBPM2 < 24)) {

                bufferAvgBr = (rrBPM1 + rrBPM2) / 2;

            } else {

                bufferAvgBr = rrBPM1;
            }
        } else if ((rrBPM2 > 10 || rrBPM2 < 24)) {

            bufferAvgBr = rrBPM2;
        }



        Breath = (int)bufferAvgBr;

        ////////////////////////////////////////////////////////////////////////   Spo2

        double meanr = sumred / counter;
        double meanb = sumblue / counter;

        for (int i = 0; i < counter - 1; i++) {

            Double bufferb = Green[i];

            Stdb = Stdb + ((bufferb - meanb) * (bufferb - meanb));

            Double bufferr = Red[i];

            Stdr = Stdr + ((bufferr - meanr) * (bufferr - meanr));

        }

        double varr = Math.sqrt(Stdr / (counter - 1));
        double varb = Math.sqrt(Stdb / (counter - 1));

        double R = (varr / meanr) / (varb / meanb);

        double spo2 = 100 - 5 * (R);
        o2 = (int) (spo2);

        if (Beats<=55){
            Beats=60;
        }

        txtLog.append("Breath  ---> "+String.valueOf(Breath)+"\n");
        txtLog.append("HRFreq---> "+String.valueOf(HRFreq)+"\n");
        txtLog.append("HRFreq1---> "+String.valueOf(HRFreq1)+"\n");
        txtLog.append("Time---> "+String.valueOf(totalTimeInSecs)+"\n");
        txtLog.append("Sampling Freq---> "+String.valueOf(SamplingFreq)+"\n");
        txtLog.append("BPM1  ---> "+String.valueOf(bpm1)+"\n");
        txtLog.append("BPM2  ---> "+String.valueOf(bpm2)+"\n");
        txtLog.append("Beats  ---> "+String.valueOf(Beats)+"\n");
        txtLog.append("SPO2  ---> "+String.valueOf(o2)+"\n");



        SharedPreferences sharedPreferences = getSharedPreferences(BloodPressureResults.TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BloodPressureResults.BPM1, String.valueOf(bpm1));
        editor.putString(BloodPressureResults.BPM2, String.valueOf(bpm2));
        editor.putString(BloodPressureResults.FINAL_BPM, String.valueOf(Beats));
        editor.putString(BloodPressureResults.SPO2, String.valueOf(o2));
        editor.putString(BloodPressureResults.BREATH_RATE, String.valueOf(Breath));
        editor.putString(BloodPressureResults.SYSTOLIC, String.valueOf(BloodPressureCalculation.finalSP(Beats, Hei, Wei, Agg, gender)));
        editor.putString(BloodPressureResults.DIASTOLIC, String.valueOf(BloodPressureCalculation.finalDP(Beats, Hei, Wei, Agg, gender)));
        editor.putString(BloodPressureResults.PROFILE_ID, String.valueOf(profileId));
        editor.putString(BloodPressureResults.LOGIN_ID, String.valueOf(loginId));
        editor.apply();

    }

    private static double getNearestBPM(double bpm1, double bpm2) {
        double targetBPM = 70; // Set a default target BPM value
        double diff1 = Math.abs(bpm1 - targetBPM);
        double diff2 = Math.abs(bpm2 - targetBPM);

        // Return the nearest BPM value
        if (diff1 < diff2) {
            return bpm1;
        } else {
            return bpm2;
        }
    }


    private void goToBlow(){
        stop();
        Intent intent = new Intent(getApplicationContext(), BlowActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        setFilters();
        startService(UsbService.class, usbConnection, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);
    }

    private void stop(){
        String data = "?";
        if (usbService != null) {
            // if UsbService was correctly binded, Send data
            usbService.write(data.getBytes());
        }
    }

    private void setStatusBarColorLight(){
        StatusBarColor statusBarColor= new StatusBarColor(BloodPressure.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
    }
    private void setStatusBarColorDark(){
        StatusBarColor statusBarColor= new StatusBarColor(BloodPressure.this);
        statusBarColor.setDarkColor(getResources().getColor(R.color.black));
    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<BloodPressure> mActivity;

        public MyHandler(BloodPressure activity) {
            mActivity = new WeakReference<>(activity);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UsbService.MESSAGE_FROM_SERIAL_PORT) {
                String data = (String) msg.obj;

                performPulseReading(data);

            }
        }

        private void performPulseReading(String data){

            try {
                String[] dataArray = data.split("\\s*,\\s*");
                double green = Double.parseDouble(dataArray[0]);
                double red = Double.parseDouble(dataArray[1]);

                if (!mActivity.get().bpmCompletedFlag) {
                    ///////////////////////////////////////////////// when data threshold matches//////////////////////////////////////////////
                    if ((green >= 25000 && green < 70000) && (red >= 25000 && red < 70000)) {
                        mActivity.get().firstDataCounter++;

                        if (mActivity.get().firstDataCounter < 100){
                            mActivity.get().txtWaitingForYourFinger.setText("Analysing your finger....");
                            mActivity.get().txtWaitingForYourFinger2.setText("Analysing your finger....");
                        }

                        if (mActivity.get().firstDataCounter > 100) {
                            mActivity.get().txtLog.append("-----------------" + "\n");

                            mActivity.get().layoutKeepYourFinger.setVisibility(View.GONE);
                            mActivity.get().layoutDoNotRemoveFinger.setVisibility(View.GONE);
                            mActivity.get().layoutBloodPressure.setVisibility(View.VISIBLE);


                            mActivity.get().setStatusBarColorLight();


                            mActivity.get().sumred = mActivity.get().sumred + red;
                            mActivity.get().sumblue = mActivity.get().sumblue + green;

                            mActivity.get().isFirstTime = true;

                            if (!mActivity.get().startTimeFlag) {
                                mActivity.get().startTime = System.currentTimeMillis() / 1000;
                                mActivity.get().startTimeFlag = true;
                            }

                            if (mActivity.get().isFirstTime2) {
                                if (!mActivity.get().endWrongTimeFlag) {
                                    mActivity.get().endWrongTime = System.currentTimeMillis() / 1000;
                                    mActivity.get().endWrongTimeFlag = true;
                                    double wrongTime = (mActivity.get().endWrongTime - mActivity.get().startWrongTime);
                                    mActivity.get().wrongTimeArray.add(wrongTime);
                                }
                            }

                            mActivity.get().txtLog.append("Green-->" + String.valueOf(green) + "\n");
                            mActivity.get().txtLog.append("Red-->" + String.valueOf(red) + "\n");

                            mActivity.get().GreenAvgList.add(green);
                            mActivity.get().RedAvgList.add(red);
                            ++mActivity.get().counter;

                            mActivity.get().startWrongTimeFlag = false;
                            mActivity.get().stopPPG();
                        } else if (mActivity.get().isFirstTime) {
                            if (!mActivity.get().startWrongTimeFlag) { // only one time startTime
                                mActivity.get().startWrongTime = System.currentTimeMillis() / 1000;
                                mActivity.get().startWrongTimeFlag = true;
                                mActivity.get().endWrongTimeFlag = false;
                            }
                            mActivity.get().isFirstTime2 = true;
                        }
                    } else {  // when finger remove which pulse reading


                        mActivity.get().txtLog.append(String.valueOf(mActivity.get().isFirstTime) + "\n");
                        mActivity.get().txtLog.append(String.valueOf(mActivity.get().isFirstTime2) + "\n");

                        mActivity.get().firstDataCounter = 0;
                        if (mActivity.get().isFirstTime) { // not first time
                            if (!mActivity.get().startWrongTimeFlag) { // only one time startTime
                                mActivity.get().startWrongTime = System.currentTimeMillis() / 1000;
                                mActivity.get().startWrongTimeFlag = true;
                                mActivity.get().endWrongTimeFlag = false;
                            }

                            /// do not remove your finger
                            mActivity.get().txtLog.append("DO NOT REMOVE-->" + "\n");
                            mActivity.get().layoutBloodPressure.setVisibility(View.GONE);
                            mActivity.get().layoutDoNotRemoveFinger.setVisibility(View.VISIBLE);
                            mActivity.get().txtWaitingForYourFinger2.setText("Waiting for your finger....");
                            mActivity.get().setStatusBarColorDark();

                            mActivity.get().isFirstTime2 = true;
                        }
                    }
                } else { /// when pulse reading is completed
                    if (mActivity.get().counter >= 1200) {
                        if ((green >= 3000) && (red >= 3000)) {
                            mActivity.get().layoutBloodPressure.setVisibility(View.GONE);
                            mActivity.get().layoutRemoveFinger.setVisibility(View.VISIBLE);
                        } else {
                            if (!mActivity.get().removeFinger) {
                                mActivity.get().removeFinger = true;
                                mActivity.get().goToBlow();
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                Log.d("UsbService", e.getMessage());
            }


        }


    }


    private void updateTable(){
        RequestParams params = new RequestParams();
        params.put("id", user_id);
        params.put("name", name);
        params.put("gender", gender);
        params.put("age", age);
        params.put("height", height);
        params.put("weight", weight);
        params.put("water_intake", waterIntake);
        params.put("alcohol", alcohol);
        params.put("smoking", smokingUnits);
        params.put("exercise", excr);
        params.put("exercise_time", exerciseInMinutes);
        params.put("meals", "meals");
        params.put("food_name", foodName);
        params.put("food_quantity", foodQuantity);
        params.put("is_had_breakfast", isHadBreakfast);
        params.put("is_had_lunch", isHadLunch);
        params.put("is_had_dinner", isHadDinner);
        params.put("food_count", foodCount);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com/humors_app/app_final/update_test_data.php",params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
    }

    private void insertDailyRoutine(){
        RequestParams params = new RequestParams();
        params.put("login_id", loginId);
        params.put("profile_id", profileId);
        params.put("water_consumed", waterIntake);
        params.put("cigarettes_unit", smokingUnits);
        params.put("alcohol_unit", alcohol);
        params.put("exercise_minutes", exerciseInMinutes);
        params.put("sleep_hours", "8");
        params.put("food_intake",foodCount);
        params.put("food_name", foodName);
        params.put("food_quantity", foodQuantity);
        params.put("breakfast", isHadBreakfast);
        params.put("lunch", isHadLunch);
        params.put("dinner", isHadDinner);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.fetchProfilesOfLogin,params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            }
        });
    }



}


