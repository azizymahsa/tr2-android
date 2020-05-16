package com.traap.traapapp.apiServices.model.survey.putSurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 5/9/2020.
 */
public class Answers
{
    public Answers(String questionId, String answerId, String answerText)
    {
        this.questionId = questionId;
        this.answerId = answerId;
        this.answerText = answerText;
    }

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("answer_id")
    @Expose
    private String answerId;
    @SerializedName("answer_text")
    @Expose
    private String answerText;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
