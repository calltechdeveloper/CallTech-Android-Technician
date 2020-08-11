package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobNameDetailsModel implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    @SerializedName("working_hours")
    private String working_hours;

    @SerializedName("job_location")
    private String job_location;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public JobNameDetailsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.working_hours);
        dest.writeString(this.job_location);
    }

    protected JobNameDetailsModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.working_hours = in.readString();
        this.job_location = in.readString();
    }

    public static final Creator<JobNameDetailsModel> CREATOR = new Creator<JobNameDetailsModel>() {
        @Override
        public JobNameDetailsModel createFromParcel(Parcel source) {
            return new JobNameDetailsModel(source);
        }

        @Override
        public JobNameDetailsModel[] newArray(int size) {
            return new JobNameDetailsModel[size];
        }
    };
}
