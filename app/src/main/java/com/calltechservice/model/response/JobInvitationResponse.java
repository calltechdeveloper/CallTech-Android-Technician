
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class JobInvitationResponse implements Parcelable {

    public String getJob_id() {
        return job_id;
    }

    public String getBookingid() {
        return bookingid;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSubcat_id() {
        return subcat_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getAgency_emp_id() {
        return agency_emp_id;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getIs_immediate() {
        return is_immediate;
    }

    public String getSchedule_date() {
        return schedule_date;
    }



    @SerializedName("job_id")
    private String job_id;

    @SerializedName("bookingid")
    private String bookingid;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("subcat_id")
    private String subcat_id;

    @SerializedName("provider_id")
    private String provider_id;

    @SerializedName("agency_emp_id")
    private String agency_emp_id;

    @SerializedName("status")
    private String status;

    @SerializedName("amount")
    private String amount;

    @SerializedName("is_immediate")
    private String is_immediate;

    @SerializedName("schedule_date")
    private String schedule_date;

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile")
    private String mobile;


    public JobInvitationResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.job_id);
        dest.writeString(this.bookingid);
        dest.writeString(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.subcat_id);
        dest.writeString(this.provider_id);
        dest.writeString(this.agency_emp_id);
        dest.writeString(this.status);
        dest.writeString(this.amount);
        dest.writeString(this.is_immediate);
        dest.writeString(this.schedule_date);
        dest.writeString(this.profile_pic);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
    }

    protected JobInvitationResponse(Parcel in) {
        this.job_id = in.readString();
        this.bookingid = in.readString();
        this.user_id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.subcat_id = in.readString();
        this.provider_id = in.readString();
        this.agency_emp_id = in.readString();
        this.status = in.readString();
        this.amount = in.readString();
        this.is_immediate = in.readString();
        this.schedule_date = in.readString();
        this.profile_pic = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
    }

    public static final Creator<JobInvitationResponse> CREATOR = new Creator<JobInvitationResponse>() {
        @Override
        public JobInvitationResponse createFromParcel(Parcel source) {
            return new JobInvitationResponse(source);
        }

        @Override
        public JobInvitationResponse[] newArray(int size) {
            return new JobInvitationResponse[size];
        }
    };
}
