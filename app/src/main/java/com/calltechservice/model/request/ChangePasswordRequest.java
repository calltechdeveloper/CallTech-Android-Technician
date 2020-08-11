
package com.calltechservice.model.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("new_password")
    private String NewPassword;
    @SerializedName("old_password")
    private String OldPassword;
    @SerializedName("user_id")
    private String UserId;

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}
