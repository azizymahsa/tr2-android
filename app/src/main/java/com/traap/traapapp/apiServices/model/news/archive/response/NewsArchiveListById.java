package com.traap.traapapp.apiServices.model.news.archive.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class NewsArchiveListById
{
    @Expose @Getter @Setter
    @SerializedName("dislikes")
    private int dislikes;

    @Expose @Getter @Setter
    @SerializedName("likes")

    private int likes;
    @Expose @Getter @Setter
    @SerializedName("create_date")
    private String createDate;

    @Expose @Getter @Setter
    @SerializedName("subtitle")
    private String subtitle;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("category")
    private String category;

    @Expose @Getter @Setter
    @SerializedName("image_name")
    private ImageName imageName;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    public static class ImageName
    {
        @Expose @Getter @Setter
        @SerializedName("thumbnail_large")
        private String thumbnailLarge;

        @Expose @Getter @Setter
        @SerializedName("thumbnail_small")
        private String thumbnailSmall;
    }
}
