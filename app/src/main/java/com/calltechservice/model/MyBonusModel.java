package com.calltechservice.model;

import com.google.gson.annotations.SerializedName;

public class MyBonusModel {

    @SerializedName("receive_date")
    private String receiveDate;

    @SerializedName("image")
    private String image;

    @SerializedName("ttype")
    private String level;

    @SerializedName("name")
    private String name;

    @SerializedName("credit_amt")
    private String commission;

    @SerializedName("product_name")
    private String remark;

    @SerializedName("transaction_no")
    private String transactionNo;

    @SerializedName("sender_id")
    private String downlineId;

    @SerializedName("status")
    private String status;


    public String getReceiveDate() {
        return receiveDate;
    }

    public String getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getCommission() {
        return commission;
    }

    public String getRemark() {
        return remark;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public String getDownlineId() {
        return downlineId;
    }

    public String getStatus() {
        return status;
    }
}