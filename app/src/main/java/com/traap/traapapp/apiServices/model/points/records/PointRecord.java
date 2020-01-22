package com.traap.traapapp.apiServices.model.points.records;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PointRecord implements Parcelable
{
    @SerializedName("id")
    @Getter @Setter
    private int id;

    @SerializedName("create_date")
    @Getter @Setter
    private String date;

    @SerializedName("title")
    @Getter @Setter
    private String title;

    @SerializedName("value")
    @Getter @Setter
    private int value;

    protected PointRecord(Parcel in)
    {
        id = in.readInt();
        date = in.readString();
        title = in.readString();
        value = in.readInt();
    }

    public static final Creator<PointRecord> CREATOR = new Creator<PointRecord>()
    {
        @Override
        public PointRecord createFromParcel(Parcel in)
        {
            return new PointRecord(in);
        }

        @Override
        public PointRecord[] newArray(int size)
        {
            return new PointRecord[size];
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
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeInt(value);
    }
}
