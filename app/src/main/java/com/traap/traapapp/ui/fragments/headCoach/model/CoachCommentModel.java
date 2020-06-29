package com.traap.traapapp.ui.fragments.headCoach.model;

import com.traap.traapapp.apiServices.model.getAllComments.Result;

import java.util.List;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class CoachCommentModel
{
    private String comment;
    private Boolean isUser;
    private Result results;
    public CoachCommentModel(String comment, Boolean isUser,Result results)
    {
        this.comment = comment;
        this.isUser = isUser;
        this.results = results;
    }



    public Result getResults()
    {
        return results;
    }

    public void setResults(Result results)
    {
        this.results = results;
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
