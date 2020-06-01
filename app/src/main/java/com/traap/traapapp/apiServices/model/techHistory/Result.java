package com.traap.traapapp.apiServices.model.techHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("seasons")
@Expose
private Integer seasons;
@SerializedName("national_goals")
@Expose
private Integer nationalGoals;
@SerializedName("club_goals")
@Expose
private Integer clubGoals;
@SerializedName("persian_last_name")
@Expose
private String persianLastName;
@SerializedName("english_last_name")
@Expose
private String englishLastName;
@SerializedName("persian_first_name")
@Expose
private String persianFirstName;
@SerializedName("english_first_name")
@Expose
private String englishFirstName;
@SerializedName("nationality")
@Expose
private String nationality;
@SerializedName("role")
@Expose
private String role;
@SerializedName("birthday")
@Expose
private String birthday;
@SerializedName("feet")
@Expose
private Integer feet;
@SerializedName("image")
@Expose
private String image;
@SerializedName("from_date")
@Expose
private String fromDate;
@SerializedName("to_date")
@Expose
private String toDate;
    @SerializedName("team")
    @Expose
    private String team;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getSeasons() {
return seasons;
}

public void setSeasons(Integer seasons) {
this.seasons = seasons;
}

public Integer getNationalGoals() {
return nationalGoals;
}

public void setNationalGoals(Integer nationalGoals) {
this.nationalGoals = nationalGoals;
}

public Integer getClubGoals() {
return clubGoals;
}

public void setClubGoals(Integer clubGoals) {
this.clubGoals = clubGoals;
}

public String getPersianLastName() {
return persianLastName;
}

public void setPersianLastName(String persianLastName) {
this.persianLastName = persianLastName;
}

public String getEnglishLastName() {
return englishLastName;
}

public void setEnglishLastName(String englishLastName) {
this.englishLastName = englishLastName;
}

public String getPersianFirstName() {
return persianFirstName;
}

public void setPersianFirstName(String persianFirstName) {
this.persianFirstName = persianFirstName;
}

public String getEnglishFirstName() {
return englishFirstName;
}

public void setEnglishFirstName(String englishFirstName) {
this.englishFirstName = englishFirstName;
}

public String getNationality() {
return nationality;
}

public void setNationality(String nationality) {
this.nationality = nationality;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public String getBirthday() {
return birthday;
}

public void setBirthday(String birthday) {
this.birthday = birthday;
}

public Integer getFeet() {
return feet;
}

public void setFeet(Integer feet) {
this.feet = feet;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getFromDate() {
return fromDate;
}

public void setFromDate(String fromDate) {
this.fromDate = fromDate;
}

public String getToDate() {
return toDate;
}

public void setToDate(String toDate) {
this.toDate = toDate;
}

    public String getTeam()
    {
        return team;
    }

    public void setTeam(String team)
    {
        this.team = team;
    }
}