package com.humorstech.respyr.reward;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.humorstech.respyr.R;

import java.util.ArrayList;

public class RewardsSheet {

    Context context;
    Activity activity;
    private static final String TAG = "DisconnectedSheet";
    RewardsSheet(Activity activity){
        this.activity = activity;
        this.context = activity;
    }

    public void showCancelBottomSheet() {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.rewards_list_bottomsheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setDimAmount(0);
        bottomSheetDialog.setCanceledOnTouchOutside(false);


        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        ScrollView scrollView = view.findViewById(R.id.scroll_reward_page);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {


                }
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    // The bottom sheet is collapsed
                    // Do something here
//                    scrollView.fullScroll(View.FOCUS_UP);
                    scrollView.smoothScrollTo(0, 0);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Not used in this example

            }
        });


        // set bottom-sheet height
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        // get screen height in pixel
        int screenHeight = displayMetrics.heightPixels;
        float scale = context.getResources().getDisplayMetrics().density;

        /// header image size in dp
        int headerHeight = 380+33;

        // convert dp into pixel
        float pixels = headerHeight * scale + 0.5f;

        // set height
        bottomSheetBehavior.setPeekHeight(screenHeight-(int)pixels);

        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setSkipCollapsed(false);
        bottomSheetDialog.show();





        GridView gridRewards = view.findViewById(R.id.grid_rewards);
        ArrayList<RewardCardModel> courseModelArrayList = new ArrayList<RewardCardModel>();
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "active"));
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "expiring_soon"));
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "expiring_soon"));
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "expiring_soon"));
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "expired"));
        courseModelArrayList.add(new RewardCardModel("10% off on annual subs...","Validity till  29/07/2022", R.drawable.reward_icon_2, "expired"));


        ViewGroup.LayoutParams layoutParams = gridRewards.getLayoutParams();

        int arraySize = courseModelArrayList.size();
        if (arraySize % 2 != 0) {
            arraySize=arraySize+1;
        }

        arraySize = arraySize /2;
        int rowGap = arraySize * 30;
        arraySize = arraySize*150;
        float dp = arraySize+rowGap+30; // the value in dp

        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f); // the result in pixels

        layoutParams.height = px; //this is in pixels
        gridRewards.setLayoutParams(layoutParams);

        RewardCardAdapter adapter = new RewardCardAdapter(context, courseModelArrayList);
        gridRewards.setAdapter(adapter);



    }
}
