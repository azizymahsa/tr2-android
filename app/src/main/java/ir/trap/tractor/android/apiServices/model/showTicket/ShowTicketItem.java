package ir.trap.tractor.android.apiServices.model.showTicket;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketItem
{
    private  String nationalCode;
    private  String name;

    public ShowTicketItem(String name, String nationalCode)
    {
        this.name=name;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
