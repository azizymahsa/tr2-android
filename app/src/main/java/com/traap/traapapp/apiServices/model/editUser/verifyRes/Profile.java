package com.traap.traapapp.apiServices.model.editUser.verifyRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("popular_player")
    @Expose
    private Integer popularPlayer;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("national_code")
    @Expose
    private String nationalCode;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("english_name")
    @Expose
    private String englishName;
    @SerializedName("key_invite")
    @Expose
    private String keyInvite;
    @SerializedName("sex")
    @Expose
    private Integer sex;
    @SerializedName("first_english_name")
    @Expose
    private String firstEnglishName;
    @SerializedName("last_english_name")
    @Expose
    private String lastEnglishName;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPopularPlayer() {
        return popularPlayer;
    }

    public void setPopularPlayer(Integer popularPlayer) {
        this.popularPlayer = popularPlayer;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getKeyInvite() {
        return keyInvite;
    }

    public void setKeyInvite(String keyInvite) {
        this.keyInvite = keyInvite;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getFirstEnglishName() {
        return firstEnglishName;
    }

    public void setFirstEnglishName(String firstEnglishName) {
        this.firstEnglishName = firstEnglishName;
    }

    public String getLastEnglishName() {
        return lastEnglishName;
    }

    public void setLastEnglishName(String lastEnglishName) {
        this.lastEnglishName = lastEnglishName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}