package ir.traap.tractor.android.models.otherModels.paymentInstance;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class TicketPaymentInstance implements Parcelable
{
    @Getter @Setter
    Integer PAYMENT_STATUS;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String nationalCode;

    @Getter @Setter
    private int ticketId;

    public TicketPaymentInstance()
    {
    }

    protected TicketPaymentInstance(Parcel in)
    {
        if (in.readByte() == 0)
        {
            PAYMENT_STATUS = null;
        } else
        {
            PAYMENT_STATUS = in.readInt();
        }
        firstName = in.readString();
        lastName = in.readString();
        nationalCode = in.readString();
        ticketId = in.readInt();
    }

    public static final Creator<TicketPaymentInstance> CREATOR = new Creator<TicketPaymentInstance>()
    {
        @Override
        public TicketPaymentInstance createFromParcel(Parcel in)
        {
            return new TicketPaymentInstance(in);
        }

        @Override
        public TicketPaymentInstance[] newArray(int size)
        {
            return new TicketPaymentInstance[size];
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(nationalCode);
        dest.writeInt(ticketId);
    }
}
