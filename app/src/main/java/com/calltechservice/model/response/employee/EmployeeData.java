package com.calltechservice.model.response.employee;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeData implements Parcelable {

    @SerializedName("agency_id")
    private String mAgencyId;
    @SerializedName("job_id")
    private String jobId;
    @SerializedName("is_individual")
    private String isIndividual;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("provider_id")
    private String mEmpId;
    @SerializedName("is_verified")
    private String mIsVerified;
    @SerializedName("job_done")
    private String mJobDone;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("lng")
    private String mLng;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_pic")
    private String mProfilePic;
    @SerializedName("rating")
    private String mRating;
    @SerializedName("area_list")
    private List<AreaList> areaList;
    private String status;
    @SerializedName("assigned_emp")
    private AssignedEmployee assignedEmp;
    @SerializedName("is_awarded")
    private String isAwareded;
    @SerializedName("invoice_send")
    private String invoiceSend;
    @SerializedName("awarded_amount")
    private String awaredAmount;

    public String getAwaredAmount() {
        return awaredAmount;
    }

    public void setAwaredAmount(String awaredAmount) {
        this.awaredAmount = awaredAmount;
    }

    public String getIsAwareded() {
        return isAwareded;
    }

    public void setIsAwareded(String isAwareded) {
        this.isAwareded = isAwareded;
    }

    public String getInvoiceSend() {
        return invoiceSend;
    }

    public void setInvoiceSend(String invoiceSend) {
        this.invoiceSend = invoiceSend;
    }

    protected EmployeeData(Parcel in) {
        mAgencyId = in.readString();
        jobId = in.readString();
        isIndividual = in.readString();
        mCreatedDate = in.readString();
        mDeviceToken = in.readString();
        mEmail = in.readString();
        mEmpId = in.readString();
        mIsVerified = in.readString();
        mJobDone = in.readString();
        mLat = in.readString();
        mLng = in.readString();
        mMobile = in.readString();
        mName = in.readString();
        mProfilePic = in.readString();
        mRating = in.readString();
        areaList = in.createTypedArrayList(AreaList.CREATOR);
        status = in.readString();
        invoiceSend = in.readString();
        isAwareded = in.readString();
        awaredAmount = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<EmployeeData> CREATOR = new Creator<EmployeeData>() {
        @Override
        public EmployeeData createFromParcel(Parcel in) {
            return new EmployeeData(in);
        }

        @Override
        public EmployeeData[] newArray(int size) {
            return new EmployeeData[size];
        }
    };

    public AssignedEmployee getAssignedEmp() {
        return assignedEmp;
    }

    public void setAssignedEmp(AssignedEmployee assignedEmp) {
        this.assignedEmp = assignedEmp;
    }

    private boolean isSelected;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(String isIndividual) {
        this.isIndividual = isIndividual;
    }

    public String getmAgencyId() {
        return mAgencyId;
    }

    public void setmAgencyId(String mAgencyId) {
        this.mAgencyId = mAgencyId;
    }

    public String getmCreatedDate() {
        return mCreatedDate;
    }

    public void setmCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }

    public String getmDeviceToken() {
        return mDeviceToken;
    }

    public void setmDeviceToken(String mDeviceToken) {
        this.mDeviceToken = mDeviceToken;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmEmpId(String mEmpId) {
        this.mEmpId = mEmpId;
    }

    public String getmIsVerified() {
        return mIsVerified;
    }

    public void setmIsVerified(String mIsVerified) {
        this.mIsVerified = mIsVerified;
    }

    public String getmJobDone() {
        return mJobDone;
    }

    public void setmJobDone(String mJobDone) {
        this.mJobDone = mJobDone;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLng() {
        return mLng;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mAgencyId);
        parcel.writeString(jobId);
        parcel.writeString(isIndividual);
        parcel.writeString(mCreatedDate);
        parcel.writeString(mDeviceToken);
        parcel.writeString(mEmail);
        parcel.writeString(mEmpId);
        parcel.writeString(mIsVerified);
        parcel.writeString(mJobDone);
        parcel.writeString(mLat);
        parcel.writeString(mLng);
        parcel.writeString(mMobile);
        parcel.writeString(mName);
        parcel.writeString(mProfilePic);
        parcel.writeString(mRating);
        parcel.writeTypedList(areaList);
        parcel.writeString(status);
        parcel.writeString(invoiceSend);
        parcel.writeString(isAwareded);
        parcel.writeString(awaredAmount);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }



    public String getmEmpId() {
        return mEmpId;
    }

    public String getmProfilePic() {
        return mProfilePic;
    }

    public void setmProfilePic(String mProfilePic) {
        this.mProfilePic = mProfilePic;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public List<AreaList> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaList> areaList) {
        this.areaList = areaList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
