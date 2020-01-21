package com.traap.traapapp.apiServices.model.points.guide;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PointsGuideResponse
{
    @SerializedName("count")
    @Getter @Setter
    private int count;

    @SerializedName("next")
    @Getter @Setter
    private String next;

    @SerializedName("previous")
    @Getter @Setter
    private String previous;

    @SerializedName("results")
    @Getter @Setter
    private List<PointGuide> guideList;
}
