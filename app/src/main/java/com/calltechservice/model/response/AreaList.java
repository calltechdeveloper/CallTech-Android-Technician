
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AreaList implements Parcelable {

    @SerializedName("area_name")
    private String mAreaName;

    @SerializedName("lat")
    private String mLat;

    @SerializedName("lng")
    private String mLng;





    public String getAreaName() {
        return mAreaName;
    }

    public void setAreaName(String areaName) {
        mAreaName = areaName;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String lng) {
        mLng = lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAreaName);
        dest.writeString(this.mLat);
        dest.writeString(this.mLng);
    }

    public AreaList() {
    }

    protected AreaList(Parcel in) {
        this.mAreaName = in.readString();
        this.mLat = in.readString();
        this.mLng = in.readString();
    }

    public static final Parcelable.Creator<AreaList> CREATOR = new Parcelable.Creator<AreaList>() {
        @Override
        public AreaList createFromParcel(Parcel source) {
            return new AreaList(source);
        }

        @Override
        public AreaList[] newArray(int size) {
            return new AreaList[size];
        }
    };
}
