
package com.calltechservice.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SendInvitationRequest implements Parcelable {

    @SerializedName("description")
    private String mDescription;

    @SerializedName("provider_ids")
    private StringBuilder provider_ids;

    @SerializedName("is_immediate")
    private String mIsImmediate;

    @SerializedName("schedule_date")
    private String mScheduleDate;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("user_id")
    private String mUserId;


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StringBuilder getProvider_ids() {
        return provider_ids;
    }

    public void setProvider_ids(StringBuilder provider_ids) {
        this.provider_ids = provider_ids;
    }

    public String getIsImmediate() {
        return mIsImmediate;
    }

    public void setIsImmediate(String isImmediate) {
        mIsImmediate = isImmediate;
    }

    public String getScheduleDate() {
        return mScheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        mScheduleDate = scheduleDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
        dest.writeSerializable(this.provider_ids);
        dest.writeString(this.mIsImmediate);
        dest.writeString(this.mScheduleDate);
        dest.writeString(this.mTitle);
        dest.writeString(this.mUserId);
    }

    public SendInvitationRequest() {
    }

    protected SendInvitationRequest(Parcel in) {
        this.mDescription = in.readString();
        this.provider_ids = (StringBuilder) in.readSerializable();
        this.mIsImmediate = in.readString();
        this.mScheduleDate = in.readString();
        this.mTitle = in.readString();
        this.mUserId = in.readString();
    }

    public static final Parcelable.Creator<SendInvitationRequest> CREATOR = new Parcelable.Creator<SendInvitationRequest>() {
        @Override
        public SendInvitationRequest createFromParcel(Parcel source) {
            return new SendInvitationRequest(source);
        }

        @Override
        public SendInvitationRequest[] newArray(int size) {
            return new SendInvitationRequest[size];
        }
    };
}
