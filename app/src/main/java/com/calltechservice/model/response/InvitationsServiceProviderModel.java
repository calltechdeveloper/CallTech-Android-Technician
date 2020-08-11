
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InvitationsServiceProviderModel implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("is_individual")
    private String is_individual;

    @SerializedName("id")
    private String id;

    @SerializedName("job_id")
    private String job_id;

    @SerializedName("provider_id")
    private String provider_id;

    @SerializedName("is_awarded")
    private String is_awarded;

    @SerializedName("awarded_amount")
    private String awarded_amount;

    @SerializedName("invoice_send")
    private String invoice_send;

    @SerializedName("created_date")
    private String created_date;

    @SerializedName("status")
    private String status;




    public String getName() {
        return name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIs_individual() {
        return is_individual;
    }

    public String getId() {
        return id;
    }

    public String getJob_id() {
        return job_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getIs_awarded() {
        return is_awarded;
    }

    public String getAwarded_amount() {
        return awarded_amount;
    }

    public String getInvoice_send() {
        return invoice_send;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getStatus() {
        return status;
    }




    public InvitationsServiceProviderModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.profile_pic);
        dest.writeString(this.mobile);
        dest.writeString(this.is_individual);
        dest.writeString(this.id);
        dest.writeString(this.job_id);
        dest.writeString(this.provider_id);
        dest.writeString(this.is_awarded);
        dest.writeString(this.awarded_amount);
        dest.writeString(this.invoice_send);
        dest.writeString(this.created_date);
        dest.writeString(this.status);
    }

    protected InvitationsServiceProviderModel(Parcel in) {
        this.name = in.readString();
        this.profile_pic = in.readString();
        this.mobile = in.readString();
        this.is_individual = in.readString();
        this.id = in.readString();
        this.job_id = in.readString();
        this.provider_id = in.readString();
        this.is_awarded = in.readString();
        this.awarded_amount = in.readString();
        this.invoice_send = in.readString();
        this.created_date = in.readString();
        this.status = in.readString();
    }

    public static final Creator<InvitationsServiceProviderModel> CREATOR = new Creator<InvitationsServiceProviderModel>() {
        @Override
        public InvitationsServiceProviderModel createFromParcel(Parcel source) {
            return new InvitationsServiceProviderModel(source);
        }

        @Override
        public InvitationsServiceProviderModel[] newArray(int size) {
            return new InvitationsServiceProviderModel[size];
        }
    };
}
