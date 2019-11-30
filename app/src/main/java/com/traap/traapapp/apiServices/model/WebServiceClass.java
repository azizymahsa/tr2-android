package com.traap.traapapp.apiServices.model;

import com.google.gson.annotations.SerializedName;

public class WebServiceClass<T>
{
    @SerializedName("info")
    public Info info;

    @SerializedName("data")
    public T data;

    public class Info
    {
        @SerializedName("code")
        public Integer statusCode;

        @SerializedName("message")
        public String message;

        @SerializedName("exception")
        public String exception;

    }
}
