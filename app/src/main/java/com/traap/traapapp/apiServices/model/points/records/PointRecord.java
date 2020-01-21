package com.traap.traapapp.apiServices.model.points.records;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PointRecord
{
    @SerializedName("id")
    @Getter @Setter
    private int id;

    @SerializedName("create_date")
    @Getter @Setter
    private String date;

    @SerializedName("title")
    @Getter @Setter
    private String title;

    @SerializedName("value")
    @Getter @Setter
    private int value;

}
