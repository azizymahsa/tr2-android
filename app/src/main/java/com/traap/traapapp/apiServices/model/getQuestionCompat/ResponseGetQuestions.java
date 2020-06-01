
package com.traap.traapapp.apiServices.model.getQuestionCompat;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetQuestions implements Serializable
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
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("create_datetime")
    @Expose
    private Double createDatetime;
    @SerializedName("update_datetime")
    @Expose
    private Integer updateDatetime;
    private final static long serialVersionUID = -1504723399048030510L;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
