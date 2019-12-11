package com.autochip.myvehicle;

/*
 * Created by Vj on 30-Mar-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import app_utility.OnFragmentInteractionListener;

class DentImagePagerAdapter extends PagerAdapter {

    private Activity aActivity;
    private LayoutInflater mLayoutInflater;
    private int[] mResources;
    private int mnResources = 3;
    private OnFragmentInteractionListener mListener;

    DentImagePagerAdapter() {
        mLayoutInflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mnResources;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dent_image_pager_adapter, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
