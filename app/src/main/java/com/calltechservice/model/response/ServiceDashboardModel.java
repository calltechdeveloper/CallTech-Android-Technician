package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceDashboardModel implements Parcelable {

	@SerializedName("total_job")
	private String total_job;

	@SerializedName("inprogress_job")
	private String inprogress_job;

	@SerializedName("commplete_job")
	private String commplete_job;

	@SerializedName("requested_job")
	private String requested_job;

	public String getTotal_job() {
		return total_job;
	}

	public String getInprogress_job() {
		return inprogress_job;
	}

	public String getCommplete_job() {
		return commplete_job;
	}

	public String getRequested_job() {
		return requested_job;
	}

	public ArrayList<CompleteJobResponse> getComplete_joblist() {
		return complete_joblist;
	}

	public ArrayList<JobInvitationResponse> getRequested_joblist() {
		return requested_joblist;
	}

	@SerializedName("complete_joblist")
	private ArrayList<CompleteJobResponse> complete_joblist;

	@SerializedName("requested_joblist")
	private ArrayList<JobInvitationResponse> requested_joblist;


	public ServiceDashboardModel() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.total_job);
		dest.writeString(this.inprogress_job);
		dest.writeString(this.commplete_job);
		dest.writeString(this.requested_job);
		dest.writeTypedList(this.complete_joblist);
		dest.writeTypedList(this.requested_joblist);
	}

	protected ServiceDashboardModel(Parcel in) {
		this.total_job = in.readString();
		this.inprogress_job = in.readString();
		this.commplete_job = in.readString();
		this.requested_job = in.readString();
		this.complete_joblist = in.createTypedArrayList(CompleteJobResponse.CREATOR);
		this.requested_joblist = in.createTypedArrayList(JobInvitationResponse.CREATOR);
	}

	public static final Creator<ServiceDashboardModel> CREATOR = new Creator<ServiceDashboardModel>() {
		@Override
		public ServiceDashboardModel createFromParcel(Parcel source) {
			return new ServiceDashboardModel(source);
		}

		@Override
		public ServiceDashboardModel[] newArray(int size) {
			return new ServiceDashboardModel[size];
		}
	};
}
