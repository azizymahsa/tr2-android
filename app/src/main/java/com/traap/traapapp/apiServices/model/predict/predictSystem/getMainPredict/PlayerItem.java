package com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class PlayerItem
{
    @SerializedName("player_id")
    @Expose @Getter @Setter
    private int playerId;

    @SerializedName("player_image")
    @Expose @Getter @Setter
    private String image;

    @SerializedName("player_name")
    @Expose @Getter @Setter
    private String fullName;
}
