package ir.traap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class HotelSendMessageRequest implements Parcelable
{
    @SerializedName("Pnr")
    @Getter @Setter
    private String pnr;

    @SerializedName("IsSuccess")
    @Getter @Setter
    private Boolean isSuccess;

    @SerializedName("UserId")
    @Getter @Setter
    private int UserId;

    @Getter @Setter
    @SerializedName("Pan")
    private String cardNo;

    @SerializedName("Pin2")
    @Getter @Setter
    private String Pin2;

    @SerializedName("Amount")
    @Getter @Setter
    private Integer Amount;

    @SerializedName("ExpDate")
    @Getter @Setter
    private String ExpDate;

    @SerializedName("Cvv2")
    @Nullable
    @Getter @Setter
    private String Cvv2;

    @SerializedName("BookingCode")
    @Getter @Setter
    private String BookingCode;

    @SerializedName("CheckIn")
    @Getter @Setter
    private String CheckIn;

    @SerializedName("CheckOut")
    @Getter @Setter
    private String CheckOut;

    @SerializedName("Currency")
    @Getter @Setter
    private library.android.service.model.Hotel.getBookingInfo.subModel.Currency Currency;

    @SerializedName("ClientInfo")
    @Getter @Setter
    private library.android.service.model.Hotel.getBookingInfo.subModel.ClientInfo ClientInfo;

    @SerializedName("Destination")
    @Getter @Setter
    private library.android.service.model.Hotel.getBookingInfo.subModel.Destination Destination;

    @SerializedName("HotelInfo")
    @Getter @Setter
    private library.android.service.model.Hotel.getBookingInfo.subModel.HotelInfo HotelInfo;

    @SerializedName("Nationality")
    @Getter @Setter
    private String Nationality;

    @SerializedName("PaymentCode")
    @Nullable
    @Getter @Setter
    private Object PaymentCode;

    @SerializedName("ProviderId")
    @Getter @Setter
    private int ProviderId;

    @SerializedName("ReservationCode")
    @Getter @Setter
    private String ReservationCode;

//    @SerializedName("ReservationChar")
//    @Getter @Setter
//    private String ReservationChar;

    @SerializedName("ReservationDateTime")
    @Getter @Setter
    private String ReservationDateTime;

    @SerializedName("Status")
    @Getter @Setter
    private String Status;

    @SerializedName("TotalPrice")
    @Getter @Setter
    private Double TotalPrice;

    @SerializedName("TrackingCode")
    @Getter @Setter
    private String TrackingCode;

    public HotelSendMessageRequest()
    {
    }

    protected HotelSendMessageRequest(Parcel in)
    {
        UserId = in.readInt();
        cardNo = in.readString();
        Pin2 = in.readString();
        ExpDate = in.readString();
        Cvv2 = in.readString();
        BookingCode = in.readString();
        CheckIn = in.readString();
        CheckOut = in.readString();
        Nationality = in.readString();
        ProviderId = in.readInt();
        ReservationCode = in.readString();
        ReservationDateTime = in.readString();
        Status = in.readString();
        if (in.readByte() == 0)
        {
            TotalPrice = null;
        } else
        {
            TotalPrice = in.readDouble();
        }
        TrackingCode = in.readString();
    }

    public static final Creator<HotelSendMessageRequest> CREATOR = new Creator<HotelSendMessageRequest>()
    {
        @Override
        public HotelSendMessageRequest createFromParcel(Parcel in)
        {
            return new HotelSendMessageRequest(in);
        }

        @Override
        public HotelSendMessageRequest[] newArray(int size)
        {
            return new HotelSendMessageRequest[size];
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
        dest.writeInt(UserId);
        dest.writeString(cardNo);
        dest.writeString(Pin2);
        dest.writeString(ExpDate);
        dest.writeString(Cvv2);
        dest.writeString(BookingCode);
        dest.writeString(CheckIn);
        dest.writeString(CheckOut);
        dest.writeString(Nationality);
        dest.writeInt(ProviderId);
        dest.writeString(ReservationCode);
        dest.writeString(ReservationDateTime);
        dest.writeString(Status);
        if (TotalPrice == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(TotalPrice);
        }
        dest.writeString(TrackingCode);
    }
}
