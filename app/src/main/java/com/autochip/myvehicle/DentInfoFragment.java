package com.autochip.myvehicle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import app_utility.CircularProgressBar;
import app_utility.Constants;
import app_utility.DataBaseHelper;
import app_utility.DatabaseHandler;
import app_utility.DentsRVData;
import app_utility.OnAdapterInterface;
import app_utility.OnFragmentInteractionListener;
import app_utility.StaticReferenceClass;
import app_utility.ZoomOutPageTransformer;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

import static app_utility.StaticReferenceClass.ADD_ALL_DATA;
import static app_utility.StaticReferenceClass.MENU_ITEM_DELETE;
import static app_utility.StaticReferenceClass.MENU_ITEM_SAVE;
import static app_utility.StaticReferenceClass.REQUEST_GALLERY_CODE;
import static app_utility.StaticReferenceClass.SUPER_BACK_PRESS;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DentInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DentInfoFragment extends Fragment implements OnAdapterInterface, OnFragmentInteractionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String sMainCategory;
    private String sSubCategory;

    private ImageView ivGallery, ivCamera, ivDentView;
    private MaterialTextView tvAddDents;
    private MaterialTextView mtvTotalTime;
    private MaterialTextView mtvTotalCost;
    private TextView tvPhotoStatus;

    private LinearLayout llBubbleParent;
    private ImageView[] imageViews;
    private ArrayList<ImageView> alBubbleViews = new ArrayList<>();
    public static OnFragmentInteractionListener mListener;
    public static OnAdapterInterface onAdapterInterface;

    DatabaseHandler dbHandler;

    PhotoEditorView mPhotoEditorView;

    PhotoEditor mPhotoEditor;

    RecyclerView recyclerViewDentsInfo;
    private ViewPager mViewPagerSlideShow;
    DentsRVAdapter dentsRVAdapter;
    FloatingActionButton fabDelete;
    int nScrollIndex = 0;
    ArrayList<String> alImagePath = new ArrayList<>();
    ArrayList<DentsRVData> alDentsData = new ArrayList<>();

    DentInfoImagePagerAdapter dentInfoImagePagerAdapter;
    HashMap<Integer, DentsRVData> hmDents = new HashMap<>();
    private LinkedHashMap<Integer, DentsRVData> lhmDents = new LinkedHashMap<>();

    float fTotalTime;
    float fTotalCost;
    int count = 0;

    String sTotalTime;
    String sTotalCost;

    boolean isDataInDB = false;
    int nDBID;

    public DentInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DentInfoFragment.
     */
    public static DentInfoFragment newInstance(String param1, String param2) {
        DentInfoFragment fragment = new DentInfoFragment();
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
            sMainCategory = getArguments().getString(ARG_PARAM1);
            sSubCategory = getArguments().getString(ARG_PARAM2);
        }
        onAdapterInterface = this;
        mListener = this;
        dbHandler = new DatabaseHandler(getContext());

        alDentsData = new ArrayList<>();
        ArrayList<DataBaseHelper> alDB = new ArrayList<>(dbHandler.getDataBySubCategory(sSubCategory));
        if (alDB.size() == 1) {
            isDataInDB = true;
            nDBID = alDB.get(0).get_id();
            alImagePath.addAll(Arrays.asList(alDB.get(0).get_image_path().split(",")));

            //ArrayList<DentsRVData> alDentsData = new ArrayList<>();
            ArrayList<String> alLength = new ArrayList<>(Arrays.asList(alDB.get(0).get_individual_length().split(",")));
            ArrayList<String> alWidth = new ArrayList<>(Arrays.asList(alDB.get(0).get_individual_width().split(",")));
            ArrayList<String> alDepth = new ArrayList<>(Arrays.asList(alDB.get(0).get_individual_depth().split(",")));
            ArrayList<String> alTime = new ArrayList<>(Arrays.asList(alDB.get(0).get_individual_time().split(",")));
            ArrayList<String> alCost = new ArrayList<>(Arrays.asList(alDB.get(0).get_individual_cost().split(",")));

            for (int i = 0; i < alLength.size(); i++) {
                DentsRVData dentsRVData = new DentsRVData(alLength.get(i), alWidth.get(i), alDepth.get(i), alTime.get(i),
                        alCost.get(i));
                alDentsData.add(dentsRVData);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dent_info, container, false);

        initViews(view);
        onClickListener();


        //alDentsData.addAll(Arrays.asList(alDB.get(0).get_individual_length().split(",")));

        dentInfoImagePagerAdapter = new DentInfoImagePagerAdapter(getContext(), alImagePath);
        mViewPagerSlideShow.setOffscreenPageLimit(alImagePath.size() - 1);
        mViewPagerSlideShow.setAdapter(dentInfoImagePagerAdapter);

        if (alImagePath.size() > 0) {
            for (int i = 0; i < alImagePath.size(); i++) {
                addNewBubble();
            }
        }


        if (count == 1)
            alDentsData = new ArrayList<>();

        alDentsData.addAll(lhmDents.values());

        /*final Runnable r = new Runnable() {
            public void run() {
                for (int i = 0; i < alDentsData.size(); i++) {
                    if (!alDentsData.get(i).getCost().equals(""))
                        fTotalCost = fTotalCost + Float.valueOf(alDentsData.get(i).getCost());
                    if (!alDentsData.get(i).getTimeInHours().equals(""))
                        fTotalTime = fTotalCost + Float.valueOf(alDentsData.get(i).getTimeInHours());
                }
                mtvTotalTime.setText(String.valueOf(fTotalTime));
                mtvTotalCost.setText(String.valueOf(fTotalCost));
            }
        };
        r.run();*/
        //if(hmDents.size()>0){
        int length = alDentsData.size();
        if (length == 0)
            length = 1;
        dentsRVAdapter = new DentsRVAdapter(getContext(), recyclerViewDentsInfo, alDentsData, length);
        recyclerViewDentsInfo.setAdapter(dentsRVAdapter);
        //}
        count = 1;
        return view;
    }

    private void initViews(View view) {
        mtvTotalTime = view.findViewById(R.id.mtv_total_time);
        mtvTotalCost = view.findViewById(R.id.mtv_total_cost);
        tvAddDents = view.findViewById(R.id.tv_add_dents);
        ivGallery = view.findViewById(R.id.iv_gallery);
        ivCamera = view.findViewById(R.id.iv_camera);
        tvPhotoStatus = view.findViewById(R.id.tv_photo_status);
        //ivDentView = view.findViewById(R.id.iv_dent_view);
        mPhotoEditorView = view.findViewById(R.id.photoEditorView);
        recyclerViewDentsInfo = view.findViewById(R.id.rv_dents_info);

        mViewPagerSlideShow = view.findViewById(R.id.viewpager_slideshow);
        mViewPagerSlideShow.setOffscreenPageLimit(alImagePath.size() - 1);

        fabDelete = view.findViewById(R.id.fab_delete);
        fabDelete.hide();

        llBubbleParent = view.findViewById(R.id.ll_bubble_parent);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDentsInfo.setLayoutManager(mLinearLayoutManager);
        recyclerViewDentsInfo.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewDentsInfo.setHasFixedSize(true);

        recyclerViewDentsInfo.addOnItemTouchListener(new DentsRVAdapter.RecyclerTouchListener(getActivity(),
                recyclerViewDentsInfo, new DentsRVAdapter.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                nScrollIndex = position;
            }

            @Override
            public void onLongClick(View view, int position) {
                nScrollIndex = position;
            }
        }));

        //mPhotoEditorView.getSource().setImageURI();
    }

    private void onClickListener() {
        if (alImagePath.size() > 0) {
            tvPhotoStatus.setVisibility(View.GONE);
        }

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_GALLERY_CODE);*/
                openImageIntent();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
                }*/
                openImageIntent();
                /*Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);*/
            }
        });

        tvAddDents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DentsRVAdapter.onAdapterInterface.onAdd(null);
                //imageViewRVAdapter
                //imageViewRVAdapter.notifyItemInserted();
            }
        });

        mViewPagerSlideShow.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                ZoomOutPageTransformer zoomOutPageTransformer = new ZoomOutPageTransformer();
                zoomOutPageTransformer.transformPage(page, position);
            }
        });

        mViewPagerSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DentsRVAdapter.onAdapterInterface.onDelete(-1);
                fabDelete.hide();
            }
        });

    }

    private void openImageIntent() {
        //onImageUtilsListener.onBitmapCompressed("SHOW_PROGRESS_BAR",1,null, null, null);
        //MainActivity.homeInterfaceListener.onHomeCalled("SHOW_PROGRESS_BAR", nCase, null, null);

        // Determine Uri of camera image to save.
        //final File root = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/" + File.separator + getActivity().getPackageName() + File.separator);
        final File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + getActivity().getResources().getString(R.string.folder_name));
        root.mkdirs();
        final String sFileName = "IMG" + System.currentTimeMillis() + ".jpg";
        File sdImageMainDirectory = new File(root, sFileName);
        Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        MainActivity.onFragmentInteractionListener.onFragmentChange(StaticReferenceClass.SET_URI, "", false, outputFileUri);
        getActivity().startActivityForResult(chooserIntent, REQUEST_GALLERY_CODE);
        //onImageUtilsListener.onBitmapCompressed("START_ACTIVITY_FOR_RESULT",1,null, chooserIntent, outputFileUri);
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
        llBubbleParent.addView(imageView);
    }

    private void removeBubble() {
        ImageView imageView = alBubbleViews.get(alBubbleViews.size() - 1);
        llBubbleParent.removeView(imageView);
        alBubbleViews.remove(imageView);
    }

    /*private void fetchAllRVData() {
        for (int i = 0; i < recyclerViewDentsInfo.getAdapter().getItemCount(); i++) {
            final RecyclerView.ViewHolder holder = recyclerViewDentsInfo.getChildViewHolder(recyclerViewDentsInfo.getChildAt(i));
            int id = holder.itemView.getId();
            View itemView = recyclerViewDentsInfo.getChildAt(i);
            //View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(i)).itemView;
            TextInputLayout et = itemView.findViewById(R.id.et_length);
            String sLength = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_width);
            String sWidth = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_depth);
            String sDepth = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_time);
            String sTime = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_cost);
            String sCost = et.getEditText().getText().toString().trim();

            DentsRVData dentsRVData = new DentsRVData(sLength, sWidth, sDepth, sTime, sCost);
            alDentsData.add(dentsRVData);
            lhmDents.put(itemView.getId(), dentsRVData);
            //hmDents.put(count, dentsRVData);
        }
    }*/

    /*@Override
    public void onResume(){
        super.onResume();
        if(alImagePath!=null && alImagePath.size()>0)
        Toast.makeText(getContext(), alImagePath.get(0)+ " data" + " OnResume", Toast.LENGTH_SHORT).show();
    }*/

    /*@Override
    public void onPause(){
        super.onPause();
        //Toast.makeText(getContext(), sMainCategory+ " data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        //Toast.makeText(getContext(), sMainCategory + " data", Toast.LENGTH_SHORT).show();
    }*/

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
    public void onAdapterCall(int nCall, boolean isAddition, float fData, ArrayList<DentsRVData> alDentsData) {
        Constants constants = Constants.values()[nCall];
        switch (constants) {
            case SHOW_FAB:
                fabDelete.show();
                break;
            case HIDE_FAB:
                fabDelete.hide();
                break;
            case TRANSITION_FRAGMENT:
                /*String sImagePath = android.text.TextUtils.join(",",alImagePath);
                long ID = dbHandler.addImagePathToServiceTable(new DataBaseHelper(sMainCategory, sSubCategory, sImagePath));*/
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();*/

                /*final Runnable r = new Runnable() {
                    public void run() {
                        DentsRVAdapter.onAdapterInterface.onAdapterCall(ADD_ALL_DATA, false, 0);
                    }
                };
                r.run();*/
                //fetchAllRVData();
                DentsRVAdapter.onAdapterInterface.onAdapterCall(ADD_ALL_DATA, false, 0, null);
                //DentsRVAdapter.onAdapterInterface.onAdapterCall(ADD_ALL_DATA);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //Fragment transitionFragment = TransitionFragment.newInstance(String.valueOf(ID), "", alImagePath);
                Fragment transitionFragment = TransitionFragment.newInstance(String.valueOf(1), "", alImagePath);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setSharedElementReturnTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.change_image_trans));
                    setExitTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.fade));

                    transitionFragment.setSharedElementEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.change_image_trans));
                    transitionFragment.setEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.fade));
                }
                ft.replace(R.id.fl_container, transitionFragment);
                ft.addToBackStack(null);
                ft.addSharedElement(mViewPagerSlideShow, getString(R.string.viewpager_transition));
                ft.addSharedElement(llBubbleParent, getString(R.string.ll_bubble_transition));
                ft.commit();
                MainActivity.onFragmentInteractionListener.onFragmentChange(MENU_ITEM_DELETE, "", true, null);
                break;
            case UPDATE_TOTAL_TIME:
                if (isAddition)
                    fTotalTime = fTotalTime + fData;
                else
                    fTotalTime = fTotalTime - fData;

                mtvTotalTime.setText(String.valueOf(fTotalTime));
                break;
            case UPDATE_TOTAL_COST:
                if (isAddition)
                    fTotalCost = fTotalCost + fData;
                else
                    fTotalCost = fTotalCost - fData;
                mtvTotalCost.setText(String.valueOf(fTotalCost));
                break;
            case RESET_RECYCLER_VIEW:
                dentsRVAdapter = new DentsRVAdapter(getContext(), recyclerViewDentsInfo, alDentsData, alDentsData.size());
                recyclerViewDentsInfo.setAdapter(dentsRVAdapter);
                MainActivity.onFragmentInteractionListener.onFragmentChange(MENU_ITEM_SAVE, "", true, null);
                break;
        }
    }

    @Override
    public void onAdd(LinkedHashMap<Integer, DentsRVData> lhmDents) {
        this.lhmDents = lhmDents;
    }

    @Override
    public void onDelete(int pos) {
        alImagePath.remove(pos);
        removeBubble();
    }

    @Override
    public void onFragmentChange(int nCase, String sCase, boolean isVisible, Uri uri) {

    }

    @Override
    public void onActivityToFragment(int nCase, String sCase, Uri uri) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case SET_URI:
                alImagePath.add(uri.getPath());
                DentInfoImagePagerAdapter dentInfoImagePagerAdapter = new DentInfoImagePagerAdapter(getContext(), alImagePath);
                mViewPagerSlideShow.setOffscreenPageLimit(alImagePath.size() - 1);
                mViewPagerSlideShow.setAdapter(dentInfoImagePagerAdapter);
                if (alImagePath.size() > 0) {
                    tvPhotoStatus.setVisibility(View.GONE);
                }
                addNewBubble();
                MainActivity.onFragmentInteractionListener.onFragmentChange(MENU_ITEM_SAVE, "", true, null);
                break;
            case SAVE:
                CircularProgressBar circularProgressBar = new CircularProgressBar(getContext());
                circularProgressBar.setCanceledOnTouchOutside(false);
                circularProgressBar.setCancelable(false);
                circularProgressBar.show();
                //fetchAllRVData();
                DentsRVAdapter.onAdapterInterface.onAdapterCall(ADD_ALL_DATA, false, 0,null);

                saveData();
                circularProgressBar.dismiss();
                getActivity().onBackPressed();
                break;
        }
    }

    private void saveData(){
        ArrayList<String> alIndividualTime = new ArrayList<>();
        ArrayList<String> alIndividualCost = new ArrayList<>();
        ArrayList<String> alIndividualLength = new ArrayList<>();
        ArrayList<String> alIndividualWidth = new ArrayList<>();
        ArrayList<String> alIndividualDepth = new ArrayList<>();

        ArrayList<DentsRVData> alDentsValue = new ArrayList<>(lhmDents.values());
        for (int i = 0; i < alDentsValue.size(); i++) {
            alIndividualTime.add(alDentsValue.get(i).getTimeInHours());
            alIndividualCost.add(alDentsValue.get(i).getCost());
            alIndividualLength.add(alDentsValue.get(i).getLength());
            alIndividualWidth.add(alDentsValue.get(i).getWidth());
            alIndividualDepth.add(alDentsValue.get(i).getDepth());
        }
        String sIndividualTime = android.text.TextUtils.join(",", alIndividualTime);
        String sIndividualCost = android.text.TextUtils.join(",", alIndividualCost);
        String sIndividualLength = android.text.TextUtils.join(",", alIndividualLength);
        String sIndividualWidth = android.text.TextUtils.join(",", alIndividualWidth);
        String sIndividualDepth = android.text.TextUtils.join(",", alIndividualDepth);

        DataBaseHelper dataBaseHelper;
        if (isDataInDB) {
            dataBaseHelper = new DataBaseHelper(android.text.TextUtils.join(",", alImagePath),
                    sIndividualTime, sIndividualCost, sIndividualLength, sIndividualWidth, sIndividualDepth, alDentsValue.size(),
                    String.valueOf(fTotalTime), String.valueOf(fTotalCost));
            dbHandler.updateRowServiceTable(dataBaseHelper, nDBID);
        } else {
            dataBaseHelper = new DataBaseHelper(sMainCategory, sSubCategory, android.text.TextUtils.join(",", alImagePath),
                    sIndividualTime, sIndividualCost, sIndividualLength, sIndividualWidth, sIndividualDepth, alDentsValue.size(),
                    String.valueOf(fTotalTime), String.valueOf(fTotalCost));
            dbHandler.addDataToServiceTable(dataBaseHelper);
        }
        MainActivity.onFragmentInteractionListener.onFragmentChange(SUPER_BACK_PRESS, "", false, null);
    }
}
