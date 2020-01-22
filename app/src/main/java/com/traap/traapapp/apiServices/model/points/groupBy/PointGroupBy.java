package com.traap.traapapp.apiServices.model.points.groupBy;

import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PointGroupBy
{
    @SerializedName("title")
    @Getter @Setter
    private String title;

    @SerializedName("value_sum")
    @Getter @Setter
    private int valueSum;

    @SerializedName("count")
    @Getter @Setter
    private int count;

    @SerializedName("content")
    @Getter @Setter
    private List<PointRecord> pointChildList;
}
