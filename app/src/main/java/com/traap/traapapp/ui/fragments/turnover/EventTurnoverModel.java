package com.traap.traapapp.ui.fragments.turnover;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class EventTurnoverModel
{
    private String from_date;
    private String to_date;
    private Integer from_amount;
    private Integer to_amount;
    private Boolean isRemove;

    public String getFrom_date()
    {
        return from_date;
    }

    public void setFrom_date(String from_date)
    {
        this.from_date = from_date;
    }

    public String getTo_date()
    {
        return to_date;
    }

    public void setTo_date(String to_date)
    {
        this.to_date = to_date;
    }

    public Integer getFrom_amount()
    {
        return from_amount;
    }

    public void setFrom_amount(Integer from_amount)
    {
        this.from_amount = from_amount;
    }

    public Integer getTo_amount()
    {
        return to_amount;
    }

    public void setTo_amount(Integer to_amount)
    {
        this.to_amount = to_amount;
    }

    public Boolean getRemove()
    {
        return isRemove;
    }

    public void setRemove(Boolean remove)
    {
        isRemove = remove;
    }
}
