package com.autochip.myvehicle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Objects;

import app_utility.Constants;
import app_utility.DataBaseHelper;
import app_utility.DatabaseHandler;
import app_utility.OnFragmentInteractionListener;
import app_utility.ZoomOutPageTransformer;

import static app_utility.StaticReferenceClass.UPDATE_IMAGE_PATH;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransitionFragment extends Fragment implements OnFragmentInteractionListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String IMAGE_PATH = "IMAGE_PATH";

    private String mParam1;
    private String mParam2;
    private int nDBID;

    public static OnFragmentInteractionListener mListener;

    private ViewPager viewPagerTransition;
    private LinearLayout llBubbleParentTransition;
    private ImageView ivRight, ivLeft;

    private ArrayList<String> alImagePath;
    private ZoomOutPageTransformer zoomOutPageTransformer;
    private ArrayList<ImageView> alBubbleViews = new ArrayList<>();
    private DatabaseHandler dbhandler;

    public TransitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1      Parameter 1.
     * @param param2      Parameter 2.
     * @param alImagePath Parameter 3.
     * @return A new instance of fragment TransitionFragment.
     */
    public static TransitionFragment newInstance(String param1, String param2, ArrayList<String> alImagePath) {
        TransitionFragment fragment = new TransitionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putStringArrayList(IMAGE_PATH, alImagePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            //getting db ID
            if (mParam1 != null)
                nDBID = Integer.valueOf(mParam1);
            else
                nDBID = -1;

            mParam2 = getArguments().getString(ARG_PARAM2);
            alImagePath = new ArrayList<>(Objects.requireNonNull(getArguments().getStringArrayList(IMAGE_PATH)));
        }
        mListener = this;
        dbhandler = new DatabaseHandler(getContext());
        zoomOutPageTransformer = new ZoomOutPageTransformer();
        //setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.change_image_transform));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transition, container, false);

        initViews(view);
        initListeners();
        updateViews();
        return view;
    }

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListeners();
        updateViews();
    }*/

    private void initViews(View view) {
        viewPagerTransition = view.findViewById(R.id.vp_image_transition);
        viewPagerTransition.setOffscreenPageLimit(alImagePath.size() - 1);

        llBubbleParentTransition = view.findViewById(R.id.ll_bubble_parent_transition);
        ivLeft = view.findViewById(R.id.iv_left);
        ivRight = view.findViewById(R.id.iv_right);
    }

    private void initListeners() {
        viewPagerTransition.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                zoomOutPageTransformer.transformPage(page, position);
            }
        });

        viewPagerTransition.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < alBubbleViews.size(); i++) {
                    if (i == position) {
                        alBubbleViews.get(i).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.bubble_solid, null));
                    } else {
                        alBubbleViews.get(i).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.bubble_holo, null));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerTransition.setCurrentItem(viewPagerTransition.getCurrentItem() - 1);
            }
        });

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerTransition.setCurrentItem(viewPagerTransition.getCurrentItem() + 1);
            }
        });
    }

    private void updateViews() {
        TransitionImagePagerAdapter dentInfoImagePagerAdapter = new TransitionImagePagerAdapter(getContext(), alImagePath);
        viewPagerTransition.setAdapter(dentInfoImagePagerAdapter);

        for (int i = 0; i < alImagePath.size(); i++) {
            addNewBubble();
        }
    }

    private void addNewBubble() {
        ImageView imageView;
        imageView = new ImageView(getContext());

        /*height and width of bubble*/
        LinearLayout.LayoutParams layoutParamsIV = new LinearLayout.LayoutParams(10, 10);
        layoutParamsIV.setMargins(5, 10, 5, 10);
        imageView.setLayoutParams(layoutParamsIV);
        if (alBubbleViews.size() == 0) {
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.bubble_solid, null));
        } else {
            imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.bubble_holo, null));
        }
        alBubbleViews.add(imageView);
        llBubbleParentTransition.addView(imageView);
    }

    private void removeBubble() {
        ImageView imageView = alBubbleViews.get(alBubbleViews.size() - 1);
        llBubbleParentTransition.removeView(imageView);
        alBubbleViews.remove(imageView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentChange(int nCase, String sCase, boolean isVisible, Uri uri) {

    }

    @Override
    public void onActivityToFragment(int nCase, String sCase, Uri uri) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case DELETE_IMAGE:
                int pos = viewPagerTransition.getCurrentItem();
                //TransitionImagePagerAdapter.onAdapterInterface.onDelete(pos);
                alImagePath.remove(pos);
                removeBubble();
                TransitionImagePagerAdapter dentInfoImagePagerAdapter = new TransitionImagePagerAdapter(getContext(), alImagePath);
                viewPagerTransition.setAdapter(dentInfoImagePagerAdapter);
                DentInfoFragment.onAdapterInterface.onDelete(pos);
                //dbhandler.updateImagePath(new DataBaseHelper(UPDATE_IMAGE_PATH, android.text.TextUtils.join(",", alImagePath)), nDBID);
                break;
        }
    }
}
