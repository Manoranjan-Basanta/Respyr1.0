package com.humorstech.respyr.authentication;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class InfinitePagerAdapter extends PagerAdapter {
    private PagerAdapter adapter;

    public InfinitePagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getCount() {
        // Set the count to a very large value to make it practically infinite
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Calculate the actual position based on the adapter's count
        int adapterPosition = position % adapter.getCount();
        return adapter.instantiateItem(container, adapterPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Calculate the actual position based on the adapter's count
        int adapterPosition = position % adapter.getCount();
        adapter.destroyItem(container, adapterPosition, object);
    }
}
