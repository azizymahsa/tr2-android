package com.traap.traapapp.apiServices.model.sendComment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePostComment
{
    @SerializedName("comment_id")
    @Expose
    private Integer commentId;
    private final static long serialVersionUID = -8087394462892201578L;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
