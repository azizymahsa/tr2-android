package ir.trap.tractor.android.apiServices.model.matchList;

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

    @SerializedName("buy_enable")
    @Expose @Getter @Setter
    private Boolean buyEnable;

    @SerializedName("is_current")
    @Expose @Getter @Setter
    private Boolean isCurrent;

    @SerializedName("is_predict")
    @Expose @Getter @Setter
    private Boolean isPredict;

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

    protected MatchItem(Parcel in)
    {
        if (in.readByte() == 0)
        {
            matchDatetime = null;
        } else
        {
            matchDatetime = in.readDouble();
        }
        matchDatetimeStr = in.readString();
        if (in.readByte() == 0)
        {
            id = null;
        } else
        {
            id = in.readInt();
        }
        byte tmpBuyEnable = in.readByte();
        buyEnable = tmpBuyEnable == 0 ? null : tmpBuyEnable == 1;
        byte tmpIsCurrent = in.readByte();
        isCurrent = tmpIsCurrent == 0 ? null : tmpIsCurrent == 1;
        byte tmpIsPredict = in.readByte();
        isPredict = tmpIsPredict == 0 ? null : tmpIsPredict == 1;
        description = in.readString();
        result = in.readString();
        referee = in.readString();
        assistant_referee = in.readString();
    }

    public static final Creator<MatchItem> CREATOR = new Creator<MatchItem>()
    {
        @Override
        public MatchItem createFromParcel(Parcel in)
        {
            return new MatchItem(in);
        }

        @Override
        public MatchItem[] newArray(int size)
        {
            return new MatchItem[size];
        }
    };

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
    }
}
