package com.traap.traapapp.models.otherModels.mediaModel;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class MediaDetailsPositionIdsModel implements Parcelable
{
    @Getter @Setter
    private Integer Id;

    @Getter @Setter
    private Integer Position;

    public MediaDetailsPositionIdsModel()
    {
    }

    protected MediaDetailsPositionIdsModel(Parcel in)
    {
        if (in.readByte() == 0)
        {
            Id = null;
        } else
        {
            Id = in.readInt();
        }
        if (in.readByte() == 0)
        {
            Position = null;
        } else
        {
            Position = in.readInt();
        }
    }

    public static final Creator<MediaDetailsPositionIdsModel> CREATOR = new Creator<MediaDetailsPositionIdsModel>()
    {
        @Override
        public MediaDetailsPositionIdsModel createFromParcel(Parcel in)
        {
            return new MediaDetailsPositionIdsModel(in);
        }

        @Override
        public MediaDetailsPositionIdsModel[] newArray(int size)
        {
            return new MediaDetailsPositionIdsModel[size];
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
        if (Id == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(Id);
        }
        if (Position == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(Position);
        }
    }
}
