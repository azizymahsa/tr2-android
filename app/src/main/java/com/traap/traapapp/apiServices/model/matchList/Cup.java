package com.traap.traapapp.apiServices.model.matchList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/31/2019.
 */
public class Cup implements Parcelable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeString(this.imageName);
        dest.writeValue(this.id);
        dest.writeString(this.description);
    }

    public Cup()
    {
    }

    protected Cup(Parcel in)
    {
        this.name = in.readString();
        this.imageName = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Cup> CREATOR = new Parcelable.Creator<Cup>()
    {
        @Override
        public Cup createFromParcel(Parcel source)
        {
            return new Cup(source);
        }

        @Override
        public Cup[] newArray(int size)
        {
            return new Cup[size];
        }
    };
}
