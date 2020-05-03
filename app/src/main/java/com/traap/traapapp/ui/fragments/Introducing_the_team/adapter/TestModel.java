package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TestModel
{
    Integer integer;
    String name;

    public TestModel(Integer integer, String name)
    {
        this.integer = integer;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getInteger()
    {
        return integer;
    }

    public void setInteger(Integer integer)
    {
        this.integer = integer;
    }
}
