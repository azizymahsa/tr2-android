package com.traap.traapapp.apiServices.model.news.details.getContent.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.traap.traapapp.apiServices.model.news.main.ImageName;
import lombok.Getter;
import lombok.Setter;

public class GetNewsDetailsResponse implements Parcelable
{
    @Expose @Getter @Setter
    @SerializedName("source")
    private String source;

    @Expose @Getter @Setter
    @SerializedName("body")
    private String body;

    @Expose @Getter @Setter
    @SerializedName("subtitle")
    private String subtitle;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("images")
    private List<ImageName> images;

    @Expose @Getter @Setter
    @SerializedName("related_news")
    private List<RelatedNews> relatedNews;

    @Expose @Getter @Setter
    @SerializedName("likes_count")
    private Integer likeCounter;

    @Expose @Getter @Setter
    @SerializedName("liked")
    private Boolean liked;

    @Expose @Getter @Setter
    @SerializedName("bookmarked")
    private Boolean bookmarked;

    @Expose @Getter @Setter
    @SerializedName("update_date")
    private String updateDate;

//    @Expose @Getter @Setter
//    @SerializedName("create_date")
//    private String createDate;

    @Expose @Getter @Setter
    @SerializedName("publish_date")
    private Integer publishDate;

    @Expose @Getter @Setter
    @SerializedName("publish_date_formatted")
    private String publishDateStr;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    protected GetNewsDetailsResponse(Parcel in)
    {
        source = in.readString();
        body = in.readString();
        subtitle = in.readString();
        title = in.readString();
        relatedNews = in.createTypedArrayList(RelatedNews.CREATOR);
        if (in.readByte() == 0)
        {
            likeCounter = null;
        } else
        {
            likeCounter = in.readInt();
        }
        byte tmpLiked = in.readByte();
        liked = tmpLiked == 0 ? null : tmpLiked == 1;
        byte tmpBookmarked = in.readByte();
        bookmarked = tmpBookmarked == 0 ? null : tmpBookmarked == 1;
        updateDate = in.readString();
//        createDate = in.readString();
        if (in.readByte() == 0)
        {
            publishDate = null;
        } else
        {
            publishDate = in.readInt();
        }
        publishDateStr = in.readString();
        id = in.readInt();
    }

    public static final Creator<GetNewsDetailsResponse> CREATOR = new Creator<GetNewsDetailsResponse>()
    {
        @Override
        public GetNewsDetailsResponse createFromParcel(Parcel in)
        {
            return new GetNewsDetailsResponse(in);
        }

        @Override
        public GetNewsDetailsResponse[] newArray(int size)
        {
            return new GetNewsDetailsResponse[size];
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
        dest.writeString(source);
        dest.writeString(body);
        dest.writeString(subtitle);
        dest.writeString(title);
        dest.writeTypedList(relatedNews);
        if (likeCounter == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(likeCounter);
        }
        dest.writeByte((byte) (liked == null ? 0 : liked ? 1 : 2));
        dest.writeByte((byte) (bookmarked == null ? 0 : bookmarked ? 1 : 2));
        dest.writeString(updateDate);
//        dest.writeString(createDate);
        if (publishDate == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(publishDate);
        }
        dest.writeString(publishDateStr);
        dest.writeInt(id);
    }
}
