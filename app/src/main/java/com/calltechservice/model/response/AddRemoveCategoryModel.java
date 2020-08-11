package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddRemoveCategoryModel implements Parcelable{


	@SerializedName("category_id")
	private String category_id;

	@SerializedName("service_image")
	private String category_image;

	@SerializedName("category_name")
	private String category_name;

	@SerializedName("service_name")
	private String service_name;

	@SerializedName("service_locations")
	private List<AreaList> location;

	public List<AreaList> getLocation() {
		return location;
	}

	public void setLocation(List<AreaList> location) {
		this.location = location;
	}





	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	@SerializedName("service_id")
	private String service_id;




	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}



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


	public AddRemoveCategoryModel() {
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
		dest.writeString(this.service_name);
		dest.writeList(this.location);
		dest.writeString(this.service_id);
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
	}

	protected AddRemoveCategoryModel(Parcel in) {
		this.category_id = in.readString();
		this.category_image = in.readString();
		this.category_name = in.readString();
		this.service_name = in.readString();
		this.location = new ArrayList<AreaList>();
		in.readList(this.location, AreaList.class.getClassLoader());
		this.service_id = in.readString();
		this.isSelected = in.readByte() != 0;
	}

	public static final Creator<AddRemoveCategoryModel> CREATOR = new Creator<AddRemoveCategoryModel>() {
		@Override
		public AddRemoveCategoryModel createFromParcel(Parcel source) {
			return new AddRemoveCategoryModel(source);
		}

		@Override
		public AddRemoveCategoryModel[] newArray(int size) {
			return new AddRemoveCategoryModel[size];
		}
	};
}
