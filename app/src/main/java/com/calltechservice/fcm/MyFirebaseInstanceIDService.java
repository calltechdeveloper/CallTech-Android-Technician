package com.calltechservice.fcm;

import android.util.Log;


import com.calltechservice.db.UserPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
        Log.e(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        UserPref.savePreferencesDevice(getApplicationContext(), "userPref",token);
      //  sendBroadcast(new Intent("Token").putExtra("Refreshed token:",refreshedToken));
    }
}
