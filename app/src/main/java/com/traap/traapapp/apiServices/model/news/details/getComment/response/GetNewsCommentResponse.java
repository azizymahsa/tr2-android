package com.traap.traapapp.apiServices.model.news.details.getComment.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetNewsCommentResponse
{
    @Expose @Getter @Setter
    @SerializedName("body")
    private String body;

    @Expose @Getter @Setter
    @SerializedName("rated")
    private int rated;

    @Expose @Getter @Setter
    @SerializedName("likes_count")
    private int likeCount;

    @Expose @Getter @Setter
    @SerializedName("dislikes_count")
    private int dislikeCount;

    @Expose @Getter @Setter
    @SerializedName("parent")
    private int parent;

    @Expose @Getter @Setter
    @SerializedName("user")
    private User user;

    @Expose @Getter @Setter
    @SerializedName("create_date")
    private String createDate;

    @Expose @Getter @Setter
    @SerializedName("replies")
    private List<Replies> replies;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

}
