package com.traap.traapapp.apiServices.model.lottery;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Winner implements Parcelable
{
    @SerializedName("first_name")
    @Expose @Getter @Setter
    private String firstName;

    @SerializedName("last_name")
    @Expose @Getter @Setter
    private String lastName;

    @SerializedName("description")
    @Expose @Getter @Setter
    private String description;

    @SerializedName("mobile")
    @Expose @Getter @Setter
    private String mobile;

    public Winner()
    {
    }

    protected Winner(Parcel in)
    {
        firstName = in.readString();
        lastName = in.readString();
        description = in.readString();
        mobile = in.readString();
    }

    public static final Creator<Winner> CREATOR = new Creator<Winner>()
    {
        @Override
        public Winner createFromParcel(Parcel in)
        {
            return new Winner(in);
        }

        @Override
        public Winner[] newArray(int size)
        {
            return new Winner[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(description);
        parcel.writeString(mobile);
    }
}
