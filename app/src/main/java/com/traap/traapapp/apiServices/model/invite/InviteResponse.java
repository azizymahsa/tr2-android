package com.traap.traapapp.apiServices.model.invite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.league.pastResult.response.Result;

import java.util.List;

public class InviteResponse
{
    @SerializedName("invite_text")
    @Expose
    private String invite_text ;

    public String getInvite_text()
    {
        return invite_text;
    }

    public void setInvite_text(String invite_text)
    {
        this.invite_text = invite_text;
    }
}
