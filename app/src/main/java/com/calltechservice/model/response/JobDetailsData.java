package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class JobDetailsData implements Parcelable {


//             * job_id : 47
//             * bookingid :
//             * user_id : 95
//             * title : job 0010
//             * subcat_id : 0
//             * description : Testing job
//             * provider_id : 182
//             * agency_emp_id : 0
//             * status : 2
//             * amount : 0
//             * is_immediate : 0
//             * schedule_date : 2019-07-06 12:39:00
//             * paydate : 2019-07-06
//             * job_location : C-69, 2nd Floor, Suite 1, Sector 58, Bishanpura Village, Sector 58, Noida, Uttar Pradesh 201301, India
//             * job_loaction_latitude : 28.6098803
//             * job_location_longitude : 77.3542519
//             * working_hours : 5
//             */

    private String job_id;
    private String bookingid;
    private String user_id;
    private String title;
    private String subcat_id;
    private String description;
    private String provider_id;
    private String agency_emp_id;
    private String status;
    private String amount;
    private String is_immediate;
    private String schedule_date;
    private String paydate;
    private String job_location;
    private String job_loaction_latitude;
    private String job_location_longitude;
    private String working_hours;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(String subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getAgency_emp_id() {
        return agency_emp_id;
    }

    public void setAgency_emp_id(String agency_emp_id) {
        this.agency_emp_id = agency_emp_id;
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

    public String getIs_immediate() {
        return is_immediate;
    }

    public void setIs_immediate(String is_immediate) {
        this.is_immediate = is_immediate;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_loaction_latitude() {
        return job_loaction_latitude;
    }

    public void setJob_loaction_latitude(String job_loaction_latitude) {
        this.job_loaction_latitude = job_loaction_latitude;
    }

    public String getJob_location_longitude() {
        return job_location_longitude;
    }

    public void setJob_location_longitude(String job_location_longitude) {
        this.job_location_longitude = job_location_longitude;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
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
        dest.writeString(this.subcat_id);
        dest.writeString(this.description);
        dest.writeString(this.provider_id);
        dest.writeString(this.agency_emp_id);
        dest.writeString(this.status);
        dest.writeString(this.amount);
        dest.writeString(this.is_immediate);
        dest.writeString(this.schedule_date);
        dest.writeString(this.paydate);
        dest.writeString(this.job_location);
        dest.writeString(this.job_loaction_latitude);
        dest.writeString(this.job_location_longitude);
        dest.writeString(this.working_hours);
    }

    public JobDetailsData() {
    }

    protected JobDetailsData(Parcel in) {
        this.job_id = in.readString();
        this.bookingid = in.readString();
        this.user_id = in.readString();
        this.title = in.readString();
        this.subcat_id = in.readString();
        this.description = in.readString();
        this.provider_id = in.readString();
        this.agency_emp_id = in.readString();
        this.status = in.readString();
        this.amount = in.readString();
        this.is_immediate = in.readString();
        this.schedule_date = in.readString();
        this.paydate = in.readString();
        this.job_location = in.readString();
        this.job_loaction_latitude = in.readString();
        this.job_location_longitude = in.readString();
        this.working_hours = in.readString();
    }

    public static final Parcelable.Creator<JobDetailsData> CREATOR = new Parcelable.Creator<JobDetailsData>() {
        @Override
        public JobDetailsData createFromParcel(Parcel source) {
            return new JobDetailsData(source);
        }

        @Override
        public JobDetailsData[] newArray(int size) {
            return new JobDetailsData[size];
        }
    };
}


