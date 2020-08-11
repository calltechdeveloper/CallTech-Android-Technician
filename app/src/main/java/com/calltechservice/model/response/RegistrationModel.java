package com.calltechservice.model.response;


import com.google.gson.annotations.SerializedName;

public class RegistrationModel {

    private String name;
    private String mobile;
    private String email;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("rquest")
    private String rquest;

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

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRquest() {
        return rquest;
    }

    public void setRquest(String rquest) {
        this.rquest = rquest;
    }
}
