
package com.traap.traapapp.apiServices.model.setAnswerQuestions;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSetAnswer implements Serializable
{

    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    private final static long serialVersionUID = -7339914175809632286L;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
