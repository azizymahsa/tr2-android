package com.traap.traapapp.apiServices.model.shetacForgotPass2.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ShetacForgotPass2Request
{
    @SerializedName("card_id")
    @Expose @Setter @Getter
    private Integer cardId;

    @SerializedName("mobile")
    @Expose @Setter @Getter
    private String mobile;
}
