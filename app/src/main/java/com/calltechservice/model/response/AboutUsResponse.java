package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;


public class AboutUsResponse {

    /**
     * status : 1
     * message : Data Found
     * data : {"id":"1","page_name":"About Us","description":" CallTech is best app in the demand service provider industry<\/p>\r\n","posted_date":"2019-03-27"}
     */



    /**
     * id : 1
     * page_name : About Us
     * description :  CallTech is best app in the demand service provider industry</p>
     * posted_date : 2019-03-27
     */

    private String id;
    private String page_name;
    private String description;
    private String posted_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }
}
