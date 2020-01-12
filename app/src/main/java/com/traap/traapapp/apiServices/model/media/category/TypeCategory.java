package com.traap.traapapp.apiServices.model.media.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TypeCategory implements Parcelable
{
    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    protected TypeCategory(Parcel in)
    {
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<TypeCategory> CREATOR = new Creator<TypeCategory>()
    {
        @Override
        public TypeCategory createFromParcel(Parcel in)
        {
            return new TypeCategory(in);
        }

        @Override
        public TypeCategory[] newArray(int size)
        {
            return new TypeCategory[size];
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
