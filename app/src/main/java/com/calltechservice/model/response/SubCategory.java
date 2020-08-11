
package com.calltechservice.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SubCategory implements Parcelable {

    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("sub_cat_id")
    private String mSubCatId;
    @SerializedName("sub_cat_image")
    private String mSubCatImage;
    @SerializedName("sub_cat_name")
    private String mSubCatName;

    protected SubCategory(Parcel in) {
        mCategoryId = in.readString();
        mSubCatId = in.readString();
        mSubCatImage = in.readString();
        mSubCatName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategoryId);
        dest.writeString(mSubCatId);
        dest.writeString(mSubCatImage);
        dest.writeString(mSubCatName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getSubCatId() {
        return mSubCatId;
    }

    public void setSubCatId(String subCatId) {
        mSubCatId = subCatId;
    }

    public String getSubCatImage() {
        return mSubCatImage;
    }

    public void setSubCatImage(String subCatImage) {
        mSubCatImage = subCatImage;
    }

    public String getSubCatName() {
        return mSubCatName;
    }

    public void setSubCatName(String subCatName) {
        mSubCatName = subCatName;
    }

}
