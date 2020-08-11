package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SelectCategoryResponse implements Parcelable{
	@SerializedName("category_id")
	private String category_id;

	@SerializedName("category_image")
	private String category_image;

	@SerializedName("category_name")
	private String category_name;

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_image() {
		return category_image;
	}

	public void setCategory_image(String category_image) {
		this.category_image = category_image;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}






	@Override
	public String toString(){
		return this.category_name.equalsIgnoreCase("Select Package")?this.category_name:this.category_name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.category_id);
		dest.writeString(this.category_image);
		dest.writeString(this.category_name);
	}

	public SelectCategoryResponse() {
	}

	protected SelectCategoryResponse(Parcel in) {
		this.category_id = in.readString();
		this.category_image = in.readString();
		this.category_name = in.readString();
	}

	public static final Creator<SelectCategoryResponse> CREATOR = new Creator<SelectCategoryResponse>() {
		@Override
		public SelectCategoryResponse createFromParcel(Parcel source) {
			return new SelectCategoryResponse(source);
		}

		@Override
		public SelectCategoryResponse[] newArray(int size) {
			return new SelectCategoryResponse[size];
		}
	};
}
