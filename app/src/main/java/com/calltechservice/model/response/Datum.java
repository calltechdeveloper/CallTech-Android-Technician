
package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("category_image")
    private String mCategoryImage;
    @SerializedName("category_name")
    private String mCategoryName;
    @SerializedName("sub_category")
    private List<SubCategory> mSubCategory;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getCategoryImage() {
        return mCategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        mCategoryImage = categoryImage;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public List<SubCategory> getSubCategory() {
        return mSubCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        mSubCategory = subCategory;
    }

}
