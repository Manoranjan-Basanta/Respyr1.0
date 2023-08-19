package com.humorstech.respyr;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeroBannerAdapter extends PagerAdapter {
    private List<HeroBannerImageModel> items = new ArrayList<>();
    private Context context;

    public HeroBannerAdapter(Context context, List<HeroBannerImageModel> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        HeroBannerImageModel item = items.get(position);
        imageView.setImageResource(item.getImageResId());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
