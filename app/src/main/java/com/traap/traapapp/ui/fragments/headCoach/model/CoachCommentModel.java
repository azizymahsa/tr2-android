package com.traap.traapapp.ui.fragments.headCoach.model;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class CoachCommentModel
{
    private String comment;
    private Boolean isUser;

    public CoachCommentModel(String comment, Boolean isUser)
    {
        this.comment = comment;
        this.isUser = isUser;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Boolean getUser()
    {
        return isUser;
    }

    public void setUser(Boolean user)
    {
        isUser = user;
    }
}
