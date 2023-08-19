package com.humorstech.respyr.reward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humorstech.respyr.R;

import java.util.ArrayList;

public class RewardCardAdapter extends ArrayAdapter<RewardCardModel> {

    public RewardCardAdapter(@NonNull Context context, ArrayList<RewardCardModel> rewardModelArrayList) {
        super(context, 0, rewardModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.reward_card, parent, false);
        }

        RewardCardModel rewardModel = getItem(position);
        ImageView txtRewardLogo = listitemView.findViewById(R.id.img_reward_card_logo);
        TextView txtRewardTitle = listitemView.findViewById(R.id.txt_reward_card_title);
        TextView txtRewardDate = listitemView.findViewById(R.id.txt_reward_card_date);

        txtRewardTitle.setText(rewardModel.getRewardTitle());
        txtRewardDate.setText(rewardModel.getRewardValidityDate());



        LinearLayout llRewardCard = listitemView.findViewById(R.id.ll_reward_card);;
        String rewardType = rewardModel.getRewardType();
        if (rewardType=="active"){
            llRewardCard.setBackground(getContext().getDrawable(R.drawable.reward_plain));
            txtRewardLogo.setImageResource(R.drawable.reward_icon_1);
        }else if (rewardType=="expiring_soon"){
            llRewardCard.setBackground(getContext().getDrawable(R.drawable.reward_expiring_soon));
            txtRewardLogo.setImageResource(R.drawable.reward_icon_1);
        }else if (rewardType=="expired"){
            llRewardCard.setBackground(getContext().getDrawable(R.drawable.reward_expired));
            txtRewardLogo.setImageResource(R.drawable.reward_icon_2);
        }



        return listitemView;
    }

}
