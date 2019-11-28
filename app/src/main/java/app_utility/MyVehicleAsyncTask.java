package app_utility;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class MyVehicleAsyncTask extends AsyncTask<String, String, String> {

    Intent data;
    private Uri outputFileUri;
    private ContentResolver contentResolver;
    private Bitmap bitmapImageUtils;

    public MyVehicleAsyncTask(Intent data, Uri outputFileUri, ContentResolver contentResolver){
        this.data = data;
        this.outputFileUri = outputFileUri;
        this.contentResolver = contentResolver;
    }

    @Override
    protected String doInBackground(String... strings) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/MyVehicle");
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
            //saveFileAsBitmap(selectedImageUri);
                    /*Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    /*if (bitmap.getWidth() > 1080 && bitmap.getHeight() > 1920) {
                        ImageUtils imageUtils = new ImageUtils(root, selectedImageUri);
                    } else {*/
            //DialogMultiple.mListener.onBitmapCompressed("SET_BITMAP", 1, bitmap, null, null);
            //}
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private String saveImage(Bitmap image) {
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
    }
}
