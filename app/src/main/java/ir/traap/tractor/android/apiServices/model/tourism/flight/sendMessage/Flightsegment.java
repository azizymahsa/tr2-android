
package ir.traap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flightsegment {

    @SerializedName("ArrivalDateTime")
    @Expose
    private String arrivalDateTime;
    @SerializedName("BookingClassName")
    @Expose
    private String bookingClassName;
    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;
    @SerializedName("FlightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("FlightType")
    @Expose
    private String flightType;
    @SerializedName("JourneyDuration")
    @Expose
    private String journeyDuration;
    @SerializedName("MarketingAirline")
    @Expose
    private String marketingAirline;
    @SerializedName("OperatingAirline")
    @Expose
    private OperatingAirline operatingAirline;
    @SerializedName("OriginAirport")
    @Expose
    private OriginAirport originAirport;
    @SerializedName("DestinationAirport")
    @Expose
    private DestinationAirport destinationAirport;
    @SerializedName("TerminalDestination")
    @Expose
    private String terminalDestination;
    @SerializedName("TerminalOrigin")
    @Expose
    private String terminalOrigin;
    @SerializedName("Ticket")
    @Expose
    private String ticket;
    @SerializedName("PlaneType")
    @Expose
    private String planeType;

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getBookingClassName() {
        return bookingClassName;
    }

    public void setBookingClassName(String bookingClassName) {
        this.bookingClassName = bookingClassName;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getJourneyDuration() {
        return journeyDuration;
    }

    public void setJourneyDuration(String journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public String getMarketingAirline() {
        return marketingAirline;
    }

    public void setMarketingAirline(String marketingAirline) {
        this.marketingAirline = marketingAirline;
    }

    public OperatingAirline getOperatingAirline() {
        return operatingAirline;
    }

    public void setOperatingAirline(OperatingAirline operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public OriginAirport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(OriginAirport originAirport) {
        this.originAirport = originAirport;
    }

    public DestinationAirport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getTerminalDestination() {
        return terminalDestination;
    }

    public void setTerminalDestination(String terminalDestination) {
        this.terminalDestination = terminalDestination;
    }

    public String getTerminalOrigin() {
        return terminalOrigin;
    }

    public void setTerminalOrigin(String terminalOrigin) {
        this.terminalOrigin = terminalOrigin;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

}
