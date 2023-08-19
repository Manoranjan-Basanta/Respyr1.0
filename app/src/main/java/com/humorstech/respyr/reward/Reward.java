package com.humorstech.respyr.reward;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.humorstech.respyr.R;

public class Reward extends AppCompatActivity {

    RewardsSheet rewardsSheet = new RewardsSheet(Reward.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.primary));

        rewardsSheet.showCancelBottomSheet();

        init();
    }
    private void init(){

    }
}