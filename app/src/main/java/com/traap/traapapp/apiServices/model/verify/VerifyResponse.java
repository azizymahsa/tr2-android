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
     @SerializedName("bimeh_api_key")
    @Expose
    private String bimeh_api_key;
    @SerializedName("bimeh_call_back")
    @Expose
    private String bimeh_call_back;

    @SerializedName("alopark_token")
    @Expose
    private String alopark_token;
    @SerializedName("bimeh_base_url")
    @Expose
    private String bimeh_base_url;

    public String getBimeh_base_url()
    {
        return bimeh_base_url;
    }

    public void setBimeh_base_url(String bimeh_base_url)
    {
        this.bimeh_base_url = bimeh_base_url;
    }

    public String getBimeh_api_key()
    {
        return bimeh_api_key;
    }

    public void setBimeh_api_key(String bimeh_api_key)
    {
        this.bimeh_api_key = bimeh_api_key;
    }

    public String getBimeh_call_back()
    {
        return bimeh_call_back;
    }

    public void setBimeh_call_back(String bimeh_call_back)
    {
        this.bimeh_call_back = bimeh_call_back;
    }

    public String getAlopark_token()
    {
        return alopark_token;
    }

    public void setAlopark_token(String alopark_token)
    {
        this.alopark_token = alopark_token;
    }

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
