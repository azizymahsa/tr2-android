package com.traap.traapapp.apiServices.model.techs;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSetFavoritePlayer implements Serializable
{

    @SerializedName("player_id")
    @Expose
    private Integer playerId;
    private final static long serialVersionUID = -5755480129795450313L;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

}