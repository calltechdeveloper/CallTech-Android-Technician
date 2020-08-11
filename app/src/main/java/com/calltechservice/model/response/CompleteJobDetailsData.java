package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CompleteJobDetailsData implements Parcelable {

    @SerializedName("job_id")
    private String jobId;
    @SerializedName("user_id")
    private String userId;
    private String title;
    private String description;
    @SerializedName("provider_id")
    private String providerId;
    @SerializedName("agency_emp_id")
    private String agencyEmpId;
    private String status;
    private String amount;
    @SerializedName("is_immediate")
    private String getIsImmediate;
    @SerializedName("schedule_date")
    private String scheduleDate;


    protected CompleteJobDetailsData(Parcel in) {
        jobId = in.readString();
        userId = in.readString();
        title = in.readString();
        description = in.readString();
        providerId = in.readString();
        agencyEmpId = in.readString();
        status = in.readString();
        amount = in.readString();
        getIsImmediate = in.readString();
        scheduleDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobId);
        dest.writeString(userId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(providerId);
        dest.writeString(agencyEmpId);
        dest.writeString(status);
        dest.writeString(amount);
        dest.writeString(getIsImmediate);
        dest.writeString(scheduleDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompleteJobDetailsData> CREATOR = new Creator<CompleteJobDetailsData>() {
        @Override
        public CompleteJobDetailsData createFromParcel(Parcel in) {
            return new CompleteJobDetailsData(in);
        }

        @Override
        public CompleteJobDetailsData[] newArray(int size) {
            return new CompleteJobDetailsData[size];
        }
    };

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAgency_emp_id() {
        return agencyEmpId;
    }

    public void setAgency_emp_id(String agency_emp_id) {
        this.agencyEmpId = agency_emp_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGetIsImmediate() {
        return getIsImmediate;
    }

    public void setGetIsImmediate(String getIsImmediate) {
        this.getIsImmediate = getIsImmediate;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}
