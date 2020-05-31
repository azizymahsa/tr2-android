
package com.traap.traapapp.apiServices.model.getAllCompations;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("create_datetime")
    @Expose
    private Double createDatetime;
    @SerializedName("update_datetime")
    @Expose
    private Integer updateDatetime;
    @SerializedName("image")
    @Expose
    private String image;
    private final static long serialVersionUID = 4090008811791859944L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Double createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Integer updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
