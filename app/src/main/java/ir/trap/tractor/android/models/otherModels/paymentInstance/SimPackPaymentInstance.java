package ir.trap.tractor.android.models.otherModels.paymentInstance;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class SimPackPaymentInstance implements Parcelable
{
    @Getter @Setter
    Integer PAYMENT_STATUS;

    @Getter @Setter
    Integer operatorType;

    @Getter @Setter
    String titlePackageType;

    @Getter @Setter
    String requestId;

    @Getter @Setter
    Integer profileId;

    public SimPackPaymentInstance()
    {

    }

    protected SimPackPaymentInstance(Parcel in)
    {
        if (in.readByte() == 0)
        {
            PAYMENT_STATUS = null;
        } else
        {
            PAYMENT_STATUS = in.readInt();
        }
        if (in.readByte() == 0)
        {
            operatorType = null;
        } else
        {
            operatorType = in.readInt();
        }
        titlePackageType = in.readString();
        requestId = in.readString();
        if (in.readByte() == 0)
        {
            profileId = null;
        } else
        {
            profileId = in.readInt();
        }
    }

    public static final Creator<SimPackPaymentInstance> CREATOR = new Creator<SimPackPaymentInstance>()
    {
        @Override
        public SimPackPaymentInstance createFromParcel(Parcel in)
        {
            return new SimPackPaymentInstance(in);
        }

        @Override
        public SimPackPaymentInstance[] newArray(int size)
        {
            return new SimPackPaymentInstance[size];
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
        if (PAYMENT_STATUS == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(PAYMENT_STATUS);
        }
        if (operatorType == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(operatorType);
        }
        dest.writeString(titlePackageType);
        dest.writeString(requestId);
        if (profileId == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(profileId);
        }
    }
}
