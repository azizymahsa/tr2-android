package com.traap.traapapp.apiServices.model.charity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.news.main.ImageName;

import lombok.Getter;
import lombok.Setter;

public class CharityModel
{
    @Expose @Getter @Setter
    @SerializedName("id")
    private int charityId;

    @Expose @Getter @Setter
    @SerializedName("subtitle")
    private String subTitle;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("image_url")
    private ImageName imageUrl;

}
