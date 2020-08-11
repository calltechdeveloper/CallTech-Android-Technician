package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobBookingDetailsModel implements Parcelable {

    public String getBooking_date() {
        return booking_date;
    }

    @SerializedName("booking_date")
    private String booking_date;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.booking_date);
    }

    public JobBookingDetailsModel() {
    }

    protected JobBookingDetailsModel(Parcel in) {
        this.booking_date = in.readString();
    }

    public static final Creator<JobBookingDetailsModel> CREATOR = new Creator<JobBookingDetailsModel>() {
        @Override
        public JobBookingDetailsModel createFromParcel(Parcel source) {
            return new JobBookingDetailsModel(source);
        }

        @Override
        public JobBookingDetailsModel[] newArray(int size) {
            return new JobBookingDetailsModel[size];
        }
    };
}
