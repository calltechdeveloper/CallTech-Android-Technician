
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class InvitationResponse implements Parcelable {


    @SerializedName("provider_details")
    private ProviderDetailsData providerDetails;
    @SerializedName("job_details")
    private JobDetailsData jobDetails;
    private String  status;
    private String  award;

    public String getKmcharges() {
        return kmcharges;
    }

    public void setKmcharges(String kmcharges) {
        this.kmcharges = kmcharges;
    }

    @SerializedName("km_charges")
    private String  kmcharges;


    @SerializedName("invoice_Status")
    private String  invoiceStatus;
    @SerializedName("awarded_amount")
    private String  awardedAmount;

    public String getService_amount() {
        return service_amount;
    }

    public void setService_amount(String service_amount) {
        this.service_amount = service_amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @SerializedName("service_amount")
    private String  service_amount;

    @SerializedName("total")
    private String  total;















    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getAwardedAmount() {
        return awardedAmount;
    }

    public void setAwardedAmount(String awardedAmount) {
        this.awardedAmount = awardedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProviderDetailsData getProviderDetails() {
        return providerDetails;
    }

    public void setProviderDetails(ProviderDetailsData providerDetails) {
        this.providerDetails = providerDetails;
    }

    public JobDetailsData getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetailsData jobDetails) {
        this.jobDetails = jobDetails;
    }

    public InvitationResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.providerDetails, flags);
        dest.writeParcelable(this.jobDetails, flags);
        dest.writeString(this.status);
        dest.writeString(this.award);
        dest.writeString(this.kmcharges);
        dest.writeString(this.invoiceStatus);
        dest.writeString(this.awardedAmount);
        dest.writeString(this.service_amount);
        dest.writeString(this.total);
    }

    protected InvitationResponse(Parcel in) {
        this.providerDetails = in.readParcelable(ProviderDetailsData.class.getClassLoader());
        this.jobDetails = in.readParcelable(JobDetailsData.class.getClassLoader());
        this.status = in.readString();
        this.award = in.readString();
        this.kmcharges = in.readString();
        this.invoiceStatus = in.readString();
        this.awardedAmount = in.readString();
        this.service_amount = in.readString();
        this.total = in.readString();
    }

    public static final Creator<InvitationResponse> CREATOR = new Creator<InvitationResponse>() {
        @Override
        public InvitationResponse createFromParcel(Parcel source) {
            return new InvitationResponse(source);
        }

        @Override
        public InvitationResponse[] newArray(int size) {
            return new InvitationResponse[size];
        }
    };
}
