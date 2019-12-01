package com.traap.traapapp.apiServices.model.news.details.sendLike.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LikeNewsDetailResponse
{
    @SerializedName("liked")
    @Expose @Getter @Setter
    private Boolean isLiked;

    @SerializedName("likes")
    @Expose @Getter @Setter
    private Integer likeCount;

}
