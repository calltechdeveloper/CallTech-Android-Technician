package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CompleteJobResponse implements Parcelable {



    @SerializedName("provider_details")
    private CompleteDetailsData providerDetails;


    @SerializedName("job_details")
    private CompleteJobDetailsData jobDetails;


    public CompleteJobDetailsData getJobDetails() {
        return jobDetails;
    }

    public CompleteDetailsData getProviderDetails() {
        return providerDetails;
    }

    public CompleteJobResponse() {
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

    protected CompleteJobResponse(Parcel in) {
        this.providerDetails = in.readParcelable(CompleteDetailsData.class.getClassLoader());
        this.jobDetails = in.readParcelable(CompleteJobDetailsData.class.getClassLoader());
    }

    public static final Creator<CompleteJobResponse> CREATOR = new Creator<CompleteJobResponse>() {
        @Override
        public CompleteJobResponse createFromParcel(Parcel source) {
            return new CompleteJobResponse(source);
        }

        @Override
        public CompleteJobResponse[] newArray(int size) {
            return new CompleteJobResponse[size];
        }
    };
}
