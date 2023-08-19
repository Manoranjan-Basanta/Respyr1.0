package com.humorstech.respyr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.humorstech.respyr.authentication.login.Name;
import com.humorstech.respyr.profile.user.AddNewProfile;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ProfileGridAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private List<Map<String, Object>> profilesData;

    public ProfileGridAdapter(Activity activity, List<Map<String, Object>> profilesData) {
        this.context = activity.getApplicationContext();
        this.profilesData = profilesData;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return profilesData.size();
    }

    @Override
    public Object getItem(int position) {
        return profilesData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.profile_card_layout, parent, false);
            holder = new ViewHolder();
            holder.profileExistsLayout = convertView.findViewById(R.id.profileExistsLayout);
            holder.profileNotExistsLayout = convertView.findViewById(R.id.profileNotExistsLayout);
            holder.createProfileButton = convertView.findViewById(R.id.createProfileButton);
            holder.txtProfileName = convertView.findViewById(R.id.txt_profile_name);
            holder.txtAgeAndGender = convertView.findViewById(R.id.txt_age_and_gender);
            holder.profileBg = convertView.findViewById(R.id.profile_bg);
            holder.imgGender = convertView.findViewById(R.id.img_gender);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> profile = profilesData.get(position);
        boolean profileHav = (boolean) profile.get("profile_hav");
        String name  = (String) profile.get("name");
        String subTitle  = (String) profile.get("age") + " years";
        String profileId  = (String) profile.get("profile_id") ;
        String gender  = (String) profile.get("gender") ;






        int profileColor1 = ContextCompat.getColor(context, R.color.profile1);
        int profileColor2 = ContextCompat.getColor(context, R.color.profile2);
        int profileColor3 = ContextCompat.getColor(context, R.color.profile3);
        int profileColor4 = ContextCompat.getColor(context, R.color.profile4);

        // Create a color filter with the specified tint color and mode (SRC_ATOP in this case)
        PorterDuffColorFilter colorFilter1 = new PorterDuffColorFilter(profileColor1, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter2 = new PorterDuffColorFilter(profileColor2, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter3 = new PorterDuffColorFilter(profileColor3, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter4 = new PorterDuffColorFilter(profileColor4, PorterDuff.Mode.SRC_ATOP);

        // Apply the color filter as the background tint to the TextView


        if (Objects.equals(profileId, "profile1")){
            holder.profileBg.getBackground().setColorFilter(colorFilter1);
        }else if(Objects.equals(profileId, "profile2")){
            holder.profileBg.getBackground().setColorFilter(colorFilter2);
        }else if(Objects.equals(profileId, "profile3")){
            holder.profileBg.getBackground().setColorFilter(colorFilter3);
        }else if(Objects.equals(profileId, "profile4")){
            holder.profileBg.getBackground().setColorFilter(colorFilter4);
        }



        if (profileHav) {
            holder.profileExistsLayout.setVisibility(View.VISIBLE);
            holder.profileNotExistsLayout.setVisibility(View.GONE);
            holder.txtProfileName.setText(name);
            holder.txtAgeAndGender.setText(subTitle);


            if (gender!=null){
                if (gender.equals("male") || gender.equals("Male")){
                    holder.imgGender.setImageResource(R.drawable.profile_av1);
                }else if (gender.equals("female") || gender.equals("Female")){
                    holder.imgGender.setImageResource(R.drawable.profile_av2);
                }
            }

        } else {
            holder.profileExistsLayout.setVisibility(View.GONE);
            holder.profileNotExistsLayout.setVisibility(View.VISIBLE);
            // Handle button click to create profile
            holder.createProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,  AddNewProfile.class);
                    activity.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    static class ViewHolder {
        LinearLayout profileExistsLayout;
        LinearLayout profileNotExistsLayout;
        Button createProfileButton;
        TextView txtProfileName;
        TextView txtAgeAndGender;
        LinearLayout profileBg;
        ImageView imgGender;
    }
}

