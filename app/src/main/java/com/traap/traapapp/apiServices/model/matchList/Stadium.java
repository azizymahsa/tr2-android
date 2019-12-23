
package com.traap.traapapp.apiServices.model.matchList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stadium implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

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

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public Stadium()
    {
    }

    protected Stadium(Parcel in)
    {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Stadium> CREATOR = new Parcelable.Creator<Stadium>()
    {
        @Override
        public Stadium createFromParcel(Parcel source)
        {
            return new Stadium(source);
        }

        @Override
        public Stadium[] newArray(int size)
        {
            return new Stadium[size];
        }
    };
}
