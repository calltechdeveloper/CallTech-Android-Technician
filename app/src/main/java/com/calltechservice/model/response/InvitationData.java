
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.calltechservice.model.response.employee.EmployeeData;

import java.util.List;

@SuppressWarnings("unused")
public class InvitationData implements Parcelable {

    @SerializedName("job_id")
    private String jobId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("is_immediate")
    private String mIsImmediate;
    @SerializedName("schedule_date")
    private String scheduleDate;
    @SerializedName("emp_list")
    private List<EmployeeData> empList;
    @SerializedName("status")
    private String status;
    @SerializedName("assigned_emp_id")
    private String assignedEmpId;
    @SerializedName("provider_id")
    private String providerId;
    @SerializedName("agency_emp_id")
    private String agencyEmpId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAgencyEmpId() {
        return agencyEmpId;
    }

    public void setAgencyEmpId(String agencyEmpId) {
        this.agencyEmpId = agencyEmpId;
    }

    protected InvitationData(Parcel in) {
        jobId = in.readString();
        mUserId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mIsImmediate = in.readString();
        scheduleDate = in.readString();
        empList = in.createTypedArrayList(EmployeeData.CREATOR);
        status = in.readString();
        assignedEmpId = in.readString();
        agencyEmpId = in.readString();
        providerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobId);
        dest.writeString(mUserId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mIsImmediate);
        dest.writeString(scheduleDate);
        dest.writeTypedList(empList);
        dest.writeString(status);
        dest.writeString(assignedEmpId);
        dest.writeString(agencyEmpId);
        dest.writeString(providerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InvitationData> CREATOR = new Creator<InvitationData>() {
        @Override
        public InvitationData createFromParcel(Parcel in) {
            return new InvitationData(in);
        }

        @Override
        public InvitationData[] newArray(int size) {
            return new InvitationData[size];
        }
    };

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmIsImmediate() {
        return mIsImmediate;
    }

    public void setmIsImmediate(String mIsImmediate) {
        this.mIsImmediate = mIsImmediate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getInvitationId() {
        return jobId;
    }

    public void setInvitationId(String invitationId) {
        this.jobId = invitationId;
    }

    public String getAssignedEmpId() {
        return assignedEmpId;
    }

    public void setAssignedEmpId(String assignedEmpId) {
        this.assignedEmpId = assignedEmpId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeData> getEmpList() {
        return empList;
    }

    public void setEmpList(List<EmployeeData> empList) {
        this.empList = empList;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getIsImmediate() {
        return mIsImmediate;
    }

    public void setIsImmediate(String isImmediate) {
        mIsImmediate = isImmediate;
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

}
