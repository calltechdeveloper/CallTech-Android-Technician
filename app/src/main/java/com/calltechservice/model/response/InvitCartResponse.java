package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class InvitCartResponse {

    @SerializedName("data")
    private List<InvitationData> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;


       public List<InvitationData> getmData() {
            return mData;
        }
    public void setmData(List<InvitationData> mData) {
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
