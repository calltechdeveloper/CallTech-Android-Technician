package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobUserDetailsModel implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("user_id")
    private String userId;

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public String getEmail() {
        return email;
    }

    public String getService_area_name() {
        return service_area_name;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("category_image")
    private String category_image;

    @SerializedName("email")
    private String email;


    @SerializedName("service_area_name")
    private String service_area_name;

    @SerializedName("schedule_date")
    private String scheduleDate;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @SerializedName("mobile")
    private String mobile;

    public JobUserDetailsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.userId);
        dest.writeString(this.profile_pic);
        dest.writeString(this.category_name);
        dest.writeString(this.category_image);
        dest.writeString(this.email);
        dest.writeString(this.service_area_name);
        dest.writeString(this.scheduleDate);
        dest.writeString(this.mobile);
    }

    protected JobUserDetailsModel(Parcel in) {
        this.name = in.readString();
        this.userId = in.readString();
        this.profile_pic = in.readString();
        this.category_name = in.readString();
        this.category_image = in.readString();
        this.email = in.readString();
        this.service_area_name = in.readString();
        this.scheduleDate = in.readString();
        this.mobile = in.readString();
    }

    public static final Creator<JobUserDetailsModel> CREATOR = new Creator<JobUserDetailsModel>() {
        @Override
        public JobUserDetailsModel createFromParcel(Parcel source) {
            return new JobUserDetailsModel(source);
        }

        @Override
        public JobUserDetailsModel[] newArray(int size) {
            return new JobUserDetailsModel[size];
        }
    };
}
