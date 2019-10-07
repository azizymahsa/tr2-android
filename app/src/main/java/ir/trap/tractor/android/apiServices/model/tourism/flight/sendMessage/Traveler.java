
package ir.trap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Traveler {

    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("DocNo")
    @Expose
    private String docNo;
    @SerializedName("DocType")
    @Expose
    private Integer docType;
    @SerializedName("FlightPassengerPrices")
    @Expose
    private List<FlightPassengerPrice> flightPassengerPrices = null;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("GivenName")
    @Expose
    private String givenName;
    @SerializedName("NamePrefix")
    @Expose
    private String namePrefix;
    @SerializedName("PassengerType")
    @Expose
    private String passengerType;
    @SerializedName("Passport")
    @Expose
    private Passport passport;
    @SerializedName("Surname")
    @Expose
    private String surname;
    @SerializedName("TicketNumbers")
    @Expose
    private List<String> ticketNumbers = null;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public List<FlightPassengerPrice> getFlightPassengerPrices() {
        return flightPassengerPrices;
    }

    public void setFlightPassengerPrices(List<FlightPassengerPrice> flightPassengerPrices) {
        this.flightPassengerPrices = flightPassengerPrices;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getTicketNumbers() {
        return ticketNumbers;
    }

    public void setTicketNumbers(List<String> ticketNumbers) {
        this.ticketNumbers = ticketNumbers;
    }

}
