package com.calltechservice.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest implements Parcelable {

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("device_id")
    private String device_id;

    @SerializedName("device_type")
    private String device_type;

    @SerializedName("category_id")
    private String category_id;



    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }




    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }


    public RegistrationRequest() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeString(this.device_id);
        dest.writeString(this.device_type);
        dest.writeString(this.category_id);
    }

    protected RegistrationRequest(Parcel in) {
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.mobile = in.readString();
        this.device_id = in.readString();
        this.device_type = in.readString();
        this.category_id = in.readString();
    }

    public static final Creator<RegistrationRequest> CREATOR = new Creator<RegistrationRequest>() {
        @Override
        public RegistrationRequest createFromParcel(Parcel source) {
            return new RegistrationRequest(source);
        }

        @Override
        public RegistrationRequest[] newArray(int size) {
            return new RegistrationRequest[size];
        }
    };
}