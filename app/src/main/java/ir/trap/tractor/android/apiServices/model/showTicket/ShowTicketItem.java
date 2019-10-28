package ir.trap.tractor.android.apiServices.model.showTicket;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketItem
{
    private  String nationalCode;
    private  String firstName;

    public ShowTicketItem(String firstName, String nationalCode)
    {
        this.firstName=firstName;
        this.nationalCode=nationalCode;
    }

    public String getNationalCode()
    {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode)
    {
        this.nationalCode = nationalCode;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
}
