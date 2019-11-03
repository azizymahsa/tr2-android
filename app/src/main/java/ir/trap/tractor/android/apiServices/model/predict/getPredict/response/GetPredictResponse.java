package ir.trap.tractor.android.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetPredictResponse
{
    @SerializedName("you_predict")
    @Expose @Getter @Setter
    private Boolean youPredict;

    @SerializedName("home_team_logo")
    @Expose @Getter @Setter
    private String homeTeamLogo;

    @SerializedName("away_team_name")
    @Expose @Getter @Setter
    private String awayTeamName;

    @SerializedName("chart")
    @Expose @Getter @Setter
    private List<Chart> chart = null;

    @SerializedName("match_datetime")
    @Expose @Getter @Setter
    private Integer matchDatetime;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("predict")
    @Expose @Getter @Setter
    private List<Predict> predict = null;

    @SerializedName("home_last_plays")
    @Expose @Getter @Setter
    private List<String> homeLastPlays = null;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private String cup;

    @SerializedName("home_score")
    @Expose @Getter @Setter
    private Integer homeScore;

    @SerializedName("away_last_plays")
    @Expose @Getter @Setter
    private List<String> awayLastPlays = null;

    @SerializedName("home_team_color_code")
    @Expose @Getter @Setter
    private String homeTeamColorCode;

    @SerializedName("away_score")
    @Expose @Getter @Setter
    private Integer awayScore;

    @SerializedName("away_team_logo")
    @Expose @Getter @Setter
    private String awayTeamLogo;

    @SerializedName("away_team_color_code")
    @Expose @Getter @Setter
    private String awayTeamColorCode;

    @SerializedName("home_team_name")
    @Expose @Getter @Setter
    private String homeTeamName;
}
