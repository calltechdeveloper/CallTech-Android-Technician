package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServiceSubCtegoryModel implements Parcelable{

	@SerializedName("service_id")
	private String service_id;

	@SerializedName("service_image")
	private String service_image;

	@SerializedName("service_name")
	private String service_name;


	private boolean isSelected;


	public String getService_id() {
		return service_id;
	}

	public String getService_image() {
		return service_image;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_image(String service_image) {
		this.service_image = service_image;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}



	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.service_id);
		dest.writeString(this.service_image);
		dest.writeString(this.service_name);
	}

	public ServiceSubCtegoryModel() {
	}

	protected ServiceSubCtegoryModel(Parcel in) {
		this.service_id = in.readString();
		this.service_image = in.readString();
		this.service_name = in.readString();
	}

	public static final Creator<ServiceSubCtegoryModel> CREATOR = new Creator<ServiceSubCtegoryModel>() {
		@Override
		public ServiceSubCtegoryModel createFromParcel(Parcel source) {
			return new ServiceSubCtegoryModel(source);
		}

		@Override
		public ServiceSubCtegoryModel[] newArray(int size) {
			return new ServiceSubCtegoryModel[size];
		}
	};
}
