package com.calltechservice.model.response.employee;


import android.os.Parcel;
import android.os.Parcelable;

public class AreaList implements Parcelable {

    private String lat;
    private String lng;
    private String area_name;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    protected AreaList(Parcel in) {
        lat = in.readString();
        lng = in.readString();
        area_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(area_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AreaList> CREATOR = new Creator<AreaList>() {
        @Override
        public AreaList createFromParcel(Parcel in) {
            return new AreaList(in);
        }

        @Override
        public AreaList[] newArray(int size) {
            return new AreaList[size];
        }
    };

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
