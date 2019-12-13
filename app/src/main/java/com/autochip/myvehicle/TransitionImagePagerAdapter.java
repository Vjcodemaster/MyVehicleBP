package com.autochip.myvehicle;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;

public class TransitionImagePagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> alImagePath;

    TransitionImagePagerAdapter(Context context, ArrayList<String> alImagePath){
        this.context = context;
        this.alImagePath = alImagePath;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*DentInfoImagePagerAdapter(Context context, int[] mResources, OnFragmentInteractionListener mListener, CircularProgressBar circularProgressBar) {
        this.context = context;
        this.mResources = mResources;
        this.mListener = mListener;
        this.circularProgressBar = circularProgressBar;
        mLayoutInflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    @Override
    public int getCount() {
        return alImagePath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dent_image_pager_item, container, false);
        ImageView ivDentImage = itemView.findViewById(R.id.iv_dent_image);

        ivDentImage.setImageURI(Uri.fromFile(new File(alImagePath.get(position))));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
