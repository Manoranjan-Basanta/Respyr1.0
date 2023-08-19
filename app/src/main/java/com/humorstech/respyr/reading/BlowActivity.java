package com.humorstech.respyr.reading;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.utills.BloodPressureResults;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.lang.ref.WeakReference;
import java.util.Set;

import com.google.android.material.progressindicator.CircularProgressIndicator;


public class BlowActivity extends AppCompatActivity {

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    isConnected = true;
                    highFlag();
                    break;

                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    isConnected = false;
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

    private void highFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", true);
        myEdit.commit();
    }

    private void lowFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", false);
        myEdit.putBoolean("bflag2", false);
        myEdit.commit();
    }

    // Usb vars
    private UsbService usbService;
    private MyHandler mHandler;

    //// vars
    private Boolean isConnected = false;
    private TextView txtDisplayText;
    private StringBuffer stringBuffer = new StringBuffer();
    private LinearLayout  layoutAnalise;

    private RelativeLayout layoutTakeDeepBreath,layoutBlowNow;
    private static final String TAG = "BlowActivity";

    private boolean blowNowFlag=false;
    private int blowNowCounter=0;
    private int bFlag2;
    private String name;
    private long connectionStartTime = 0;

    /// form data
    private TextView txtAutoNext;
    private CountDownTimer blowTimer;

    private CircularProgressIndicator circularProgressIndicator1;
    private CircularProgressIndicator circularProgressIndicator2;
    private CircularProgressIndicator circularProgressIndicator3;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    private View view1;
    private View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blow);
        StatusBarColor statusBarColor= new StatusBarColor(BlowActivity.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        mHandler = new MyHandler(this);
        init();

    }

    private void stepper(){
         circularProgressIndicator1 =findViewById(R.id.analyse_progress1);
         circularProgressIndicator2 =findViewById(R.id.analyse_progress2);
         circularProgressIndicator3 =findViewById(R.id.analyse_progress3);

         imageView1 =findViewById(R.id.analyse_image1);
         imageView2 =findViewById(R.id.analyse_image2);
         imageView3 =findViewById(R.id.analyse_image3);

         view1=findViewById(R.id.analyse_view1);
         view2=findViewById(R.id.analyse_view2);

        setAnalysingProgress(1);

        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long taskDuration = 50 / 3; // Duration of each task (in seconds)
                int currentTask = (int)((50 - seconds) / taskDuration + 1); // Current task number



                // Perform actions based on the current task
                switch (currentTask) {
                    case 1:
                        setAnalysingProgress(1);
                        break;
                    case 2:
                        setAnalysingProgress(2);
                        break;
                    case 3:
                        setAnalysingProgress(3);
                        break;
                }
            }

            public void onFinish() {
                setAnalysingProgress(4);
            }
        }.start();



    }

    private void setAnalysingProgress(int i){
        // 1. Calculating your results
        // 2. Analysing your results
        // 3. Generating your report
        int active = R.drawable.uncheck_progress1;
        int inactive = R.drawable.uncheck_progress;
        int completed = R.drawable.check_progress;

        int colorCompleted = getResources().getColor(R.color.green);
        int colorInComplete = getResources().getColor(R.color.calories_progress_bg_active);


        switch (i){
            case 1 :
                circularProgressIndicator1.setVisibility(View.VISIBLE);
                circularProgressIndicator2.setVisibility(View.GONE);
                circularProgressIndicator3.setVisibility(View.GONE);

                imageView1.setImageResource(active);
                imageView2.setImageResource(inactive);
                imageView3.setImageResource(inactive);

                view1.setBackgroundTintList(ColorStateList.valueOf(colorInComplete));
                view2.setBackgroundTintList(ColorStateList.valueOf(colorInComplete));

                break;

            case 2 :
                circularProgressIndicator2.setVisibility(View.VISIBLE);
                circularProgressIndicator1.setVisibility(View.GONE);
                circularProgressIndicator3.setVisibility(View.GONE);

                imageView1.setImageResource(completed);
                imageView2.setImageResource(active);
                imageView3.setImageResource(inactive);


                view1.setBackgroundTintList(ColorStateList.valueOf(colorCompleted));
                view2.setBackgroundTintList(ColorStateList.valueOf(colorInComplete));

                break;
            case 3 :
                circularProgressIndicator3.setVisibility(View.VISIBLE);
                circularProgressIndicator2.setVisibility(View.GONE);
                circularProgressIndicator1.setVisibility(View.GONE);

                imageView1.setImageResource(completed);
                imageView2.setImageResource(completed);
                imageView3.setImageResource(active);



                view1.setBackgroundTintList(ColorStateList.valueOf(colorCompleted));
                view2.setBackgroundTintList(ColorStateList.valueOf(colorCompleted));

                break;
            case 4 :
                circularProgressIndicator3.setVisibility(View.GONE);
                circularProgressIndicator2.setVisibility(View.GONE);
                circularProgressIndicator1.setVisibility(View.GONE);

                imageView1.setImageResource(completed);
                imageView2.setImageResource(completed);
                imageView3.setImageResource(completed);

                view1.setBackgroundTintList(ColorStateList.valueOf(colorCompleted));
                view2.setBackgroundTintList(ColorStateList.valueOf(colorCompleted));

                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = getSharedPreferences("firstTime", MODE_PRIVATE);
        boolean s1 = sh.getBoolean("bflag1", false);
        boolean s2 = sh.getBoolean("bflag2", false);

        if (s1 && s2){
            bFlag2=0;
        }else if(s1 && !s2){
            bFlag2=1;
        }

    }

    private void init(){
        initVars();
        onClicks();
        autoNext();
    }

    private void initVars() {
        txtDisplayText = findViewById(R.id.txtDisplay);
        txtDisplayText.setMovementMethod(new ScrollingMovementMethod());

        txtAutoNext = findViewById(R.id.txt_auto_next);


        layoutTakeDeepBreath = findViewById(R.id.layout_take_deep_breath);
        layoutBlowNow = findViewById(R.id.layout_blow_now);
        layoutAnalise = findViewById(R.id.layout_analysing);


    }

    private void onClicks(){
        ImageButton buttonCancel=findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTest.show(BlowActivity.this, BlowActivity.this);
            }
        });
    }
    private void autoNext(){
        new CountDownTimer(6000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                txtAutoNext.setText("Auto Next..." + millisUntilFinished / 1000 +" seconds");
                // logic to set the EditText could go here
            }

            public void onFinish() {
                cancel();
            }

        }.start();
    }


    private void highFlag1(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag2", true);
        myEdit.apply();
    }

    private void blowNowStart(){
        layoutTakeDeepBreath.setVisibility(View.GONE);
        layoutBlowNow.setVisibility(View.VISIBLE);
        blowNowTimer();
        blowTimer.start();
    }
    private void analiseStart(){
        layoutBlowNow.setVisibility(View.GONE);
        layoutAnalise.setVisibility(View.VISIBLE);
        stepper();
        blowTimer.cancel();
    }
    private void finialStringProcess(String data){
        stringBuffer.append(data);
    }
    private void finalDo(String data){
        stringBuffer.append(data);
        txtDisplayText.append(String.valueOf(stringBuffer) +"\n");
        addDataToServer(String.valueOf(stringBuffer));
    }

    private void blowNowTimer(){
        TextView txtBlowWithin =findViewById(R.id.txt_blow_within);
         blowTimer = new CountDownTimer(12000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                int seconds = (int)millisUntilFinished / 1000;
                txtBlowWithin.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                abortReading();
            }
        };
    }

    private void abortReading(){
        String data = "#";
        if (usbService != null) {
            // if UsbService was correctly binded, Send data
            usbService.write(data.getBytes());
        }
        AbortBlow.show(BlowActivity.this, BlowActivity.this);
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
        private final WeakReference<BlowActivity> mActivity;

        public MyHandler(BlowActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = (String) msg.obj;
                    mActivity.get().txtDisplayText.append(data+"\n");

                    if (data.contains("blownow")){
                        mActivity.get().blowNowStart();
                    }else if(data.contains("analize")){
                        mActivity.get().analiseStart();
                    }else if (data.contains("$")){
                        mActivity.get().finialStringProcess(data);
                    }else if(data.contains("*")){
                        mActivity.get().finalDo(data);
                    }
            }
        }
    }



    public void addDataToServer(String testdata) {
        SharedPreferences sharedPreferences = getSharedPreferences(BloodPressureResults.TITLE, Context.MODE_PRIVATE);
        String profileId = sharedPreferences.getString(BloodPressureResults.PROFILE_ID, "0");
        String loginId = sharedPreferences.getString(BloodPressureResults.LOGIN_ID, "0");
        String finalBPM = sharedPreferences.getString(BloodPressureResults.FINAL_BPM, "0");
        String systolicPressure = sharedPreferences.getString(BloodPressureResults.SYSTOLIC, "0");
        String diastolicPressure = sharedPreferences.getString(BloodPressureResults.DIASTOLIC, "0");

        RequestParams params = new RequestParams();
        params.put("bflag" , bFlag2);
        params.put("bpm" , finalBPM);
        params.put("testdata" , testdata);
        params.put("subid" , loginId+"$"+profileId);
        params.put("sp" , systolicPressure);
        params.put("dp" , diastolicPressure);


        AsyncHttpClient client = new AsyncHttpClient();
        String url="https://humorstech.com/humorscalculation/production.php";


        client.get(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {

                txtDisplayText.append("Raw Data-->" + testdata + "\n");
                txtDisplayText.append("URL-->" + url + "\n");
                txtDisplayText.append("Params-->" + params + "\n");
            }


            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                highFlag1();
                String response = new String(responseBody);
                txtDisplayText.append("Response-->" +response+"\n");
                Intent intent = new Intent(getApplicationContext(), Result2.class);
                intent.putExtra("response", response);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                addDataToServer(testdata);
            }
        });
    }
}