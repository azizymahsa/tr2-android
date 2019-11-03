package ir.trap.tractor.android.apiServices.model.showTicket;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketItem
{
    private  String nationalCode;
    private  String name;
    private  String station;

    public ShowTicketItem(String name, String nationalCode,String station)
    {
        this.name=name;
        this.nationalCode=nationalCode;
        this.station=station;
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

    public String getStation()
    {
        return station;
    }

    public void setStation(String station)
    {
        this.station = station;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
