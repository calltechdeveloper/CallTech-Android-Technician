package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobEndDetailsModel implements Parcelable {

    @SerializedName("end")
    private String end;

    @SerializedName("invitation_doc")
    private String invitation_doc;

    public String getEnd() {
        return end;
    }

    public String getInvitation_doc() {
        return invitation_doc;
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
        dest.writeString(this.end);
        dest.writeString(this.invitation_doc);
        dest.writeString(this.start_status);
    }

    public JobEndDetailsModel() {
    }

    protected JobEndDetailsModel(Parcel in) {
        this.end = in.readString();
        this.invitation_doc = in.readString();
        this.start_status = in.readString();
    }

    public static final Creator<JobEndDetailsModel> CREATOR = new Creator<JobEndDetailsModel>() {
        @Override
        public JobEndDetailsModel createFromParcel(Parcel source) {
            return new JobEndDetailsModel(source);
        }

        @Override
        public JobEndDetailsModel[] newArray(int size) {
            return new JobEndDetailsModel[size];
        }
    };
}
