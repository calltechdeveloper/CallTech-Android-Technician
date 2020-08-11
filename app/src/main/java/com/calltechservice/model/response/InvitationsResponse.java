
package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InvitationsResponse {

    @SerializedName("data")
    private InvitationData mInvitations;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public InvitationData getmInvitations() {
        return mInvitations;
    }

    public void setmInvitations(InvitationData mInvitations) {
        this.mInvitations = mInvitations;
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
