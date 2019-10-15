package ir.trap.tractor.android.apiServices.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WebServiceClass<T>
{
    @SerializedName("code")
    public Integer statusCode;

    @SerializedName("message")
    public String message;

    @SerializedName("exception")
    public String exception;

    @SerializedName("data")
    public T data;

}
