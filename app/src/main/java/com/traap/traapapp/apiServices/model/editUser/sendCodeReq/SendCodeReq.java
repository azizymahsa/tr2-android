package com.traap.traapapp.apiServices.model.editUser.sendCodeReq;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendCodeReq {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("country_code")
    @Expose
    private String country_code;

    public String getCountry_code()
    {
        return country_code;
    }

    public void setCountry_code(String country_code)
    {
        this.country_code = country_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}