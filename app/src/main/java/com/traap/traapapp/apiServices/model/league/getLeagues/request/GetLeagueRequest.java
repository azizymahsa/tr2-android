package com.traap.traapapp.apiServices.model.league.getLeagues.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetLeagueRequest
{
    @SerializedName("league")
    @Expose @Getter @Setter
    private String league;
}
