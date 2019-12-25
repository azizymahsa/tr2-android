package com.traap.traapapp.apiServices.model.mainPhotos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCategory implements Parcelable
{

@SerializedName("title")
@Expose
private String title;
@SerializedName("id")
@Expose
private Integer id;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.title);
        dest.writeValue(this.id);
    }

    public ListCategory()
    {
    }

    protected ListCategory(Parcel in)
    {
        this.title = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ListCategory> CREATOR = new Creator<ListCategory>()
    {
        @Override
        public ListCategory createFromParcel(Parcel source)
        {
            return new ListCategory(source);
        }

        @Override
        public ListCategory[] newArray(int size)
        {
            return new ListCategory[size];
        }
    };
}