package com.traap.traapapp.apiServices.model.event.getEventByid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetEventByIdResponse implements Serializable
{


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("create_date")
    @Expose
    private Double createDate;
    @SerializedName("update_date")
    @Expose
    private Double updateDate;
    @SerializedName("event_start_date")
    @Expose
    private Double eventStartDate;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 3883978106355002032L;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Double getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Double createDate)
    {
        this.createDate = createDate;
    }

    public Double getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Double updateDate)
    {
        this.updateDate = updateDate;
    }

    public Double getEventStartDate()
    {
        return eventStartDate;
    }

    public void setEventStartDate(Double eventStartDate)
    {
        this.eventStartDate = eventStartDate;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}