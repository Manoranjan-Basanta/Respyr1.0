package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.utills.ReadingParameters;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Set;

public class BeforeReading extends AppCompatActivity {

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


    private void highFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", true);
        myEdit.commit();
    }
    private void storeTime(){

    }

    private void lowFlag(){
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("bflag1", false);
        myEdit.putBoolean("bflag2", false);
        myEdit.commit();
    }





    ///////////////
    private ImageView imgConnectionStatus;
    private TextView txtConnectionStatus;
    private TextView txtConnectionStatus2;
    private Button buttonNext;
    private LinearLayout llDeviceConnected;
    private LinearLayout  llInsertCap;
    private TextView  txtLoading;
    CountDownTimer timer;
    NumberProgressBar numberProgressBar;

    SharedPreferences personalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_reading);
        StatusBarColor statusBarColor= new StatusBarColor(BeforeReading.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));
        mHandler = new MyHandler(this);
        init();

        TextView txtLog =findViewById(R.id.txt_log);

        SharedPreferences sharedPreferences = getSharedPreferences(ReadingParameters.TITLE, Context.MODE_PRIVATE);
        txtLog.append("Water Intake: " + sharedPreferences.getString(ReadingParameters.WATER_INTAKE, "") + "\n");
        txtLog.append("Cigarettes Count: " + sharedPreferences.getString(ReadingParameters.SMOKING_UNITS, "") + "\n");
        txtLog.append("Alcohol Consumption: " + sharedPreferences.getString(ReadingParameters.IS_TAKEN_ALCOHOL, "") + "\n");
        txtLog.append("Sleep Hours: " + sharedPreferences.getString(ReadingParameters.SLEEP_HOURS, "") + "\n");
        txtLog.append("Exercise Count: " + sharedPreferences.getString(ReadingParameters.EXERCISE_IN_MINUTES, "") + "\n");
        txtLog.append("Food Name: " + sharedPreferences.getString(ReadingParameters.FOOD_NAME, "") + "\n");
        txtLog.append("Food Quantity: " + sharedPreferences.getString(ReadingParameters.FOOD_QUANTITY, "") + "\n");
        txtLog.append("Food Count: " + sharedPreferences.getString(ReadingParameters.FOOD_COUNT, "") + "\n");
        txtLog.append("Had Breakfast: " + sharedPreferences.getString(ReadingParameters.IS_HAD_BREAKFAST, "") + "\n");
        txtLog.append("Had Lunch: " + sharedPreferences.getString(ReadingParameters.IS_HAD_LUNCH, "") + "\n");
        txtLog.append("Had Dinner: " + sharedPreferences.getString(ReadingParameters.IS_HAD_DINNER, "") + "\n");
        txtLog.append("Name: " + sharedPreferences.getString(ReadingParameters.NAME, "") + "\n");
        txtLog.append("Gender: " + sharedPreferences.getString(ReadingParameters.GENDER, "") + "\n");
        txtLog.append("Age: " + sharedPreferences.getString(ReadingParameters.AGE, "") + "\n");
        txtLog.append("Height: " + sharedPreferences.getString(ReadingParameters.HEIGHT, "") + "\n");
        txtLog.append("Weight: " + sharedPreferences.getString(ReadingParameters.WEIGHT, "") + "\n");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        initVars();
        onClicks();
        playVideo();
    }

    private void playVideo(){
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.place_cap));
        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            // Autoplay
            videoView.start();
            // Loop
            videoView.setOnCompletionListener(MediaPlayer::start);
        });

        videoView.setOnTouchListener((v, event) -> true);
        videoView.setMediaController(null);
    }

    private void initVars(){

        buttonNext=findViewById(R.id.button_next);


        imgConnectionStatus=findViewById(R.id.img_connection_status);
        txtConnectionStatus=findViewById(R.id.txt_connection_status);
        txtConnectionStatus2=findViewById(R.id.txt_connection_sub_status);
        numberProgressBar=findViewById(R.id.number_progress);



        llDeviceConnected =findViewById(R.id.device_connected_layout);
        llInsertCap =findViewById(R.id.insert_cap_layout);
        txtLoading =findViewById(R.id.txt_loading);
        llDeviceConnected.setVisibility(View.VISIBLE);
        llInsertCap.setVisibility(View.GONE);

    }


    private void onClicks(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoading();
            }
        });
    }
    private void startLoading(){

        llDeviceConnected.setVisibility(View.GONE);
        llInsertCap.setVisibility(View.VISIBLE);

        long duration = 10000; // 10 seconds in milliseconds
        long interval = 1; // Update interval in milliseconds

        numberProgressBar.setMax(100);

        timer = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                int percentage = (int) ((secondsRemaining / 10.0) * 100);
                int finalPercentage = 100 - percentage;

                numberProgressBar.setProgress(finalPercentage);
            }

            @Override
            public void onFinish() {
                moveToNext();
            }
        };

        timer.start();


    }
    private void moveToNext(){
        Intent intent = new Intent(getApplicationContext(), CheckList.class);
        startActivity(intent);
        finish();
    }




    @Override
    public void onResume() {
        super.onResume();
        // Start listening notifications from UsbService
        setFilters();
        // Start UsbService(if it was not started before) and Bind it
        startService(UsbService.class, usbConnection, null);

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
        private final WeakReference<BeforeReading> mActivity;

        public MyHandler(BeforeReading activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UsbService.MESSAGE_FROM_SERIAL_PORT) {
                String data = (String) msg.obj;
            }
        }

    }
}