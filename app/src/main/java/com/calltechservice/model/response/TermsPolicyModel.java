package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

public class TermsPolicyModel implements Parcelable {

    private String id;
    private String page_name;
    private String description;
    private String posted_date;

    protected TermsPolicyModel(Parcel in) {
        id = in.readString();
        page_name = in.readString();
        description = in.readString();
        posted_date = in.readString();
    }

    public static final Creator<TermsPolicyModel> CREATOR = new Creator<TermsPolicyModel>() {
        @Override
        public TermsPolicyModel createFromParcel(Parcel in) {
            return new TermsPolicyModel(in);
        }

        @Override
        public TermsPolicyModel[] newArray(int size) {
            return new TermsPolicyModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(page_name);
        parcel.writeString(description);
        parcel.writeString(posted_date);
    }

    public String getId() {
        return id;
    }

    public String getPage_name() {
        return page_name;
    }

    public String getDescription() {
        return description;
    }

    public String getPosted_date() {
        return posted_date;
    }
}
