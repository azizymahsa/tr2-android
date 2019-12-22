
package com.traap.traapapp.apiServices.model.matchList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TeamAway implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("color_code")
    @Expose
    private String colorCode;

    @SerializedName("livescore_id")
    @Expose @Getter @Setter
    private Integer livescoreId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.name);
        dest.writeString(this.logo);
        dest.writeValue(this.id);
        dest.writeString(this.colorCode);
        dest.writeValue(this.livescoreId);
    }

    public TeamAway()
    {
    }

    protected TeamAway(Parcel in)
    {
        this.name = in.readString();
        this.logo = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.colorCode = in.readString();
        this.livescoreId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TeamAway> CREATOR = new Parcelable.Creator<TeamAway>()
    {
        @Override
        public TeamAway createFromParcel(Parcel source)
        {
            return new TeamAway(source);
        }

        @Override
        public TeamAway[] newArray(int size)
        {
            return new TeamAway[size];
        }
    };
}
