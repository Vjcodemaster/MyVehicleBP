package com.autochip.myvehicle;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import app_utility.DataBaseHelper;
import app_utility.DatabaseHandler;
import app_utility.OnFragmentInteractionListener;
import app_utility.SubMenuRVData;

import static app_utility.StaticReferenceClass.ADD_ALL_DATA;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubMenuFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mMainCategory;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    DatabaseHandler dbHandler;
    RecyclerView recyclerViewSubMenu;
    BottomSheetBehavior sheetBehavior;
    FrameLayout flBottomSheet;
    ArrayList<String> alSubMenuName;

    MaterialTextView mtvBSDentCount, mtvBSCost, mtvBSTime;

    public SubMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubMenuFragment.
     */
    public static SubMenuFragment newInstance(String param1, String param2) {
        SubMenuFragment fragment = new SubMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMainCategory = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler = new DatabaseHandler(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_menu, container, false);
        alSubMenuName = new ArrayList<>(Arrays.asList(dbHandler.getSCByMCNameMainTable(mMainCategory).split(",")));
        initViews(view);

        return view;
    }


    private void initViews(View view) {
        recyclerViewSubMenu = view.findViewById(R.id.rv_sub_menu);
        LinearLayoutManager mLinearLayoutManager;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLinearLayoutManager = new GridLayoutManager(getContext(), 3);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In landscape
        } else {
            if (alSubMenuName.size() >= 8)
                mLinearLayoutManager = new GridLayoutManager(getContext(), 2);
            else
                mLinearLayoutManager = new GridLayoutManager(getContext(), 1);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In portrait
        }
        recyclerViewSubMenu.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        recyclerViewSubMenu.setHasFixedSize(true);
        recyclerViewSubMenu.setLayoutManager(mLinearLayoutManager);
        flBottomSheet = view.findViewById(R.id.bottom_sheet);

        mtvBSDentCount = flBottomSheet.findViewById(R.id.mtv_bs_dent_count);
        mtvBSCost = flBottomSheet.findViewById(R.id.mtv_bs_cost);
        mtvBSTime = flBottomSheet.findViewById(R.id.mtv_bs_time);

        sheetBehavior = BottomSheetBehavior.from(flBottomSheet);

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        loadItems();
        /*ArrayList<DataBaseHelper> alDB = new ArrayList<>(dbHandler.getSCFromSTByMCName(mMainCategory));
        ArrayList<String> alSubMenuName = new ArrayList<>();
        ArrayList<String> alSubMenuImagePath = new ArrayList<>();
        for (int i=0; i<alDB.size(); i++){
            alSubMenuName.add(alDB.get(i).get_sub_category());
            String sImagePath = alDB.get(i).get_image_path();
            if(sImagePath.contains(",")){
                sImagePath = alDB.get(i).get_image_path().split(",")[0];
            }
            alSubMenuImagePath.add(sImagePath);
        }*/



        /*final ArrayList<String> alSubMenuName = new ArrayList<>(Arrays.asList(dbHandler.getSCByMCNameMainTable(mMainCategory).split(",")));
        ArrayList<DataBaseHelper> alDB = new ArrayList<>(dbHandler.getImagePathFromServiceTable(mMainCategory));
        HashMap<String, SubMenuRVData> hmSCWithImagePath = new HashMap<>();
        float fTotalTime = 0;
        float fTotalCost = 0;
        int count = 0;
        for (int i = 0; i < alDB.size(); i++) {
            String sSC = alDB.get(i).get_sub_category();
            //String sIP = alDB.get(i).get_image_path();
            SubMenuRVData subMenuRVData = new SubMenuRVData(alDB.get(i).get_image_path(), alDB.get(i).get_total_dent_count(),
                    alDB.get(i).get_total_cost(), alDB.get(i).get_total_time());
            //hmSCWithImagePath.put(sSC, sIP);
            fTotalTime = fTotalTime + Float.valueOf(alDB.get(i).get_total_time());
            fTotalCost = fTotalCost + Float.valueOf(alDB.get(i).get_total_cost());
            count = count + alDB.get(i).get_total_dent_count();
            hmSCWithImagePath.put(sSC, subMenuRVData);
        }

        TextView tvDentCount = flBottomSheet.findViewById(R.id.mtv_bs_dent_count);
        TextView tvCost = flBottomSheet.findViewById(R.id.mtv_bs_cost);
        TextView tvTime = flBottomSheet.findViewById(R.id.mtv_bs_time);

        tvDentCount.setText(String.valueOf(count));
        tvCost.setText(String.valueOf(fTotalCost));
        tvTime.setText(String.valueOf(fTotalTime));

       *//* alSubMenuName.add("Roof");
        alSubMenuName.add("Hood");
        alSubMenuName.add("Front door left");
        alSubMenuName.add("Front door right");
        alSubMenuName.add("Back door left");
        alSubMenuName.add("Back door right");*//*

        //SubMenuRVAdapter imageViewRVAdapter = new SubMenuRVAdapter(getContext(), recyclerViewSubMenu, mMainCategory, alSubMenuName, alSubMenuImagePath);
        SubMenuRVAdapter imageViewRVAdapter = new SubMenuRVAdapter(getContext(), recyclerViewSubMenu, mMainCategory, alSubMenuName, hmSCWithImagePath);
        recyclerViewSubMenu.setAdapter(imageViewRVAdapter);*/
    }

    private void loadItems() {
        //final ArrayList<String> alSubMenuName = new ArrayList<>(Arrays.asList(dbHandler.getSCByMCNameMainTable(mMainCategory).split(",")));

        ArrayList<DataBaseHelper> alDB = new ArrayList<>(dbHandler.getImagePathFromServiceTable(mMainCategory));
        HashMap<String, SubMenuRVData> hmSCWithImagePath = new HashMap<>();
        float fTotalTime = 0;
        float fTotalCost = 0;
        int count = 0;
        for (int i = 0; i < alDB.size(); i++) {
            String sSC = alDB.get(i).get_sub_category();
            SubMenuRVData subMenuRVData = new SubMenuRVData(alDB.get(i).get_image_path(), alDB.get(i).get_total_dent_count(),
                    alDB.get(i).get_total_cost(), alDB.get(i).get_total_time());
            fTotalTime = fTotalTime + Float.valueOf(alDB.get(i).get_total_time());
            fTotalCost = fTotalCost + Float.valueOf(alDB.get(i).get_total_cost());
            count = count + alDB.get(i).get_total_dent_count();
            hmSCWithImagePath.put(sSC, subMenuRVData);
        }

        TextView tvDentCount = flBottomSheet.findViewById(R.id.mtv_bs_dent_count);
        TextView tvCost = flBottomSheet.findViewById(R.id.mtv_bs_cost);
        TextView tvTime = flBottomSheet.findViewById(R.id.mtv_bs_time);

        tvDentCount.setText(String.valueOf(count));
        tvCost.setText(String.valueOf(fTotalCost));
        tvTime.setText(String.valueOf(fTotalTime));

        mtvBSDentCount.setText(String.valueOf(count));
        mtvBSCost.setText(String.valueOf(fTotalCost));
        mtvBSTime.setText(String.valueOf(fTotalTime));

        SubMenuRVAdapter imageViewRVAdapter = new SubMenuRVAdapter(getContext(), recyclerViewSubMenu, mMainCategory, alSubMenuName, hmSCWithImagePath);
        recyclerViewSubMenu.setAdapter(imageViewRVAdapter);
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

}
