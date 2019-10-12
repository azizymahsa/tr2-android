package ir.trap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Travelers
{
    @SerializedName("BirthDate")
    @Expose
    @Getter @Setter
    private String BirthDate;

    @SerializedName("Gender")
    @Expose
    @Getter @Setter
    private String Gender;

    @SerializedName("GivenName")
    @Expose
    @Getter @Setter
    private String GivenName;

    @SerializedName("NamePrefix")
    @Expose
    @Getter @Setter
    private String NamePrefix;

    @SerializedName("PassengerType")
    @Expose
    @Getter @Setter
    private String PassengerType;

    @SerializedName("Surname")
    @Expose
    @Getter @Setter
    private String Surname;

}
