
package com.traap.traapapp.apiServices.model.tourism.bus.getPaymentBus.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteDetail{

    @SerializedName("Seats")
    @Expose
    private String seats;

    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;

    @SerializedName("Refrences")
    @Expose
    private String refrences;
    @SerializedName("RouteId")
    @Expose
    private String routeId;
    @SerializedName("RoutePrice")
    @Expose
    private Integer routePrice;
    @SerializedName("Sale_No")
    @Expose
    private Integer saleNo;
    @SerializedName("ServiceReservationId")
    @Expose
    private String serviceReservationId;
    @SerializedName("BusPrice")
    @Expose
    private BusPrice busPrice;
    @SerializedName("ReservationId")
    @Expose
    private String reservationId;
    @SerializedName("DestinationCity")
    @Expose
    private DestinationCity destinationCity;
    @SerializedName("OriginCity")
    @Expose
    private OriginCity originCity;
    @SerializedName("Passenger")
    @Expose
    private Passenger passenger;

    @SerializedName("Company")
    @Expose
    private Company company;

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getRefrences() {
        return refrences;
    }

    public void setRefrences(String refrences) {
        this.refrences = refrences;
    }


    
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getRoutePrice() {
        return routePrice;
    }

    public void setRoutePrice(Integer routePrice) {
        this.routePrice = routePrice;
    }

    public Integer getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(Integer saleNo) {
        this.saleNo = saleNo;
    }

    public String getServiceReservationId() {
        return serviceReservationId;
    }

    public void setServiceReservationId(String serviceReservationId) {
        this.serviceReservationId = serviceReservationId;
    }

    public BusPrice getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(BusPrice busPrice) {
        this.busPrice = busPrice;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public DestinationCity getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(DestinationCity destinationCity) {
        this.destinationCity = destinationCity;
    }

    public OriginCity getOriginCity() {
        return originCity;
    }

    public void setOriginCity(OriginCity originCity) {
        this.originCity = originCity;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
