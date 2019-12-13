package app_utility;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import com.autochip.myvehicle.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static app_utility.StaticReferenceClass.IMAGE_SAVED;

public class MyVehicleAsyncTask extends AsyncTask<String, String, String> {

    private Uri outputFileUri;
    private ContentResolver contentResolver;
    private Bitmap bitmapImageUtils;
    private String sFolderNameToSaveImage;
    File sdImageMainDirectory;
    private int nCase;

    public MyVehicleAsyncTask(Uri outputFileUri, ContentResolver contentResolver, String sFolderNameToSaveImage) {
        this.outputFileUri = outputFileUri;
        this.contentResolver = contentResolver;
        this.sFolderNameToSaveImage = sFolderNameToSaveImage;
    }

    @Override
    protected String doInBackground(String... strings) {
        nCase = Integer.valueOf(strings[0]);
        switch (nCase){
            case 1:
                saveFileAsBitmap(outputFileUri);
                break;
        }

        /*final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + sFolderNameToSaveImage);
        Uri selectedImageUri;
        if (isCamera) {
            selectedImageUri = outputFileUri;
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap.getWidth() > 1080 && bitmap.getHeight() > 1920) {
                ImageUtils imageUtils = new ImageUtils(storageDir, selectedImageUri);
            } else {
                //DialogMultiple.mListener.onBitmapCompressed("SET_BITMAP", 1, bitmap, null, null);
                bitmapImageUtils = bitmap;
            }
        } else {
            selectedImageUri = data.getData();
            //Bitmap bitmap = BitmapFactory.decodeFile(selectedImageUri.getPath());
            saveFileAsBitmap(selectedImageUri);
                    *//*Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
         *//*if (bitmap.getWidth() > 1080 && bitmap.getHeight() > 1920) {
                        ImageUtils imageUtils = new ImageUtils(root, selectedImageUri);
                    } else {*//*
            //DialogMultiple.mListener.onBitmapCompressed("SET_BITMAP", 1, bitmap, null, null);
            //}
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        switch (nCase){
            case 1:
                MainActivity.onFragmentInteractionListener.onFragmentChange(IMAGE_SAVED, "", false, Uri.fromFile(sdImageMainDirectory));
                break;
        }
    }

    private void saveFileAsBitmap(Uri selectedImageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(selectedImageUri.getPath());
        FileOutputStream fileOutputStream = null;
        String imageFileName = "IMG" + System.currentTimeMillis() + ".jpg";
        //final String fname = System.currentTimeMillis() + "insurance";
        final File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + sFolderNameToSaveImage);
        sdImageMainDirectory = new File(root, imageFileName);
        //outputFileUri = Uri.fromFile(sdImageMainDirectory);

        assert bitmap != null;
        if (bitmap.getWidth() > 1080 && bitmap.getHeight() > 1920) {
            ImageUtils imageUtils = new ImageUtils();
        }
        try {
            fileOutputStream = new FileOutputStream(sdImageMainDirectory);
            // PNG is a loss less format, the compression factor (100) is ignored
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
