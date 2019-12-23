package com.traap.traapapp.apiServices.model.media.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MediaArchiveCategory implements Parcelable
{
    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    protected MediaArchiveCategory(Parcel in)
    {
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<MediaArchiveCategory> CREATOR = new Creator<MediaArchiveCategory>()
    {
        @Override
        public MediaArchiveCategory createFromParcel(Parcel in)
        {
            return new MediaArchiveCategory(in);
        }

        @Override
        public MediaArchiveCategory[] newArray(int size)
        {
            return new MediaArchiveCategory[size];
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
        dest.writeString(title);
        dest.writeInt(id);
    }
}
