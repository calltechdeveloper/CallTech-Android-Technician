package com.calltechservice.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class BookingCompleteModel implements Parcelable {

    @SerializedName("bookingNo")
    private String bookingNo;

    @SerializedName("first_name")
    private String name;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("sourceLati")
    private String sourceLati;

    @SerializedName("sourceLongi")
    private String sourceLongi;

    @SerializedName("destiLati")
    private String destiLati;

    @SerializedName("destiLongi")
    private String destiLongi;

    @SerializedName("rideDate")
    private String rideDate;

    @SerializedName("rideType")
    private String rideType;

    @SerializedName("cabType")
    private String cabType;

    @SerializedName("distance")
    private String distance;

    @SerializedName("totalFare")
    private String price;


    protected BookingCompleteModel(Parcel in) {
        bookingNo = in.readString();
        name = in.readString();
        telephone = in.readString();
        sourceLati = in.readString();
        sourceLongi = in.readString();
        destiLati = in.readString();
        destiLongi = in.readString();
        rideDate = in.readString();
        rideType = in.readString();
        cabType = in.readString();
        distance = in.readString();
        price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookingNo);
        dest.writeString(name);
        dest.writeString(telephone);
        dest.writeString(sourceLati);
        dest.writeString(sourceLongi);
        dest.writeString(destiLati);
        dest.writeString(destiLongi);
        dest.writeString(rideDate);
        dest.writeString(rideType);
        dest.writeString(cabType);
        dest.writeString(distance);
        dest.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingCompleteModel> CREATOR = new Creator<BookingCompleteModel>() {
        @Override
        public BookingCompleteModel createFromParcel(Parcel in) {
            return new BookingCompleteModel(in);
        }

        @Override
        public BookingCompleteModel[] newArray(int size) {
            return new BookingCompleteModel[size];
        }
    };

    public String getBookingNo() {
        return bookingNo;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getSourceLati() {
        return sourceLati;
    }

    public String getSourceLongi() {
        return sourceLongi;
    }

    public String getDestiLati() {
        return destiLati;
    }

    public String getDestiLongi() {
        return destiLongi;
    }

    public String getRideDate() {
        return rideDate;
    }

    public String getRideType() {
        return rideType;
    }

    public String getCabType() {
        return cabType;
    }

    public String getDistance() {
        return distance;
    }

    public String getPrice() {
        return price;
    }

}
