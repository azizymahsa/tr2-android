package com.traap.traapapp.apiServices.model.points.groupBy;

import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.points.guide.PointGuide;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PointsGroupByResponse
{
    @SerializedName("results")
    @Getter @Setter
    private List<PointGroupBy> groupByList;
}
