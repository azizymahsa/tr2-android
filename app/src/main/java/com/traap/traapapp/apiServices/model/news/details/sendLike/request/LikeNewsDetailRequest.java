package com.traap.traapapp.apiServices.model.news.details.sendLike.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LikeNewsDetailRequest
{
    @SerializedName("value")
    @Expose @Getter @Setter
    private Boolean like;
}
