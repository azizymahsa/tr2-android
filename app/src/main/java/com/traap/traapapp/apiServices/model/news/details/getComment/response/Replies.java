package com.traap.traapapp.apiServices.model.news.details.getComment.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Replies
{
    @Expose @Getter @Setter
    @SerializedName("parent")
    private int parent;

    @Expose @Getter @Setter
    @SerializedName("rated")
    private int rated;

    @Expose @Getter @Setter
    @SerializedName("like_count")
    private int likeCount;

    @Expose @Getter @Setter
    @SerializedName("dislike_count")
    private int dislikeCount;

    @Expose @Getter @Setter
    @SerializedName("body")
    private String body;

    @Expose @Getter @Setter
    @SerializedName("user")
    private User user;

    @Expose @Getter @Setter
    @SerializedName("create_date")
    private String createDate;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;
}