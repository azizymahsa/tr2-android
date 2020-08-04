package com.traap.traapapp.ui.fragments.events;

public class PersonEvent
{
    private int workshopId=0;
    private int count=0;
    private String national_code="";
    private String first_name="";
    private String last_name="";
    private String mobile="";
    private String email="";
    private String price="";

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNational_code()
    {
        return national_code;
    }

    public void setNational_code(String national_code)
    {
        this.national_code = national_code;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public int getWorkshopId()
    {
        return workshopId;
    }

    public void setWorkshopId(int workshopId)
    {
        this.workshopId = workshopId;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
}
