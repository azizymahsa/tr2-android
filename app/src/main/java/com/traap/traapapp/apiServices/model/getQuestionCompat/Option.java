
package com.traap.traapapp.apiServices.model.getQuestionCompat;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option implements Serializable
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
    private Boolean isCheck;

    public Boolean getCheck()
    {
        return isCheck;
    }

    public void setCheck(Boolean check)
    {
        isCheck = check;
    }

    private final static long serialVersionUID = -3510123081747400093L;

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


}
