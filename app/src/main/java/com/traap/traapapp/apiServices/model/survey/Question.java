package com.traap.traapapp.apiServices.model.survey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class Question
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("question_type")
    @Expose
    private Integer questionType;
    @SerializedName("is_mandatory")
    @Expose
    private Boolean isMandatory;
    @SerializedName("options")
    @Expose
    private List<Option> options;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean isMandatory()
    {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory)
    {
        isMandatory = mandatory;
    }

    public List<Option> getOptions()
    {
        return options;
    }

    public void setOptions(List<Option> options)
    {
        this.options = options;
    }
}
