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
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Button;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Set;

public class DeviceReady extends AppCompatActivity {


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    connectionStatus = true;
                    highFlag();
                    break;

                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    connectionStatus = false;
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




    //    usb
    private UsbService usbService;
    private MyHandler mHandler;
    private  boolean connectionStatus, isDeviceReady;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_ready);
        StatusBarColor statusBarColor= new StatusBarColor(DeviceReady.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        mHandler = new MyHandler(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String data = "%";
                if (usbService != null) {
                    usbService.write(data.getBytes());
                }


            }
        },1500);


    }
    private void moveActivity(){
        Intent intent = new Intent(getApplicationContext(), BloodPressure.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        setFilters();
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
    private void CalibrationCompleted(){
        moveActivity();
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
        private final WeakReference<DeviceReady> mActivity;

        public MyHandler(DeviceReady activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UsbService.MESSAGE_FROM_SERIAL_PORT) {
                String data = (String) msg.obj;
                String signal="null";
                if(data!=null){
                    signal = data.trim();
                }

                if (signal.equals("%") || signal.contains("%")) {
                    mActivity.get().isDeviceReady = true;
                    mActivity.get().moveActivity();
                    mActivity.get().CalibrationCompleted();
                }
            }
        }
    }



}