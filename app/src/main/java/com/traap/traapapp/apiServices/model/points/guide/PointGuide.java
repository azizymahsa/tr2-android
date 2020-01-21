package com.traap.traapapp.apiServices.model.points.guide;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PointGuide
{
    @SerializedName("title")
    @Getter @Setter
    private String title;

    @SerializedName("value")
    @Getter @Setter
    private int value;

    @SerializedName("order")
    @Getter @Setter
    private int order;

}
