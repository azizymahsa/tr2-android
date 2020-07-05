package com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SendPredictSystemPlayersRequest
{
    @SerializedName("formation_id")
    @Expose @Getter @Setter
    private int formationId;

    @SerializedName("players")
    @Expose @Getter @Setter
    private List<PlayerPositioning> playerPositioning;
}
