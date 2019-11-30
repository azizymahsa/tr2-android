package com.traap.traapapp.apiServices.model.mainVideos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("big_poster")
    @Expose
    private String bigPoster;
    @SerializedName("small_poster")
    @Expose
    private String smallPoster;
    @SerializedName("frame")
    @Expose
    private String frame;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("create_date_formatted")
    @Expose
    private String createDateFormatted;
    @SerializedName("update_date_formatted")
    @Expose
    private String updateDateFormatted;
    @SerializedName("is_bookmarked")
    @Expose
    private Boolean isBookmarked;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("is_liked")
    @Expose
    private Boolean isLiked;
    @SerializedName("cover")
    @Expose
    private String cover;

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getTitle()
    {
        return title;
    }

public void setTitle(String title) {
this.title = title;
}

public String getCaption() {
return caption;
}

public void setCaption(String caption) {
this.caption = caption;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getBigPoster() {
return bigPoster;
}

public void setBigPoster(String bigPoster) {
this.bigPoster = bigPoster;
}

public String getSmallPoster() {
return smallPoster;
}

public void setSmallPoster(String smallPoster) {
this.smallPoster = smallPoster;
}

public String getFrame() {
return frame;
}

public void setFrame(String frame) {
this.frame = frame;
}

public Integer getCategoryId() {
return categoryId;
}

public void setCategoryId(Integer categoryId) {
this.categoryId = categoryId;
}

public String getCreateDate() {
return createDate;
}

public void setCreateDate(String createDate) {
this.createDate = createDate;
}

public String getUpdateDate() {
return updateDate;
}

public void setUpdateDate(String updateDate) {
this.updateDate = updateDate;
}

public String getCreateDateFormatted() {
return createDateFormatted;
}

public void setCreateDateFormatted(String createDateFormatted) {
this.createDateFormatted = createDateFormatted;
}

public String getUpdateDateFormatted() {
return updateDateFormatted;
}

public void setUpdateDateFormatted(String updateDateFormatted) {
this.updateDateFormatted = updateDateFormatted;
}

public Boolean getIsBookmarked() {
return isBookmarked;
}

public void setIsBookmarked(Boolean isBookmarked) {
this.isBookmarked = isBookmarked;
}

public Integer getLikes() {
return likes;
}

public void setLikes(Integer likes) {
this.likes = likes;
}

public Boolean getIsLiked() {
return isLiked;
}

public void setIsLiked(Boolean isLiked) {
this.isLiked = isLiked;
}


    public Category()
    {
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
        dest.writeString(this.caption);
        dest.writeValue(this.id);
        dest.writeString(this.bigPoster);
        dest.writeString(this.smallPoster);
        dest.writeString(this.frame);
        dest.writeValue(this.categoryId);
        dest.writeString(this.createDate);
        dest.writeString(this.updateDate);
        dest.writeString(this.createDateFormatted);
        dest.writeString(this.updateDateFormatted);
        dest.writeValue(this.isBookmarked);
        dest.writeValue(this.likes);
        dest.writeValue(this.isLiked);
        dest.writeString(this.cover);
    }

    protected Category(Parcel in)
    {
        this.title = in.readString();
        this.caption = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bigPoster = in.readString();
        this.smallPoster = in.readString();
        this.frame = in.readString();
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createDate = in.readString();
        this.updateDate = in.readString();
        this.createDateFormatted = in.readString();
        this.updateDateFormatted = in.readString();
        this.isBookmarked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.likes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isLiked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.cover = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>()
    {
        @Override
        public Category createFromParcel(Parcel source)
        {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size)
        {
            return new Category[size];
        }
    };
}