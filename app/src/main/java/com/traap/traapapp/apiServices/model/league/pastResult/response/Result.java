
package com.traap.traapapp.apiServices.model.league.pastResult.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("home_id")
    @Expose
    private String homeId;
    @SerializedName("away_logo")
    @Expose
    private String awayLogo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ht_score")
    @Expose
    private String htScore;
    @SerializedName("league_id")
    @Expose
    private String leagueId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_formatted")
    @Expose
    private String dateFormatted;
    @SerializedName("last_changed")
    @Expose
    private String lastChanged;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("competition_name")
    @Expose
    private String competitionName;
    @SerializedName("away_id")
    @Expose
    private String awayId;
    @SerializedName("home_logo")
    @Expose
    private String homeLogo;
    @SerializedName("ft_score")
    @Expose
    private String ftScore;
    @SerializedName("scheduled")
    @Expose
    private String scheduled;
    @SerializedName("competition_id")
    @Expose
    private String competitionId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("away_name")
    @Expose
    private String awayName;
    @SerializedName("home_name")
    @Expose
    private String homeName;
    @SerializedName("fixture_id")
    @Expose
    private String fixtureId;
    @SerializedName("outcomes")
    @Expose
    private Outcomes outcomes;
    @SerializedName("et_score")
    @Expose
    private String etScore;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("score")
    @Expose
    private String score;

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getAwayLogo() {
        return awayLogo;
    }

    public void setAwayLogo(String awayLogo) {
        this.awayLogo = awayLogo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHtScore() {
        return htScore;
    }

    public void setHtScore(String htScore) {
        this.htScore = htScore;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public String getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(String lastChanged) {
        this.lastChanged = lastChanged;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getAwayId() {
        return awayId;
    }

    public void setAwayId(String awayId) {
        this.awayId = awayId;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public void setHomeLogo(String homeLogo) {
        this.homeLogo = homeLogo;
    }

    public String getFtScore() {
        return ftScore;
    }

    public void setFtScore(String ftScore) {
        this.ftScore = ftScore;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
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

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(String fixtureId) {
        this.fixtureId = fixtureId;
    }

    public Outcomes getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Outcomes outcomes) {
        this.outcomes = outcomes;
    }

    public String getEtScore() {
        return etScore;
    }

    public void setEtScore(String etScore) {
        this.etScore = etScore;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
