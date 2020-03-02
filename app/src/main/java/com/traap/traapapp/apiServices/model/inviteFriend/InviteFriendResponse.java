package com.traap.traapapp.apiServices.model.inviteFriend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteFriendResponse
{

    @SerializedName("invite_key")
    @Expose
    private String inviteKey;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("url")
    @Expose
    private String url;

    public String getInviteKey()
    {
        return inviteKey;
    }

    public void setInviteKey(String inviteKey)
    {
        this.inviteKey = inviteKey;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
