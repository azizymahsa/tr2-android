package com.traap.traapapp.apiServices.model.survey.putSurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 5/9/2020.
 */
public class PutSurveyRequest
{

    @SerializedName("answers")
    @Expose
    private List<Answers> answers = null;

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }


}
