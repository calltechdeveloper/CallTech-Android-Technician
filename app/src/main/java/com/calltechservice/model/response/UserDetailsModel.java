package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class UserDetailsModel implements Parcelable {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("name")
    private String name;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getMobile() {
        return mobile;
    }

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("mobile")
    private String mobile;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.profile_pic);
        dest.writeString(this.mobile);
    }

    public UserDetailsModel() {
    }

    protected UserDetailsModel(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.profile_pic = in.readString();
        this.mobile = in.readString();
    }

    public static final Creator<UserDetailsModel> CREATOR = new Creator<UserDetailsModel>() {
        @Override
        public UserDetailsModel createFromParcel(Parcel source) {
            return new UserDetailsModel(source);
        }

        @Override
        public UserDetailsModel[] newArray(int size) {
            return new UserDetailsModel[size];
        }
    };
}
