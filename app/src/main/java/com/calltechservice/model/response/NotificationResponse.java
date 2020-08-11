package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

public class NotificationResponse {


    /**
     * id : 5
     * user_id : 74
     * job_id : 117
     * provider_id : 156
     * title : Maxtra work has been sent.
     * message : Job invitation sent.
     * status : 1
     * date : 2019-10-04
     */

    private String id;
    private String user_id;
    private String job_id;
    private String provider_id;
    private String title;
    private String message;
    private String status;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
