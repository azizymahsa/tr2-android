package com.traap.traapapp.apiServices.model.withdrawWallet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WithdrawWalletResponse implements Parcelable
{

    @SerializedName("ref_no")
    @Expose
    private Long refNumber;

    @SerializedName("amount")
    @Expose
    private Long amount;

    @SerializedName("sheba_number")
    @Expose
    private String shebaNumber;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("date_time")
    @Expose
    private String dateTime;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRefNumber()
    {
        return refNumber;
    }

    public void setRefNumber(Long refNumber)
    {
        this.refNumber = refNumber;
    }

    public Long getAmount()
    {
        return amount;
    }

    public void setAmount(Long amount)
    {
        this.amount = amount;
    }

    public String getShebaNumber()
    {
        return shebaNumber;
    }

    public void setShebaNumber(String shebaNumber)
    {
        this.shebaNumber = shebaNumber;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(this.refNumber);
        dest.writeValue(this.amount);
        dest.writeString(this.shebaNumber);
        dest.writeString(this.from);
        dest.writeString(this.dateTime);
        dest.writeString(this.message);
    }

    public WithdrawWalletResponse()
    {
    }

    protected WithdrawWalletResponse(Parcel in)
    {
        this.refNumber = (Long) in.readValue(Long.class.getClassLoader());
        this.amount = (Long) in.readValue(Long.class.getClassLoader());
        this.shebaNumber = in.readString();
        this.from = in.readString();
        this.dateTime = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<WithdrawWalletResponse> CREATOR = new Parcelable.Creator<WithdrawWalletResponse>()
    {
        @Override
        public WithdrawWalletResponse createFromParcel(Parcel source)
        {
            return new WithdrawWalletResponse(source);
        }

        @Override
        public WithdrawWalletResponse[] newArray(int size)
        {
            return new WithdrawWalletResponse[size];
        }
    };
}

