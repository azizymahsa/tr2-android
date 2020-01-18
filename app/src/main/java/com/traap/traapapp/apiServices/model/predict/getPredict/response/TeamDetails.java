package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TeamDetails
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer teamId;

    @SerializedName("logo")
    @Expose @Getter @Setter
    private String logo;

    @SerializedName("name")
    @Expose @Getter @Setter
    private String name;

    @SerializedName("color_code")
    @Expose @Getter @Setter
    private String colorCode;

    @SerializedName("livescore_id")
    @Expose @Getter @Setter
    private Integer liveScoreId;

}
