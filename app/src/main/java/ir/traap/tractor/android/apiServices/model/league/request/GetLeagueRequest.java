package ir.traap.tractor.android.apiServices.model.league.request;

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
