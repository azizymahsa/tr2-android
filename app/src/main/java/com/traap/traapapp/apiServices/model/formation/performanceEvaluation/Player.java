package com.traap.traapapp.apiServices.model.formation.performanceEvaluation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Player
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("image_url")
    @Expose @Getter @Setter
    private String PlayerImage;

    @SerializedName("is_changed")
    @Expose @Getter @Setter
    private Boolean isChanged;

    @SerializedName("player_first_name")
    @Expose @Getter @Setter
    private String playerFirstName;

    @SerializedName("player_last_name")
    @Expose @Getter @Setter
    private String playerLastName;

    @SerializedName("is_evaluated")
    @Expose @Getter @Setter
    private Boolean isEvaluated;
}
