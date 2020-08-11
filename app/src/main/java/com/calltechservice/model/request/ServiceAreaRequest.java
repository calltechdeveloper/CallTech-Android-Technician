
package com.calltechservice.model.request;
import com.calltechservice.model.response.AreaList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ServiceAreaRequest {
    @SerializedName("area_list")
    private List<AreaList> mAreaList=new ArrayList<>();
    @SerializedName("provider_id")
    private String mProviderId;
    @SerializedName("rquest")
    private String request;

    public List<AreaList> getAreaList() {
        return mAreaList;
    }

    public void setAreaList(List<AreaList> areaList) {
        mAreaList = areaList;
    }

    public String getProviderId() {
        return mProviderId;
    }

    public void setProviderId(String providerId) {
        mProviderId = providerId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String mrequest) {
        request = mrequest;
    }

}
