package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OnGoingJobResponse implements Parcelable {



    @SerializedName("provider_details")
    private OnGoingDetailsData providerDetails;


    @SerializedName("job_details")
    private OnGiongJobDetailsData jobDetails;


    public OnGiongJobDetailsData getJobDetails() {
        return jobDetails;
    }

    public OnGoingDetailsData getProviderDetails() {
        return providerDetails;
    }

    public OnGoingJobResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.providerDetails, flags);
        dest.writeParcelable(this.jobDetails, flags);
    }

    protected OnGoingJobResponse(Parcel in) {
        this.providerDetails = in.readParcelable(OnGoingDetailsData.class.getClassLoader());
        this.jobDetails = in.readParcelable(OnGiongJobDetailsData.class.getClassLoader());
    }

    public static final Creator<OnGoingJobResponse> CREATOR = new Creator<OnGoingJobResponse>() {
        @Override
        public OnGoingJobResponse createFromParcel(Parcel source) {
            return new OnGoingJobResponse(source);
        }

        @Override
        public OnGoingJobResponse[] newArray(int size) {
            return new OnGoingJobResponse[size];
        }
    };
}
