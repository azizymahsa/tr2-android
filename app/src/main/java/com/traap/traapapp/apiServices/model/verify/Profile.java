package com.traap.traapapp.apiServices.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class Profile
{
    @SerializedName("key_invite")
    @Expose
    private String keyInvite;

    @SerializedName("popular_player")
    @Expose
    private Integer popularPlayer;

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
    private Object birthday;

    @SerializedName("english_name")
    @Expose
    private String englishName;

    public String getKeyInvite()
    {
        return keyInvite;
    }

    public void setKeyInvite(String keyInvite)
    {
        this.keyInvite = keyInvite;
    }

    public Integer getPopularPlayer()
    {
        return popularPlayer;
    }

    public void setPopularPlayer(Integer popularPlayer)
    {
        this.popularPlayer = popularPlayer;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getNationalCode()
    {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode)
    {
        this.nationalCode = nationalCode;
    }

    public Object getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Object birthday)
    {
        this.birthday = birthday;
    }

    public String getEnglishName()
    {
        return englishName;
    }

    public void setEnglishName(String englishName)
    {
        this.englishName = englishName;
    }
}
