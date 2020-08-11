package com.calltechservice.model;

import com.google.gson.annotations.SerializedName;

public class LocationResponse<T>{

    @SerializedName("data")
    private T data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
    public int getStatus() {
        return status;
    }
}