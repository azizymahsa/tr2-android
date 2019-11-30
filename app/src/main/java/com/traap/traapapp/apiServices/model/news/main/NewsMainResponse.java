package com.traap.traapapp.apiServices.model.news.main;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class NewsMainResponse implements Parcelable
{

    @Expose @Getter @Setter
    @SerializedName("categories")
    private ArrayList<Categories> categories;

    @Expose @Getter @Setter
    @SerializedName("latest_news")
    private List<News> latestNews;

    @Expose @Getter @Setter
    @SerializedName("favorite_news")
    private List<News> favoriteNews;

    public NewsMainResponse()
    {
    }

    protected NewsMainResponse(Parcel in)
    {
    }

    public static final Creator<NewsMainResponse> CREATOR = new Creator<NewsMainResponse>()
    {
        @Override
        public NewsMainResponse createFromParcel(Parcel in)
        {
            return new NewsMainResponse(in);
        }

        @Override
        public NewsMainResponse[] newArray(int size)
        {
            return new NewsMainResponse[size];
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
    }
}
