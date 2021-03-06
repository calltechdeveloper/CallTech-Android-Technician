package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CompleteDetailsData implements Parcelable {

    @SerializedName("user_id")
    private String userId;
    private String name;
    private String mobile;
    private String email;
    private String lat;
    private String lng;


    protected CompleteDetailsData(Parcel in) {
        userId = in.readString();
        name = in.readString();
        mobile = in.readString();
        email = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(lat);
        dest.writeString(lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompleteDetailsData> CREATOR = new Creator<CompleteDetailsData>() {
        @Override
        public CompleteDetailsData createFromParcel(Parcel in) {
            return new CompleteDetailsData(in);
        }

        @Override
        public CompleteDetailsData[] newArray(int size) {
            return new CompleteDetailsData[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
