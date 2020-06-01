
package com.traap.traapapp.apiServices.model.getQuestionCompat;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("title")
    @Expose
    private String title;
    private final static long serialVersionUID = -1281107476879683647L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
