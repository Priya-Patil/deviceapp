package com.pvp.deviceapp.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.tonnyl.light.Light;

public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    public static void launchActivity(Activity activity, Class<?> mClass, boolean shouldFinishParent, Bundle bundle) {
        Intent intent = new Intent(activity, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        if (shouldFinishParent) {
            activity.finish();
        }
    }


    public static String getCurrentDate(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        return filename;
    }


    public static void launchActivityForResult(Activity activity, Class<?> mClass, int requestCode) {
        Intent intent = new Intent(activity, mClass);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void launchActivityWithContext(Context context, Class<?> mClass) {
        Intent intent = new Intent(context, mClass);
        context.startActivity(intent);
    }

    public static void launchActivityForResult(Activity activity, Class<?> mClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void launchActivityForResultFromFragment(Fragment fragment, Activity activity, Class<?> mClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void launchActivity(Activity activity, Class<?> mClass, boolean shouldFinishParent) {
        Intent intent = new Intent(activity, mClass);
        activity.startActivity(intent);
        if (shouldFinishParent) {
            activity.finish();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void finishWithResult(Activity activity, Bundle bundle, int result) {
        Intent i = new Intent();
        if (bundle != null) {
            i.putExtras(bundle);
        }
        activity.setResult(result, i);
        activity.finish();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the cu rrently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view = null;
    }

    public static String getDeviceToken(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean emailValidate(String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean numberValidation(String number) {
        return Pattern.compile("^[0-9]+$").matcher(number).matches();
    }

    public static String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static String getDeviceName() {
        return Build.MANUFACTURER + " - " + Build.MODEL;
    }


    public static void showErrorMessage(Activity activity, String msg) {
        try {
            //Snackbar.make(activity.getWindow().getDecorView().getRootView(), msg, length).show();
            Light.error(activity.getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }
    }

    public static void showErrorMessage(Activity activity, String msg, int length) {
        try {
            Snackbar.make(activity.getWindow().getDecorView().getRootView(), msg, length).show();
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }
    }

    public static void showWarningMessage(Activity activity, String msg) {
        try {
            //Snackbar.make(activity.getWindow().getDecorView().getRootView(), msg, length).show();
            Light.warning(activity.getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }
    }

    public static void showSuccessMessage(Activity activity, String msg) {
        try {
            //Snackbar.make(activity.getWindow().getDecorView().getRootView(), msg, length).show();
            Light.success(activity.getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }
    }

    public static void showNormalMessage(Activity activity, String msg) {
        try {
            //Snackbar.make(activity.getWindow().getDecorView().getRootView(), msg, length).show();
            Light.normal(activity.getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }
    }


    public static String encodeImage(String imageUrl) {

        String imageString=null;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }

        return imageString;
    }


    public static Bitmap getBitmapFromURL(String imageUrl) {

        Bitmap myBitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception ex) {
            Log.e(TAG, "showErrorMessage: ", ex);
        }
        return  myBitmap;
    }

    public static String encodeImage(Bitmap bitmap) {

        String imageString=null;

         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }

         return imageString;
    }


    public static Bitmap decodeImage(String imageString) {

        Bitmap image = null;
        try {
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            //image.setImageBitmap(decodedImage);
        }
        catch (Exception ex){
            Log.e(TAG, "showErrorMessage: ", ex);
        }

        return image;
    }


    public static void downloadManager(Context context,String fileName){
        String dir=Environment.getExternalStorageDirectory().getAbsolutePath()+"/images/";
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
      /*  DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://olseducation.in/api/attachments/instructions/85657-saarthi-logo-png.png"))
                .setTitle("Dummy File")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationInExternalFilesDir(context,dir, fileName)// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);*/
    }


//    public static void sendIntent(Context context, String action) {
//        final Intent intent = new Intent(context, MusicService.class);
//        intent.setAction(action);
//        context.startService(intent);
//    }
//
//    public static void sendIntent(Context context, String action, Bundle b) {
//        final Intent intent = new Intent(context, MusicService.class);
//        intent.putExtras(b);
//        intent.setAction(action);
//        context.startService(intent);
//    }
}
