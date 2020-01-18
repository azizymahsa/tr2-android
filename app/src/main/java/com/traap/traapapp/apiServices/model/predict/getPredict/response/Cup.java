package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Cup
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer cupId;

    @SerializedName("logo")
    @Expose @Getter @Setter
    private String logo;

    @SerializedName("name")
    @Expose @Getter @Setter
    private String name;

    @SerializedName("description")
    @Expose @Getter @Setter
    private String description;

}
