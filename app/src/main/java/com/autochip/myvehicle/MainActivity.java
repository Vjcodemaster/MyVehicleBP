package com.autochip.myvehicle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import app_utility.Constants;
import app_utility.DataBaseHelper;
import app_utility.DatabaseHandler;
import app_utility.MyVehicleAsyncTask;
import app_utility.OnFragmentInteractionListener;
import app_utility.PermissionHandler;
import photo.editor.EditImageActivity;

import static app_utility.PermissionHandler.APP_PERMISSION;
import static app_utility.StaticReferenceClass.DELETE_IMAGE;
import static app_utility.StaticReferenceClass.INVISIBLE;
import static app_utility.StaticReferenceClass.VISIBLE;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private Intent data;
    private int nPermissionFlag = 0;
    private Uri uriImage;

    private String sCurrentMainCategory;
    private String sCurrentSubCategory;

    private DatabaseHandler dbHandler;
    ArrayList<String> alMainCategory;
    public static OnFragmentInteractionListener onFragmentInteractionListener;
    Menu menu;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onFragmentInteractionListener = this;
        dbHandler = new DatabaseHandler(MainActivity.this);
        initViews();

        alMainCategory = new ArrayList<>();
        alMainCategory.add("Body & Paint");
        alMainCategory.add("Mechanical Service");
        alMainCategory.add("Full Service");

        ArrayList<String> alSubMenuName = new ArrayList<>();
        alSubMenuName.add("Roof");
        alSubMenuName.add("Hood");
        alSubMenuName.add("Front door left");
        alSubMenuName.add("Front door right");
        alSubMenuName.add("Back door left");
        alSubMenuName.add("Back door right");


        String sSubCategory = TextUtils.join(",", alSubMenuName);

        for (int i = 0; i < alMainCategory.size(); i++) {
            dbHandler.addDataToTableMain(new DataBaseHelper(alMainCategory.get(i), sSubCategory));
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_body_paint:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment subMenuFragment = SubMenuFragment.newInstance(alMainCategory.get(0), "");
                ft.add(R.id.fl_container, subMenuFragment);
                ft.addToBackStack(null);
                ft.commit();
                toolbarBackArrowVisibility(VISIBLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toobar_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //handles open and close of home button of actionbar/toolbar
            case R.id.action_save:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_delete:
                TransitionFragment.mListener.onActivityToFragment(DELETE_IMAGE, "", null);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    Uri uriSelectedImage = data.getData();
                    if (uriSelectedImage == null) {
                        Intent in = new Intent(MainActivity.this, EditImageActivity.class);
                        in.setData(uriImage);
                        startActivity(in);
                    } else {
                        new MyVehicleAsyncTask(uriSelectedImage, getContentResolver(), getResources().getString(R.string.folder_name)).execute("1");
                    }
                }
                break;
            case 3:
                if (resultCode != Activity.RESULT_OK) {
                    MainActivity.this.finish();
                }
                break;
            /*case 242:
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                break;*/
        }
    }

    private void toolbarBackArrowVisibility(int nVisibility) {
        //menu.findItem(R.id.action_save).setVisible(true);
        if (nVisibility == VISIBLE)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void menuItemHandler(MenuItem item, int nVisibility) {
        //menu.findItem(R.id.action_save).setVisible(true);
        if (nVisibility == VISIBLE)
            item.setVisible(true);
        else
            item.setVisible(false);
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            //currentFragment.getClass().getName();
            //String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if (currentFragment.getClass().getName().equals(DentInfoFragment.class.getName()))
                menuItemHandler(menu.findItem(R.id.action_save), INVISIBLE);
            else if(currentFragment.getClass().getName().equals(TransitionFragment.class.getName()))
                menuItemHandler(menu.findItem(R.id.action_delete), INVISIBLE);
        } else
            toolbarBackArrowVisibility(INVISIBLE);
        super.onBackPressed();
        /*if(getSupportFragmentManager().getBackStackEntryCount()==0) {
            //menuItemHandler(menu.findItem(R.id.action_save), INVISIBLE);
            toolbarBackArrowVisibility(INVISIBLE);
        }*/
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
    public void onFragmentChange(int nCase, String sCase, boolean isVisible, Uri uri) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case OPEN_DENT_FRAGMENT:
                sCurrentMainCategory = sCase.split(",")[0];
                sCurrentSubCategory = sCase.split(",")[1];
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment subMenuFragment = DentInfoFragment.newInstance(sCurrentMainCategory, sCurrentSubCategory);
                ft.add(R.id.fl_container, subMenuFragment);
                ft.addToBackStack(null);
                ft.commit();
                //menuItemHandler(menu.findItem(R.id.action_save), VISIBLE);
                //toolbarBackArrowVisibility(VISIBLE);
                break;
            case SET_URI:
                uriImage = uri;
                break;
            case IMAGE_SAVED:
                Intent in = new Intent(MainActivity.this, EditImageActivity.class);
                //in.putExtra(CURRENT_MAIN_CATEGORY, sCurrentMainCategory);
                //in.putExtra(CURRENT_SUB_CATEGORY, sCurrentSubCategory);
                in.setData(uri);
                startActivity(in);
                break;
            case MENU_ITEM_SAVE:
                if (isVisible)
                    menuItemHandler(menu.findItem(R.id.action_save), VISIBLE);
                else
                    menuItemHandler(menu.findItem(R.id.action_save), INVISIBLE);
                break;
            case MENU_ITEM_DELETE:
                if (isVisible)
                    menuItemHandler(menu.findItem(R.id.action_delete), VISIBLE);
                else
                    menuItemHandler(menu.findItem(R.id.action_delete), INVISIBLE);
                break;
        }
    }

    @Override
    public void onActivityToFragment(int nCase, String sCase, Uri uri) {

    }
}
