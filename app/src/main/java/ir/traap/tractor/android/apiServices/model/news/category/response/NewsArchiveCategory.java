package ir.traap.tractor.android.apiServices.model.news.category.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class NewsArchiveCategory implements Parcelable
{
    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    protected NewsArchiveCategory(Parcel in)
    {
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<NewsArchiveCategory> CREATOR = new Creator<NewsArchiveCategory>()
    {
        @Override
        public NewsArchiveCategory createFromParcel(Parcel in)
        {
            return new NewsArchiveCategory(in);
        }

        @Override
        public NewsArchiveCategory[] newArray(int size)
        {
            return new NewsArchiveCategory[size];
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
