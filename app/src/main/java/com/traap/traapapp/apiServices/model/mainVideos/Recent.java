package com.traap.traapapp.apiServices.model.mainVideos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recent
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
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("is_bookmarked")
    @Expose
    private Boolean isBookmarked;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("is_liked")
    @Expose
    private Boolean isLiked;

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

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getBigPoster()
    {
        return bigPoster;
    }

    public void setBigPoster(String bigPoster)
    {
        this.bigPoster = bigPoster;
    }

    public String getSmallPoster()
    {
        return smallPoster;
    }

    public void setSmallPoster(String smallPoster)
    {
        this.smallPoster = smallPoster;
    }

    public String getFrame()
    {
        return frame;
    }

    public void setFrame(String frame)
    {
        this.frame = frame;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getCreateDateFormatted()
    {
        return createDateFormatted;
    }

    public void setCreateDateFormatted(String createDateFormatted)
    {
        this.createDateFormatted = createDateFormatted;
    }

    public String getUpdateDateFormatted()
    {
        return updateDateFormatted;
    }

    public void setUpdateDateFormatted(String updateDateFormatted)
    {
        this.updateDateFormatted = updateDateFormatted;
    }

    public Boolean getIsBookmarked()
    {
        return isBookmarked;
    }

    public void setIsBookmarked(Boolean isBookmarked)
    {
        this.isBookmarked = isBookmarked;
    }

    public Integer getLikes()
    {
        return likes;
    }

    public void setLikes(Integer likes)
    {
        this.likes = likes;
    }

    public Boolean getIsLiked()
    {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked)
    {
        this.isLiked = isLiked;
    }

}