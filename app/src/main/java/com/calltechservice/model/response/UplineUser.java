package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

public class UplineUser {

	@SerializedName("image")
	private String image;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	private String telephone;

	public String getImage(){
		return image;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getUserId(){
		return userId;
	}

	public String getName(){
		return name;
	}
}