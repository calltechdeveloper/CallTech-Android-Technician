package com.calltechservice.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonResponse<T> {
    @SerializedName("data")
    private List<T> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<T> getmData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
