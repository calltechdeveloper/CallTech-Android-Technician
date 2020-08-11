package com.calltechservice.location;

import android.annotation.SuppressLint;
import android.location.Location;

@SuppressWarnings("ALL")
@SuppressLint("ALL")
public interface LocationResult{

   void gotLocation(Location location);
  }