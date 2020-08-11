package com.calltechservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CountryModel implements Parcelable {

	@SerializedName("country")
	private String country;

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("code")
	private String code;

	@SerializedName("currency")
	private String currency;

	@SerializedName("currency_id")
	private String currencyId;

	protected CountryModel(Parcel in) {
		country = in.readString();
		symbol = in.readString();
		code = in.readString();
		currency = in.readString();
		currencyId = in.readString();
	}

	public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {
		@Override
		public CountryModel createFromParcel(Parcel in) {
			return new CountryModel(in);
		}

		@Override
		public CountryModel[] newArray(int size) {
			return new CountryModel[size];
		}
	};

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	public String getSymbol(){
		return symbol;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setCurrencyId(String currencyId){
		this.currencyId = currencyId;
	}

	public String getCurrencyId(){
		return currencyId;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	public CountryModel() {

	}
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(country);
		parcel.writeString(symbol);
		parcel.writeString(code);
		parcel.writeString(currency);
		parcel.writeString(currencyId);
	}


	@Override
	public String toString() {
		return this.country.equalsIgnoreCase("Select Country")?this.country:this.country+" ( "+code+" )";
	}
}