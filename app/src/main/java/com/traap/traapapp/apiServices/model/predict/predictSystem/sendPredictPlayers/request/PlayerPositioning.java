package com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PlayerPositioning
{
    @SerializedName("player")
    @Expose @Getter @Setter
    private int playerId;

    @SerializedName("position")
    @Expose @Getter @Setter
    private int positionId;

    public PlayerPositioning(int playerId, int positionId)
    {
        this.playerId = playerId;
        this.positionId = positionId;
    }
}
