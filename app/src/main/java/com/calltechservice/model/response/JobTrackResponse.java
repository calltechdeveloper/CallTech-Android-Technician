
package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class JobTrackResponse implements Parcelable {

    @SerializedName("job")
    private JobNameDetailsModel job;

    @SerializedName("userdetail")
    private JobUserDetailsModel userdetail;


    @SerializedName("startjob")
    private JobStartDetailsModel startjob;

    @SerializedName("commplete_job")
    private JobEndDetailsModel commplete_job;

    @SerializedName("booking")
    private JobBookingDetailsModel booking_date;


    public JobUserDetailsModel getUserdetail() {
        return userdetail;
    }

    public JobStartDetailsModel getStartjob() {
        return startjob;
    }

    public JobEndDetailsModel getCommplete_job() {
        return commplete_job;
    }


    public JobNameDetailsModel getJob() {
        return job;
    }

    public JobBookingDetailsModel getBooking_date() {
        return booking_date;
    }



    public JobTrackResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.job, flags);
        dest.writeParcelable(this.userdetail, flags);
        dest.writeParcelable(this.startjob, flags);
        dest.writeParcelable(this.commplete_job, flags);
        dest.writeParcelable(this.booking_date, flags);
    }

    protected JobTrackResponse(Parcel in) {
        this.job = in.readParcelable(JobNameDetailsModel.class.getClassLoader());
        this.userdetail = in.readParcelable(JobUserDetailsModel.class.getClassLoader());
        this.startjob = in.readParcelable(JobStartDetailsModel.class.getClassLoader());
        this.commplete_job = in.readParcelable(JobEndDetailsModel.class.getClassLoader());
        this.booking_date = in.readParcelable(JobBookingDetailsModel.class.getClassLoader());
    }

    public static final Creator<JobTrackResponse> CREATOR = new Creator<JobTrackResponse>() {
        @Override
        public JobTrackResponse createFromParcel(Parcel source) {
            return new JobTrackResponse(source);
        }

        @Override
        public JobTrackResponse[] newArray(int size) {
            return new JobTrackResponse[size];
        }
    };
}
