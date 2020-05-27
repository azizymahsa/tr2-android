package com.traap.traapapp.apiServices.model.survey.listSurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("start_date")
@Expose
private String startDate;
@SerializedName("end_date")
@Expose
private String endDate;
@SerializedName("is_active")
@Expose
private Boolean isActive;
@SerializedName("question_count")
@Expose
private Integer questionCount;
@SerializedName("title")
@Expose
private String title;

    @SerializedName("id")
    @Expose
    private Integer id;

public String getStartDate() {
return startDate;
}

public void setStartDate(String startDate) {
this.startDate = startDate;
}

public String getEndDate() {
return endDate;
}

public void setEndDate(String endDate) {
this.endDate = endDate;
}

public Boolean getIsActive() {
return isActive;
}

public void setIsActive(Boolean isActive) {
this.isActive = isActive;
}

public Integer getQuestionCount() {
return questionCount;
}

public void setQuestionCount(Integer questionCount) {
this.questionCount = questionCount;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}