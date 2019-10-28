package ir.trap.tractor.android.apiServices.model.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result
{
    @SerializedName("cup")
    @Expose
    private String cup;
    @SerializedName("team_home")
    @Expose
    private TeamHome teamHome;
    @SerializedName("team_away")
    @Expose
    private TeamAway teamAway;
    @SerializedName("stadium")
    @Expose
    private Stadium stadium;
    @SerializedName("match_datetime")
    @Expose
    private Double matchDatetime;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public TeamHome getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(TeamHome teamHome) {
        this.teamHome = teamHome;
    }

    public TeamAway getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(TeamAway teamAway) {
        this.teamAway = teamAway;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Double getMatchDatetime() {
        return matchDatetime;
    }

    public void setMatchDatetime(Double matchDatetime) {
        this.matchDatetime = matchDatetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
