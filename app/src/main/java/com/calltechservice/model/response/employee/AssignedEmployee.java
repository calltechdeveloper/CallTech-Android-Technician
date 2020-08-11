package com.calltechservice.model.response.employee;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AssignedEmployee implements Parcelable {


    private String name;
    private String mobile;
    @SerializedName("profile_pic")
    private String profilePic;

    protected AssignedEmployee(Parcel in) {
        name = in.readString();
        mobile = in.readString();
        profilePic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(profilePic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AssignedEmployee> CREATOR = new Creator<AssignedEmployee>() {
        @Override
        public AssignedEmployee createFromParcel(Parcel in) {
            return new AssignedEmployee(in);
        }

        @Override
        public AssignedEmployee[] newArray(int size) {
            return new AssignedEmployee[size];
        }
    };

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
