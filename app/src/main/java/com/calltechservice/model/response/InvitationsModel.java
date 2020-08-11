
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvitationsModel implements Parcelable {

    @SerializedName("job_id")
    private String jobId;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("provider_id")
    private String provider_id;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("is_immediate")
    private String mIsImmediate;

    @SerializedName("schedule_date")
    private String mScheduleDate;

    @SerializedName("bookingid")
    private String bookingid;

    @SerializedName("subcat_id")
    private String subcat_id;

    @SerializedName("agency_emp_id")
    private String agency_emp_id;

    @SerializedName("amount")
    private String amount;

    @SerializedName("serviceprovider")
    private List<InvitationsServiceProviderModel> serviceprovider;

    public List<InvitationsServiceProviderModel> getServiceprovider() {
        return serviceprovider;
    }

    public String getJobId() {
        return jobId;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmIsImmediate() {
        return mIsImmediate;
    }

    public String getmScheduleDate() {
        return mScheduleDate;
    }


    public InvitationsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jobId);
        dest.writeString(this.mUserId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.provider_id);
        dest.writeString(this.mStatus);
        dest.writeString(this.mIsImmediate);
        dest.writeString(this.mScheduleDate);
        dest.writeString(this.bookingid);
        dest.writeString(this.subcat_id);
        dest.writeString(this.agency_emp_id);
        dest.writeString(this.amount);
        dest.writeTypedList(this.serviceprovider);
    }

    protected InvitationsModel(Parcel in) {
        this.jobId = in.readString();
        this.mUserId = in.readString();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.provider_id = in.readString();
        this.mStatus = in.readString();
        this.mIsImmediate = in.readString();
        this.mScheduleDate = in.readString();
        this.bookingid = in.readString();
        this.subcat_id = in.readString();
        this.agency_emp_id = in.readString();
        this.amount = in.readString();
        this.serviceprovider = in.createTypedArrayList(InvitationsServiceProviderModel.CREATOR);
    }

    public static final Creator<InvitationsModel> CREATOR = new Creator<InvitationsModel>() {
        @Override
        public InvitationsModel createFromParcel(Parcel source) {
            return new InvitationsModel(source);
        }

        @Override
        public InvitationsModel[] newArray(int size) {
            return new InvitationsModel[size];
        }
    };
}
