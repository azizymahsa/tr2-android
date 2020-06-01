package com.traap.traapapp.apiServices.model.techs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 5/27/2020.
 */
public class GetTechsIdResponse
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("joined_date")
    @Expose
    private String joinedDate;
    @SerializedName("leaved_date")
    @Expose
    private String leavedDate;
    @SerializedName("feet")
    @Expose
    private Integer feet;
    @SerializedName("persian_first_name")
    @Expose
    private String persianFirstName;
    @SerializedName("persian_last_name")
    @Expose
    private String persianLastName;
    @SerializedName("english_first_name")
    @Expose
    private String englishFirstName;
    @SerializedName("english_last_name")
    @Expose
    private String englishLastName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("club_goals")
    @Expose
    private Integer clubGoals;
    @SerializedName("national_goals")
    @Expose
    private Integer nationalGoals;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("wiki")
    @Expose
    private String wiki;
    @SerializedName("description")
    @Expose
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getLeavedDate() {
        return leavedDate;
    }

    public void setLeavedDate(String leavedDate) {
        this.leavedDate = leavedDate;
    }

    public Integer getFeet() {
        return feet;
    }

    public void setFeet(Integer feet) {
        this.feet = feet;
    }

    public String getPersianFirstName() {
        return persianFirstName;
    }

    public void setPersianFirstName(String persianFirstName) {
        this.persianFirstName = persianFirstName;
    }

    public String getPersianLastName() {
        return persianLastName;
    }

    public void setPersianLastName(String persianLastName) {
        this.persianLastName = persianLastName;
    }

    public String getEnglishFirstName() {
        return englishFirstName;
    }

    public void setEnglishFirstName(String englishFirstName) {
        this.englishFirstName = englishFirstName;
    }

    public String getEnglishLastName() {
        return englishLastName;
    }

    public void setEnglishLastName(String englishLastName) {
        this.englishLastName = englishLastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getClubGoals() {
        return clubGoals;
    }

    public void setClubGoals(Integer clubGoals) {
        this.clubGoals = clubGoals;
    }

    public Integer getNationalGoals() {
        return nationalGoals;
    }

    public void setNationalGoals(Integer nationalGoals) {
        this.nationalGoals = nationalGoals;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public String getWiki()
    {
        return wiki;
    }

    public void setWiki(String wiki)
    {
        this.wiki = wiki;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
