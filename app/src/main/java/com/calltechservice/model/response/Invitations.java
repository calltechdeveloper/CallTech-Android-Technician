
package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Invitations {

    @SerializedName("job_id")
    private String jobId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("assigned_emp_id")
    private String mAssignedEmpId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("is_immediate")
    private String mIsImmediate;
    @SerializedName("schedule_date")
    private String mScheduleDate;



    public String getAssignedEmpId() {
        return mAssignedEmpId;
    }

    public void setAssignedEmpId(String assignedEmpId) {
        mAssignedEmpId = assignedEmpId;
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

    public String getScheduleDate() {
        return mScheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        mScheduleDate = scheduleDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
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
