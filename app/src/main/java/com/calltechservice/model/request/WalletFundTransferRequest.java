package com.calltechservice.model.request;

import com.google.gson.annotations.SerializedName;

public class WalletFundTransferRequest {

    @SerializedName("amount")
    private String amount;

    @SerializedName("tpassword")
    private String tpassword;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("receiver_id")
    private String receiverId;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTpassword(String tpassword) {
        this.tpassword = tpassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}