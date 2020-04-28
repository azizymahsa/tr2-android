package com.traap.traapapp.models.otherModels.ticket;

public class SpectatorInfoModel {

    String lastName;
    String firstName;
    String nationalCode;

    public SpectatorInfoModel(String firstName, String lastName, String nationalCode)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.nationalCode = nationalCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
