
package com.traap.traapapp.apiServices.model.setAnswerQuestions;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Serializable
{

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("answer_id")
    @Expose
    private String answerId;
    private final static long serialVersionUID = 1905507335733346887L;

    public Question(String questionId, String answerId)
    {
        this.questionId = questionId;
        this.answerId = answerId;
    }


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

}
