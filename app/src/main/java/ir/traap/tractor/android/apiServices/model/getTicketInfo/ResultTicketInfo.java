package ir.traap.tractor.android.apiServices.model.getTicketInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class ResultTicketInfo
{
    @SerializedName("match_date")
    @Expose
    private String matchDate;
    @SerializedName("stadium_name")
    @Expose
    private String stadiumName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("reservation_ticket_id")
    @Expose
    private Integer reservationTicketId;
    @SerializedName("home_logo")
    @Expose
    private String homeLogo;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("away_logo")
    @Expose
    private String awayLogo;
    @SerializedName("home_name")
    @Expose
    private String homeName;
    @SerializedName("box_name")
    @Expose
    private String boxName;
    @SerializedName("national_code")
    @Expose
    private String nationalCode;
    @SerializedName("away_name")
    @Expose
    private String awayName;

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getReservationTicketId() {
        return reservationTicketId;
    }

    public void setReservationTicketId(Integer reservationTicketId) {
        this.reservationTicketId = reservationTicketId;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public void setHomeLogo(String homeLogo) {
        this.homeLogo = homeLogo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getAwayLogo() {
        return awayLogo;
    }

    public void setAwayLogo(String awayLogo) {
        this.awayLogo = awayLogo;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }
}
