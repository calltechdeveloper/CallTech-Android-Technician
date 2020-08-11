package com.calltechservice.utils;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.calltechservice.R;
import com.calltechservice.databinding.DialogLogoutBinding;
import com.calltechservice.databinding.NormalAlertBinding;
import com.calltechservice.db.SharedPref;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.activity.NewUserActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

    private static final Pattern VALID_NUMBER = Pattern.compile("[0-9]+");


    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, int mContainer) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        ftTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        } else {
            ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        }
        ftTransaction.commit();
    }


    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {

            Pattern pattern;
            Matcher matcher;
            final String EMAIL_PATTERN =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})R ";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(target);
            return matcher.matches();
            // return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*createCustomDialog() is used to create dialog*/
    public static Dialog createCustomDialog(Context context, View layoutResourceId) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(layoutResourceId);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);
        return dialog;
    }


    public static void exitDialog(final Context mContext) {
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot());
        dialogLogoutBinding.tvMessage.setText(R.string.exit_from_app);
        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                mContext.startActivity(intent);
                System.exit(0);
            }
        });
        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
    }


    public static boolean isVin(String s) {
        return VALID_NUMBER.matcher(s).matches();
    }


    /**
     * checking internet connection
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static boolean isValidPhone(String target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }

    public static void printLog(Context context, String msg) {
        if (context != null) {
            Log.e(context.getClass().getName(), msg);
        } else {
            Log.e("DEBUG", msg);
        }
    }

    public static String getTimeStamp(String str_date) {
        Date date = null;
        try {
            date = (Date) new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(str_date);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        assert date != null;
        String timestampValue = String.valueOf(date.getTime());
        return timestampValue;
    }


    public static String getTimeStampDate(String str_date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy HH:mm");
        if (str_date.length() < 13) {
            str_date = (Long.valueOf(str_date) * 1000) + "";
        }
        Date date = null;
        try {
            date = new Date(Long.valueOf(str_date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert date != null;
        String timestampValue="";
        try
        {
            timestampValue = format.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return timestampValue;
    }


    public static void datePickerDialog(final Context context, final TextView textView) {
        final Calendar c = Calendar.getInstance(TimeZone.getDefault());
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dpd = new DatePickerDialog(context, R.style.MyTimePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear + 1);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                c.getTime();
                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.setCanceledOnTouchOutside(true);
        dpd.show();
    }

    public static void showSnack(View view, String message) {
        Snackbar.make(view, Html.fromHtml("<font color=\"#ffffff\">"+message+"</font>"), Snackbar.LENGTH_SHORT).show();

    }


    public static void setTime(Context mContext, final TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext,  R.style.MyTimePickerDialogTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = selectedHour + "";
                String min = selectedMinute + "";
                if (selectedHour < 10) {
                    hour = "0" + selectedHour;
                }
                if (selectedMinute < 10) {
                    min = "0" + selectedMinute;
                }
                textView.setText(hour + ":" + min);
            }
        }, hour, 0, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }


    public static void commonAlert(final Context mContext, String message, final Fragment fragment) {
        NormalAlertBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.normal_alert, null, false);
        final Dialog commonDialog = CommonUtils.createCustomDialog(mContext, dialogBinding.getRoot());
        dialogBinding.tvMessage.setText(message);
        dialogBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
                CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
            }
        });

        commonDialog.show();
    }



    public static void commonAlertBackStack(final Activity mContext, String message) {
        NormalAlertBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.normal_alert, null, false);
        final Dialog commonDialog = CommonUtils.createCustomDialog(mContext, dialogBinding.getRoot());
        dialogBinding.tvMessage.setText(message);
        dialogBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
                ((HomeActivity)mContext).onBackPressed();
            }
        });

        commonDialog.show();
    }


    public static void call(Context context, String phone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(callIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static Animation animate()
    {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(80); //You can manage the time of the blink with this parameter
        anim.setStartOffset(30);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }

    public static void alertMessage(final Context mContext, String message, final Fragment fragment) {
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot());
        dialogLogoutBinding.tvMessage.setText(message);
        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               exitDialog.dismiss();
            }
        });
        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
                CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);

            }
        });
        exitDialog.show();
    }


    /**
     * Here is the key method to apply the animation
     */
    public static void setAnimation(View viewToAnimate, Context context)
    {
       /* Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);*/

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);

    }

    public static void alertMessageNotRegister(final Context mContext, String message) {
        NormalAlertBinding normalAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.normal_alert, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, normalAlertBinding.getRoot());
        normalAlertBinding.tvMessage.setText(message);
        normalAlertBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
                Intent intent=new Intent(mContext,NewUserActivity.class);
                mContext.startActivity(intent);
            }
        });
        exitDialog.show();
    }

    public static void logoutDialog(final Context mContext) {
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_logout, null, false);
        final Dialog exitDialog = CommonUtils.createCustomDialog(mContext, dialogLogoutBinding.getRoot());
        dialogLogoutBinding.tvMessage.setText(R.string.logout_from_app);
        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
                SharedPref.removePreference(mContext);
                SharedPref.savePreferenceBoolean(mContext,AppConstant.NOT_FIRST_TIME,true);
                Intent intent = new Intent(mContext,NewUserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);

            }
        });
        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();
    }


}