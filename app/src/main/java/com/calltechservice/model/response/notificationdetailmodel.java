package com.calltechservice.model.response;

public class notificationdetailmodel {



    /**
     * notication_id : 131
     * user_id : 100
     * job_id : 173
     * provider_id : 206
     * title : Invoice Amount 500
     * message : Job invoice sent successfully.
     * notification_date : 2019-08-17 12:03:49
     */

    private String notication_id;
    private String user_id;
    private String job_id;
    private String provider_id;
    private String title;
    private String message;
    private String notification_date;

    public String getNotication_id() {
        return notication_id;
    }

    public void setNotication_id(String notication_id) {
        this.notication_id = notication_id;
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

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }
}
