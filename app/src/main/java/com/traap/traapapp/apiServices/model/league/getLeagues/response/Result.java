package com.traap.traapapp.apiServices.model.league.getLeagues.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("goal_diff")
    @Expose
    private String goalDiff;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("won")
    @Expose
    private String won;
    @SerializedName("goals_conceded")
    @Expose
    private String goalsConceded;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("goals_scored")
    @Expose
    private String goalsScored;
    @SerializedName("matches")
    @Expose
    private String matches;
    @SerializedName("lost")
    @Expose
    private String lost;
    @SerializedName("drawn")
    @Expose
    private String drawn;
    @SerializedName("team_logo")
    @Expose
    private String teamLogo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoalDiff() {
        return goalDiff;
    }

    public void setGoalDiff(String goalDiff) {
        this.goalDiff = goalDiff;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(String goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(String goalsScored) {
        this.goalsScored = goalsScored;
    }

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getDrawn() {
        return drawn;
    }

    public void setDrawn(String drawn) {
        this.drawn = drawn;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

}
