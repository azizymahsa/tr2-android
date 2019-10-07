package ir.trap.tractor.android.apiServices.helper;

public class Const
{
    public static final boolean MOCK = false;
    public static final boolean TEST = false;

    //-----------------------------------------------------------------------------
    //public final static String BASEURL = "https://rest.diba24.app/";
    public final static String BASEURL = "http://diba.eniac-service.ir:8000/";

    private static final String SubBASEURL = "ApiDiba/";
    //-----------------------------------------------------------------------------

    public final static String MOBILE_CHARGE = SubBASEURL + "TopupCharge/v2/BuyCharge";

    public final static String GET_PACKAGE_RIGHTEL = SubBASEURL + "InternetPackage/v1/GetPackageRightel";
    public final static String GET_PACKAGE_MCI = SubBASEURL + "InternetPackage/v1/GetPackageMci";
    public final static String GET_PACKAGE_IRANCELL = SubBASEURL + "InternetPackage/v1/GetPackageIrancell";

    public final static String PACKAGE_BUY = SubBASEURL + "InternetPackage/v1/PackageBuy";

    public final static String GetInfoPhoneBill = SubBASEURL + "Bill/v1/GetInfoPhoneBill";
    public final static String BillPayment = SubBASEURL + "Bill/v1/BillPayment";
    public final static String GetInfoBill = SubBASEURL + "Bill/v1/GetInfoBill";

    public final static String GetHappyCardInfo = "ApiDiba/Happy/v1/GetCardInfo";
    public final static String GetShetabCardInfo = "ApiDiba/Shetab/v1/GetCardInfo";
    public final static String DoTransferCard = "ApiDiba/Shetab/v1/DoTransfer";

    //Hotel
    public final static String HotelSendMessage = "ApiShetac/GdsHotel/v1/SendMessage";
    public final static String HotelGetUserPass = "ApiShetac/GdsHotel/v1/GetAccountInfo";
    public final static String HotelPayment = "ApiShetac/GdsHotel/v1/HotelPayment";
    //flight
    public final static String FlightPayment = "ApiShetac/GdsFlight/v1/FlightPayment";
    public final static String FlightSendMessage = "ApiShetac/GdsFlight/v1/SendMessage";
    public final static String FlightGetUserPass = "ApiShetac/GdsFlight/v1/GetAccountInfo";
    //Bus
    public final static String SendBusMessage = "ApiShetac/GdsBus/v1/SendMessage";
    public final static String GetBusUserPass = "ApiShetac/GdsBus/v1/GetAccountInfo";
    public final static String BusPayment = "ApiShetac/GdsBus/v1/BusPayment";



}
