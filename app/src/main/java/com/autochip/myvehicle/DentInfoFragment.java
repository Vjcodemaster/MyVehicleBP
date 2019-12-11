package com.autochip.myvehicle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app_utility.Constants;
import app_utility.OnAdapterInterface;
import app_utility.OnFragmentInteractionListener;
import app_utility.StaticReferenceClass;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

import static app_utility.StaticReferenceClass.HIDE_FAB;
import static app_utility.StaticReferenceClass.REQUEST_CAMERA_CODE;
import static app_utility.StaticReferenceClass.REQUEST_GALLERY_CODE;
import static app_utility.StaticReferenceClass.SHOW_FAB;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DentInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DentInfoFragment extends Fragment implements OnAdapterInterface {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView ivGallery, ivCamera;
    private TextView tvAddDents;

    public static OnFragmentInteractionListener mListener;
    public static OnAdapterInterface onAdapterInterface;

    PhotoEditorView mPhotoEditorView;

    PhotoEditor mPhotoEditor;

    RecyclerView recyclerViewDentsInfo;
    private ViewPager mViewPagerSlideShow;
    DentsRVAdapter imageViewRVAdapter;
    FloatingActionButton fabDelete;
    int nScrollIndex = 0;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        onAdapterInterface = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dent_info, container, false);

        initViews(view);
        onClickListener();

        /*ArrayList<String> alLength = new ArrayList<>();
        alLength.add("mm");
        alLength.add("cm");
        alLength.add("in");*/
        imageViewRVAdapter = new DentsRVAdapter(getContext(), recyclerViewDentsInfo, 1);
        recyclerViewDentsInfo.setAdapter(imageViewRVAdapter);
        return view;
    }

    private void initViews(View view) {
        tvAddDents = view.findViewById(R.id.tv_add_dents);
        ivGallery = view.findViewById(R.id.iv_gallery);
        ivCamera = view.findViewById(R.id.iv_camera);
        mPhotoEditorView = view.findViewById(R.id.photoEditorView);
        recyclerViewDentsInfo = view.findViewById(R.id.rv_dents_info);

        mViewPagerSlideShow = view.findViewById(R.id.viewpager_slideshow);
        mViewPagerSlideShow.setOffscreenPageLimit(2 - 1);

        fabDelete = view.findViewById(R.id.fab_delete);
        fabDelete.hide();
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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
                }
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
                DentsRVAdapter.onAdapterInterface.onAdd();
                //imageViewRVAdapter
                //imageViewRVAdapter.notifyItemInserted();
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
        final String sFileName = "IMG" + System.currentTimeMillis() + ".png";
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
        MainActivity.onFragmentInteractionListener.onFragmentChange(StaticReferenceClass.SET_URI, "", outputFileUri);
        getActivity().startActivityForResult(chooserIntent, REQUEST_GALLERY_CODE);
        //onImageUtilsListener.onBitmapCompressed("START_ACTIVITY_FOR_RESULT",1,null, chooserIntent, outputFileUri);
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
    public void onAdapterCall(int nCall) {
        Constants constants = Constants.values()[nCall];
        switch (constants){
            case SHOW_FAB:
                fabDelete.show();
                break;
            case HIDE_FAB:
                fabDelete.hide();
                break;
        }
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onDelete() {

    }
}
