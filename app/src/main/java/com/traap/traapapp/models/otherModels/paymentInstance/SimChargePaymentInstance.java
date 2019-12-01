package com.traap.traapapp.models.otherModels.paymentInstance;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class SimChargePaymentInstance implements Parcelable
{
    @Getter @Setter
    int PAYMENT_STATUS;

    @Getter @Setter
    int operatorType;

    @Getter @Setter
    int simcardType;

    @Getter @Setter
    int typeCharge;

    public SimChargePaymentInstance()
    {

    }

    protected SimChargePaymentInstance(Parcel in)
    {
        PAYMENT_STATUS = in.readInt();
        operatorType = in.readInt();
        simcardType = in.readInt();
        typeCharge = in.readInt();
    }

    public static final Creator<SimChargePaymentInstance> CREATOR = new Creator<SimChargePaymentInstance>()
    {
        @Override
        public SimChargePaymentInstance createFromParcel(Parcel in)
        {
            return new SimChargePaymentInstance(in);
        }

        @Override
        public SimChargePaymentInstance[] newArray(int size)
        {
            return new SimChargePaymentInstance[size];
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
        dest.writeInt(PAYMENT_STATUS);
        dest.writeInt(operatorType);
        dest.writeInt(simcardType);
        dest.writeInt(typeCharge);
    }
}
