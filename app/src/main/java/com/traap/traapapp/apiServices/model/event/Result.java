
package com.traap.traapapp.apiServices.model.event;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
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
    private Integer eventStartDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 6769135451735891552L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Double createDate) {
        this.createDate = createDate;
    }

    public Double getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Double updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Integer eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
