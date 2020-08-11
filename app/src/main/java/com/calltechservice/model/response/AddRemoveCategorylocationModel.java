package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddRemoveCategorylocationModel implements Parcelable {

	@SerializedName("area_name")
	private String area_name;

	@SerializedName("lng")
	private String lng;

	@SerializedName("lat")
	private String lat;




	public String getArea_name() {
		return area_name;
	}

	public String getLng() {
		return lng;
	}

	public String getLat() {
		return lat;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.area_name);
		dest.writeString(this.lng);
		dest.writeString(this.lat);
	}

	public AddRemoveCategorylocationModel() {
	}

	protected AddRemoveCategorylocationModel(Parcel in) {
		this.area_name = in.readString();
		this.lng = in.readString();
		this.lat = in.readString();
	}

	public static final Parcelable.Creator<AddRemoveCategorylocationModel> CREATOR = new Parcelable.Creator<AddRemoveCategorylocationModel>() {
		@Override
		public AddRemoveCategorylocationModel createFromParcel(Parcel source) {
			return new AddRemoveCategorylocationModel(source);
		}

		@Override
		public AddRemoveCategorylocationModel[] newArray(int size) {
			return new AddRemoveCategorylocationModel[size];
		}
	};
}
