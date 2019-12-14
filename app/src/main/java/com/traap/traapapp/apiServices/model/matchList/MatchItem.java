package com.traap.traapapp.apiServices.model.matchList;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MatchItem implements Parcelable
{
    @SerializedName("cup")
    @Expose @Getter @Setter
    private Cup cup;

    @SerializedName("team_home")
    @Expose @Getter @Setter
    private TeamHome teamHome;

    @SerializedName("team_away")
    @Expose @Getter @Setter
    private TeamAway teamAway;

    @SerializedName("stadium")
    @Expose @Getter @Setter
    private Stadium stadium;

    @SerializedName("match_datetime")
    @Expose @Getter @Setter
    private Double matchDatetime;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("predict_time")
    @Expose @Getter @Setter
    private Double predictTime;

    @SerializedName("buy_enable")
    @Expose @Getter @Setter
    private Boolean buyEnable;

    @SerializedName("is_current")
    @Expose @Getter @Setter
    private Boolean isCurrent;

    @SerializedName("is_predict")
    @Expose @Getter @Setter
    private Boolean isPredict;

    @SerializedName("is_chart_predict")
    @Expose @Getter @Setter
    private Boolean is_chart_predict;

    @SerializedName("description")
    @Expose @Getter @Setter
    private String description;

    @SerializedName("result")
    @Expose @Getter @Setter
    @Nullable
    private String result = null;

    @SerializedName("referee")
    @Expose @Getter @Setter
    @Nullable
    private String referee = null;

    @SerializedName("assistant_referee")
    @Expose @Getter @Setter
    @Nullable
    private String assistant_referee = null;

    public MatchItem()
    {
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        if (matchDatetime == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(matchDatetime);
        }
        dest.writeString(matchDatetimeStr);
        if (id == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeByte((byte) (buyEnable == null ? 0 : buyEnable ? 1 : 2));
        dest.writeByte((byte) (isCurrent == null ? 0 : isCurrent ? 1 : 2));
        dest.writeByte((byte) (isPredict == null ? 0 : isPredict ? 1 : 2));
        dest.writeString(description);
        dest.writeString(result);
        dest.writeString(referee);
        dest.writeString(assistant_referee);
        /////////
        dest.writeValue(this.is_chart_predict);

    }

    protected MatchItem(Parcel in)
    {
        this.cup = in.readParcelable(Cup.class.getClassLoader());
        this.teamHome = in.readParcelable(TeamHome.class.getClassLoader());
        this.teamAway = in.readParcelable(TeamAway.class.getClassLoader());
        this.stadium = in.readParcelable(Stadium.class.getClassLoader());
        this.matchDatetime = (Double) in.readValue(Double.class.getClassLoader());
        this.matchDatetimeStr = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.predictTime = (Double) in.readValue(Double.class.getClassLoader());
        this.buyEnable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isCurrent = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isPredict = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.is_chart_predict = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.description = in.readString();
        this.result = in.readString();
        this.referee = in.readString();
        this.assistant_referee = in.readString();
    }

    public static final Creator<MatchItem> CREATOR = new Creator<MatchItem>()
    {
        @Override
        public MatchItem createFromParcel(Parcel source)
        {
            return new MatchItem(source);
        }

        @Override
        public MatchItem[] newArray(int size)
        {
            return new MatchItem[size];
        }
    };
}
