package com.humorstech.respyr.authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.humorstech.respyr.R;



public class SliderAdapter extends PagerAdapter {
    private Context context;
    private int[] images = {R.drawable.welcome_1, R.drawable.welcome_1, R.drawable.welcome_1, R.drawable.welcome_1};
    private String[] titles = {"Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet"};
    private String[] subTitles = {
            "Lorem ipsum dolor sit amet consectetur. In mattis nunc sit adipiscing interdum neque.",
            "Lorem ipsum dolor sit amet consectetur. In mattis nunc sit adipiscing interdum neque.",
            "Lorem ipsum dolor sit amet consectetur. In mattis nunc sit adipiscing interdum neque.",
            "Lorem ipsum dolor sit amet consectetur. In mattis nunc sit adipiscing interdum neque."};

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.welcome_slider, container, false);


        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        TextView txtTitle =view.findViewById(R.id.txt_title);
        TextView txtSubTitle =view.findViewById(R.id.txt_sub_title);

        txtTitle.setText(titles[position]);
        txtSubTitle.setText(subTitles[position]);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
