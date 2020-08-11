
package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UploadImageResponse {

    @SerializedName("data")
    private String mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
