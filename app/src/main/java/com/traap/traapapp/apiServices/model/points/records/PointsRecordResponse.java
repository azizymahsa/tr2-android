package com.traap.traapapp.apiServices.model.points.records;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PointsRecordResponse
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

    @SerializedName("balance")
    @Getter @Setter
    private String balance;

    @SerializedName("results")
    @Getter @Setter
    private List<PointRecord> recordList;
}
