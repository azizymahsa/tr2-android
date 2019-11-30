
package com.traap.traapapp.apiServices.model.matchSchedule.nextMatches;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("round")
    @Expose
    private String round;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("away_name")
    @Expose
    private String awayName;
    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("home_name")
    @Expose
    private String homeName;
    @SerializedName("competition")
    @Expose
    private Competition competition;
    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("away_logo")
    @Expose
    private String awayLogo;
    @SerializedName("home_id")
    @Expose
    private String homeId;
    @SerializedName("home_logo")
    @Expose
    private String homeLogo;
    @SerializedName("away_id")
    @Expose
    private String awayId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("league")
    @Expose
    private League league;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private String location;
    private final static long serialVersionUID = -4648335178715279058L;

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getAwayLogo() {
        return awayLogo;
    }

    public void setAwayLogo(String awayLogo) {
        this.awayLogo = awayLogo;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public void setHomeLogo(String homeLogo) {
        this.homeLogo = homeLogo;
    }

    public String getAwayId() {
        return awayId;
    }

    public void setAwayId(String awayId) {
        this.awayId = awayId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
