package com.traap.traapapp.apiServices.model.event.participant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParticipantEventIdResponse
        implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;
    @SerializedName("national_code")
    @Expose
    private String nationalCode;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("transaction")
    @Expose
    private Integer transaction;
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("workshop")
    @Expose
    private Integer workshop;
    private final static long serialVersionUID = 5032589288134266701L;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Boolean getIsPaid()
    {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid)
    {
        this.isPaid = isPaid;
    }

    public String getNationalCode()
    {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode)
    {
        this.nationalCode = nationalCode;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getTransaction()
    {
        return transaction;
    }

    public void setTransaction(Integer transaction)
    {
        this.transaction = transaction;
    }

    public Integer getUser()
    {
        return user;
    }

    public void setUser(Integer user)
    {
        this.user = user;
    }

    public Integer getWorkshop()
    {
        return workshop;
    }

    public void setWorkshop(Integer workshop)
    {
        this.workshop = workshop;
    }

}
