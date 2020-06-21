package com.traap.traapapp.models.otherModels.qrModel;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class QrModel implements Parcelable
{
    @Getter @Setter
    private String merchantId;

    @Getter @Setter
    private Integer price = 0;

    public QrModel(String merchantId, Integer price)
    {
        this.merchantId = merchantId;
        this.price = price;
    }

    protected QrModel(Parcel in)
    {
        merchantId = in.readString();
        if (in.readByte() == 0)
        {
            price = null;
        }
        else
        {
            price = in.readInt();
        }
    }

    public static final Creator<QrModel> CREATOR = new Creator<QrModel>()
    {
        @Override
        public QrModel createFromParcel(Parcel in)
        {
            return new QrModel(in);
        }

        @Override
        public QrModel[] newArray(int size)
        {
            return new QrModel[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(merchantId);
        if (price == null)
        {
            dest.writeByte((byte) 0);
        }
        else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
    }
}
