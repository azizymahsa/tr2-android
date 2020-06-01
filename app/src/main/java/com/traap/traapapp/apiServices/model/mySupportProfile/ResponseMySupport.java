
package com.traap.traapapp.apiServices.model.mySupportProfile;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMySupport implements Serializable
{

    @SerializedName("player")
    @Expose
    private Player player;
    @SerializedName("score")
    @Expose
    private Integer score;
    private final static long serialVersionUID = -4339423050232482760L;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
