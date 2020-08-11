package com.calltechservice.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int PERMISSION_REQUEST_CODE = 100;

    public static boolean checkPermissionStorage(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED || result1 == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionLocation(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED || result1 == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionCall(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionReadContact(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionAccount(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionContact(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionLocation(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    public static void requestPermissionContact(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_CONTACTS) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            showPermissionRationale(activity, Manifest.permission.WRITE_CONTACTS);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestPermissionAccount(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.GET_ACCOUNTS) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.GET_ACCOUNTS_PRIVILEGED)) {
            showPermissionRationale(activity, Manifest.permission.GET_ACCOUNTS);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.GET_ACCOUNTS_PRIVILEGED}, PERMISSION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private static void showPermissionRationale(Activity activity, String permissionCode) {
        String feature = " for ";
        switch (permissionCode) {
            case Manifest.permission.GET_ACCOUNTS:
            case Manifest.permission.GET_ACCOUNTS_PRIVILEGED:
                feature += "accounts";
                break;
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.CALL_PHONE:
                feature += "contacts";
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                feature += "location";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                feature += "storage";
                break;
            case Manifest.permission.RECORD_AUDIO:
            case Manifest.permission.CAPTURE_AUDIO_OUTPUT:
            case Manifest.permission.MODIFY_AUDIO_SETTINGS:
                feature += "audio";
                break;
            default:
                feature = "";
        }
        //TODO: make this a dialog so that it's clearer to the user
        Toast.makeText(activity, "Please grant permission" + feature, Toast.LENGTH_LONG).show();
    }

    public static void requestPermissionCall(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            showPermissionRationale(activity, Manifest.permission.CALL_PHONE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestPermissionAudio(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            showPermissionRationale(activity, Manifest.permission.RECORD_AUDIO);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAPTURE_AUDIO_OUTPUT}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestPermissionStorage(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static boolean checkPermissionCamera(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionAudio(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity, int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            showPermissionRationale(activity, Manifest.permission.CAMERA);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

}