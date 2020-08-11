package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobStartDetailsModel implements Parcelable {

    @SerializedName("start")
    private String start;

    public String getStart() {
        return start;
    }

    public String getStart_status() {
        return start_status;
    }

    @SerializedName("start_status")
    private String start_status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.start);
        dest.writeString(this.start_status);
    }

    public JobStartDetailsModel() {
    }

    protected JobStartDetailsModel(Parcel in) {
        this.start = in.readString();
        this.start_status = in.readString();
    }

    public static final Creator<JobStartDetailsModel> CREATOR = new Creator<JobStartDetailsModel>() {
        @Override
        public JobStartDetailsModel createFromParcel(Parcel source) {
            return new JobStartDetailsModel(source);
        }

        @Override
        public JobStartDetailsModel[] newArray(int size) {
            return new JobStartDetailsModel[size];
        }
    };
}
