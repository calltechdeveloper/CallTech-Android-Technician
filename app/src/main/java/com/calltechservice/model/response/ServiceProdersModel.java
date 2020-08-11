package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServiceProdersModel implements Parcelable{

	@SerializedName("name")
	private String name;

	@SerializedName("category_name")
	private String category_name;

	@SerializedName("category_image")
	private String category_image;

	@SerializedName("company_name")
	private String company_name;

	@SerializedName("email")
	private String email;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("service_area_name")
	private String service_area_name;

	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public String getAbout() {
		return about;
	}

	public String getLat() {
		return lat;
	}

	public String getLog() {
		return log;
	}

	public String getLocation() {
		return location;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public String getProvider_id() {
		return provider_id;
	}

	public String getTotal_exp() {
		return total_exp;
	}

	public String getRating() {
		return rating;
	}

	public String getTotal_job() {
		return total_job;
	}

	@SerializedName("about")
	private String about;

	@SerializedName("lat")
	private String lat;

	@SerializedName("long")
	private String log;

	@SerializedName("location")
	private String location;

	@SerializedName("profile_pic")
	private String profile_pic;

	@SerializedName("provider_id")
	private String provider_id;

	@SerializedName("total_exp")
	private String total_exp;

	@SerializedName("rating")
	private String rating;

	@SerializedName("total_job")
	private String total_job;


	public String getName() {
		return name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public String getCategory_image() {
		return category_image;
	}

	public String getCompany_name() {
		return company_name;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public String getService_area_name() {
		return service_area_name;
	}


	public ServiceProdersModel() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.category_name);
		dest.writeString(this.category_image);
		dest.writeString(this.company_name);
		dest.writeString(this.email);
		dest.writeString(this.mobile);
		dest.writeString(this.service_area_name);
		dest.writeString(this.about);
		dest.writeString(this.lat);
		dest.writeString(this.log);
		dest.writeString(this.location);
		dest.writeString(this.profile_pic);
		dest.writeString(this.provider_id);
		dest.writeString(this.total_exp);
		dest.writeString(this.rating);
		dest.writeString(this.total_job);
	}

	protected ServiceProdersModel(Parcel in) {
		this.name = in.readString();
		this.category_name = in.readString();
		this.category_image = in.readString();
		this.company_name = in.readString();
		this.email = in.readString();
		this.mobile = in.readString();
		this.service_area_name = in.readString();
		this.about = in.readString();
		this.lat = in.readString();
		this.log = in.readString();
		this.location = in.readString();
		this.profile_pic = in.readString();
		this.provider_id = in.readString();
		this.total_exp = in.readString();
		this.rating = in.readString();
		this.total_job = in.readString();
	}

	public static final Creator<ServiceProdersModel> CREATOR = new Creator<ServiceProdersModel>() {
		@Override
		public ServiceProdersModel createFromParcel(Parcel source) {
			return new ServiceProdersModel(source);
		}

		@Override
		public ServiceProdersModel[] newArray(int size) {
			return new ServiceProdersModel[size];
		}
	};
}
