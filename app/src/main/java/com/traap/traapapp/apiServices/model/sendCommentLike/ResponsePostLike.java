package com.traap.traapapp.apiServices.model.sendCommentLike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePostLike
{

    @SerializedName("user_like")
    @Expose
    private String userLike;
    private final static long serialVersionUID = -4463234559915562788L;

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

}
