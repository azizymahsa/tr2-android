package ir.traap.tractor.android.apiServices.model.news.details.getContent.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.traap.tractor.android.apiServices.model.news.main.ImageName;
import lombok.Getter;
import lombok.Setter;

public class RelatedNews implements Parcelable
{

    @Expose @Getter @Setter
    @SerializedName("dislikes")
    private int dislikes;

    @Expose @Getter @Setter
    @SerializedName("likes")
    private int likes;

    @Expose @Getter @Setter
    @SerializedName("create_date")
    private String createDate;

    @Expose @Getter @Setter
    @SerializedName("subtitle")
    private String subtitle;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("category")
    private String category;

    @Expose @Getter @Setter
    @SerializedName("image_name")
    private ImageName imageName;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    protected RelatedNews(Parcel in)
    {
        dislikes = in.readInt();
        likes = in.readInt();
        createDate = in.readString();
        subtitle = in.readString();
        title = in.readString();
        category = in.readString();
        id = in.readInt();
    }

    public static final Creator<RelatedNews> CREATOR = new Creator<RelatedNews>()
    {
        @Override
        public RelatedNews createFromParcel(Parcel in)
        {
            return new RelatedNews(in);
        }

        @Override
        public RelatedNews[] newArray(int size)
        {
            return new RelatedNews[size];
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
        dest.writeInt(dislikes);
        dest.writeInt(likes);
        dest.writeString(createDate);
        dest.writeString(subtitle);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeInt(id);
    }
}
