package com.autochip.myvehicle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import app_utility.MyVehicleAsyncTask;
import app_utility.OnFragmentInteractionListener;
import app_utility.PermissionHandler;

import static app_utility.PermissionHandler.APP_PERMISSION;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private Intent data;
    private int nPermissionFlag = 0;

    public static OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onFragmentInteractionListener = this;
    }

    public void onButtonClick(View v){
        switch (v.getId()){
            case R.id.btn_body_paint:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment subMenuFragment = SubMenuFragment.newInstance("","");
                ft.add(R.id.fl_container, subMenuFragment);
                ft.addToBackStack(null);
                ft.commit();
            break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!PermissionHandler.hasPermissions(MainActivity.this, APP_PERMISSION)) {
            ActivityCompat.requestPermissions(MainActivity.this, APP_PERMISSION, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int PERMISSION_ALL, String permissions[], int[] grantResults) {
        StringBuilder sMSG = new StringBuilder();
        if (PERMISSION_ALL == 1) {
            for (String sPermission : permissions) {
                switch (sPermission) {
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                //Show permission explanation dialog...
                                //showPermissionExplanation(SignInActivity.this.getResources().getString(R.string.phone_explanation));
                                //Toast.makeText(SignInActivity.this, "not given", Toast.LENGTH_SHORT).show();
                                sMSG.append("STORAGE, ");
                                nPermissionFlag = 0;
                            } else {
                                //Never ask again selected, or device policy prohibits the app from having that permission.
                                //So, disable that feature, or fall back to another situation...
                                //@SuppressWarnings("unused") AlertDialogs alertDialogs = new AlertDialogs(HomeScreen.this, 1, mListener);
                                //Toast.makeText(SignInActivity.this, "permission never ask", Toast.LENGTH_SHORT).show();
                                //showPermissionExplanation(HomeScreenActivity.this.getResources().getString(R.string.phone_explanation));
                                sMSG.append("STORAGE, ");
                                nPermissionFlag = 0;
                            }
                        }
                        break;
                    case Manifest.permission.CAMERA:
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                                //Show permission explanation dialog...
                                //showPermissionExplanation(SignInActivity.this.getResources().getString(R.string.phone_explanation));
                                //Toast.makeText(SignInActivity.this, "not given", Toast.LENGTH_SHORT).show();
                                sMSG.append("CAMERA, ");
                                nPermissionFlag = 0;
                            } else {
                                //Never ask again selected, or device policy prohibits the app from having that permission.
                                //So, disable that feature, or fall back to another situation...
                                //@SuppressWarnings("unused") AlertDialogs alertDialogs = new AlertDialogs(HomeScreen.this, 1, mListener);
                                //Toast.makeText(SignInActivity.this, "permission never ask", Toast.LENGTH_SHORT).show();
                                //showPermissionExplanation(HomeScreenActivity.this.getResources().getString(R.string.phone_explanation));
                                sMSG.append("CAMERA, ");
                                nPermissionFlag = 0;
                            }
                        }
                        break;

                }
            }
            if (!sMSG.toString().equals("") && !sMSG.toString().equals(" ")) {
                PermissionHandler permissionHandler = new PermissionHandler(MainActivity.this, 0, sMSG.toString(), nPermissionFlag);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 241:
                if (resultCode == Activity.RESULT_OK) {
                    this.data = data;
                    Uri uriSelectedImage = data.getData();
                    //imageview.setImageURI(selectedImage);
                    DentInfoFragment.mListener.onActivityToFragment("IMAGE_URI", uriSelectedImage);
                    //new MyVehicleAsyncTask(data, null, getContentResolver()).execute();
                }
                //Uri.fromFile(new File("/sdcard/sample.jpg"))
                //saveImage();
                break;
            case 3:
                if (resultCode != Activity.RESULT_OK) {
                    MainActivity.this.finish();
                }
                break;
        }
    }

    /*private String saveImage(Bitmap image) {
        String savedImagePath = null;

        Date d = new Date();
        CharSequence s = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        //String imageFileName = "IMG" + s + count + ".jpg";
        String imageFileName = "IMG" + s  + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/MyVehicle");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            //galleryAddPic(savedImagePath);
            //Toast.makeText(mContext, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }*/

    @Override
    public void onFragmentChange(String sCase) {
        switch (sCase){
            case "OPEN_DENT_FRAGMENT":
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment subMenuFragment = DentInfoFragment.newInstance("","");
                ft.add(R.id.fl_container, subMenuFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }

    @Override
    public void onActivityToFragment(String sCase, Uri uri) {

    }
}
