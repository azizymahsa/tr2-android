package ir.trap.tractor.android.apiServices.model.predict.sendPredict.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendPredictRequest
{
    @SerializedName("match_id")
    @Expose@Getter @Setter
    private Integer matchId;

    @SerializedName("home_team_score")
    @Expose @Getter @Setter
    private Integer homeTeamDcore;

    @SerializedName("away_team_score")
    @Expose @Getter @Setter
    private Integer awayTeamDcore;

}
