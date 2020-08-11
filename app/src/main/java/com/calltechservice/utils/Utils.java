package com.calltechservice.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.calltechservice.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Singleton;

@Singleton
public class Utils {
    public static String IS_NOTIFICATION="is_notification";
    public static String job,userid;
    private Context context;
    static Geocoder geocoder;

    public static String serviceID;

    public Utils(Context context) {
        this.context = context;
    }

    public void toaster(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void logger(String message) {
        Log.e("Win-Millionaire-Log", message);
    }

    public void simpleAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setPositiveButton("Close", null);
        builder.create();
        builder.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public String savedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String showDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(date);
    }

    @SuppressWarnings("deprecation")
    public Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


    /**
     * checking internet connection
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public String getFormattedDate(String incomingDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String formatedDate="";
        try {
            date = fmt.parse(incomingDate);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
            formatedDate=fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

    public static String getAddressFromLatLng(Context context,LatLng latLng) {

        geocoder = new Geocoder(context, Locale.getDefault());
        String address = "";
        if (latLng != null) {

            try {
                address = geocoder
                        .getFromLocation(latLng.latitude, latLng.longitude, 1)
                        .get(0).getAddressLine(0);
            } catch (IOException e) {
            }
        }

        return address;

    }








}