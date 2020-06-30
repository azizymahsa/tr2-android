package com.traap.traapapp.apiServices.model.getLast5PastMatch.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict.Score;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.Cup;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.TeamDetails;

import lombok.Getter;
import lombok.Setter;

public class Last5PastMatchItem
{
    @SerializedName("result")
    @Expose @Getter @Setter
    private Score result;

    @SerializedName("stadium")
    @Expose @Getter @Setter
    private String stadium;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String dateStr;

    @SerializedName("home_team")
    @Expose @Getter @Setter
    private TeamDetails homeTeam;

    @SerializedName("away_team")
    @Expose @Getter @Setter
    private TeamDetails awayTeam;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private Cup cup;

    @SerializedName("status")
    @Expose @Getter @Setter
    private String status;

//    @Getter
//    private MatchStatus matchStatus = getMatchStatusBackground();
//
//    private MatchStatus getMatchStatusBackground()
//    {
//        if (status.equals("-1"))
//        {
//            return MatchStatus.Loose;
//        }
//        else if (status.equals("0"))
//        {
//            return MatchStatus.Equal;
//        }
//        else if (status.equals("1"))
//        {
//            return MatchStatus.Win;
//        }
//        return MatchStatus.Equal;
//    }
//
//    public enum MatchStatus
//    {
//        Win, Equal, Loose
//    }

}
