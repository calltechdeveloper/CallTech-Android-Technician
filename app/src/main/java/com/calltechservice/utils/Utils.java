package com.calltechservice.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.calltechservice.R;

import javax.inject.Singleton;

@Singleton
public class Utils {
    public static String job, userid;
    private Context context;

    public static String serviceID;

    public Utils(Context context) {
        this.context = context;
    }

    public void toaster(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void simpleAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setPositiveButton("Close", null);
        builder.create();
        builder.show();
    }

    public void simpleAlert(Context context, String title, String message, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Close", null)
                .setCancelable(true)
                .setOnDismissListener(dismissListener)
                .create()
                .show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressWarnings("deprecation")
    public Spanned fromHtml(String html) {
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

}