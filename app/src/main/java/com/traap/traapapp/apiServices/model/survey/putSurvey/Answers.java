package com.traap.traapapp.apiServices.model.survey.putSurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 5/9/2020.
 */
public class Answers
{
    public Answers(Integer questionId, Integer answerId, String answerText)
    {
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerText = answerText;
    }

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("answer_id")
    @Expose
    private Integer answerId;
    @SerializedName("answer_text")
    @Expose
    private String answerText;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
