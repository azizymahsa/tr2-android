package com.traap.traapapp.apiServices.model.survey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 5/9/2020.
 */
public class Option
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("answer_option")
    @Expose
    private String answerOption;
    @Expose
    private Boolean isCheck=false;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public Boolean getCheck()
    {
        return isCheck;
    }

    public void setCheck(Boolean check)
    {
        isCheck = check;
    }
}
