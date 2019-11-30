package com.traap.traapapp.apiServices.model.news.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ImageName
{
    @Expose @Getter @Setter
    @SerializedName("thumbnail_large")
    private String thumbnailLarge;

    @Expose @Getter @Setter
    @SerializedName("thumbnail_small")
    private String thumbnailSmall;
}
