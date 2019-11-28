package com.autochip.myvehicle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class DentInfoPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;

    /*DentInfoPagerAdapter(Context context, int[] mResources, OnFragmentInteractionListener mListener, CircularProgressBar circularProgressBar) {
        this.context = context;
        this.mResources = mResources;
        this.mListener = mListener;
        this.circularProgressBar = circularProgressBar;
        mLayoutInflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dent_image_pager_item, container, false);
        return itemView;
    }
}
