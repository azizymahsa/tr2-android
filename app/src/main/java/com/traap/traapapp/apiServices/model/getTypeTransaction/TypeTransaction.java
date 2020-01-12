package com.traap.traapapp.apiServices.model.getTypeTransaction;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TypeTransaction implements Parcelable
{
    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    public TypeTransaction()
    {
    }

    protected TypeTransaction(Parcel in)
    {
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<TypeTransaction> CREATOR = new Creator<TypeTransaction>()
    {
        @Override
        public TypeTransaction createFromParcel(Parcel in)
        {
            return new TypeTransaction(in);
        }

        @Override
        public TypeTransaction[] newArray(int size)
        {
            return new TypeTransaction[size];
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
