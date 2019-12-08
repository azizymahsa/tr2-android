package com.traap.traapapp.apiServices.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyResponse
{

    @SerializedName("profile")
    @Expose
    private Profile profile;

    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("gds_token")
    @Expose
    private String gds_token;
    @SerializedName("bimeh_token")
    @Expose
    private String bimeh_token;

    public String getGds_token()
    {
        return gds_token;
    }

    public void setGds_token(String gds_token)
    {
        this.gds_token = gds_token;
    }

    public String getBimeh_token()
    {
        return bimeh_token;
    }

    public void setBimeh_token(String bimeh_token)
    {
        this.bimeh_token = bimeh_token;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    public String getAccess()
    {
        return access;
    }

    public void setAccess(String access)
    {
        this.access = access;
    }

}
