package com.traap.traapapp.apiServices.model.editUser.verifyRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("access")
    @Expose
    private String access;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

}