package ir.trap.tractor.android.apiServices.helper;

public class Const
{
    public static final boolean MOCK = false;
    public static final boolean TEST = false;

    //-----------------------------------------------------------------------------
    //public final static String BASEURL = "https://rest.diba24.app/";
    public final static String BASEURL = "http://5.253.25.117:9000/";
    public final static String BASEURLTest = "http://diba.eniac-service.ir:8000/";

    private static final String SubBASEURL = "api/";
    private static final String SubBASEURLTest = "ApiDiba/";
    //-----------------------------------------------------------------------------

    public final static String Login = SubBASEURL + "v1/user/login/";
    public final static String Verify =SubBASEURL +"v1/user/verify/";
    public final static String GetMenu = SubBASEURL + "v1/menu/get_menu/";
    public final static String GetMenuAll = SubBASEURL + "v1/menu/get_all_services/";
    public final static String GetMyBills = SubBASEURL + "v1/bill/";
    public final static String GetBillCodePayCode= SubBASEURL +"v1/bill/get_bill_code_pay_code/";
    public final static String BuyCharge = SubBASEURL+"v1/topupcharge/buy_charge/";

    public final static String MOBILE_CHARGE = SubBASEURL + "TopupCharge/v2/BuyCharge";

    public final static String GET_PACKAGE_RIGHTEL = SubBASEURL + "v1/internetpackage/get_package_rightel/\n";
    public final static String GET_PACKAGE_MCI = SubBASEURL + "v1/internetpackage/get_package_mci/";
    public final static String GET_PACKAGE_IRANCELL = SubBASEURL + "v1/internetpackage/get_package_irancell/";

    public final static String PACKAGE_BUY = SubBASEURL + "InternetPackage/v1/PackageBuy";

    public final static String GetInfoPhoneBill = SubBASEURL + "Bill/v1/GetInfoPhoneBill";
    public final static String BillPayment = SubBASEURL + "Bill/v1/BillPayment";
    public final static String GetInfoBill = SubBASEURL + "Bill/v1/GetInfoBill";

    public final static String GetHappyCardInfo = SubBASEURL + "Happy/v1/GetCardInfo";
    public final static String GetShetabCardInfo = SubBASEURL + "Shetab/v1/GetCardInfo";
    public final static String DoTransferCard = SubBASEURL + "Shetab/v1/DoTransfer";

    //Hotel
    public final static String HotelSendMessage = SubBASEURLTest + "GdsHotel/v1/SendMessage";
    public final static String HotelGetUserPass = SubBASEURLTest + "GdsHotel/v1/GetAccountInfo";
    public final static String HotelPayment = SubBASEURLTest + "GdsHotel/v1/HotelPayment";
    //flight
    public final static String FlightPayment = SubBASEURLTest + "GdsFlight/v1/FlightPayment";
    public final static String FlightSendMessage = SubBASEURLTest + "GdsFlight/v1/SendMessage";
    public final static String FlightGetUserPass = SubBASEURLTest + "GdsFlight/v1/GetAccountInfo";
    //Bus
    public final static String SendBusMessage = SubBASEURLTest + "GdsBus/v1/SendMessage";
    public final static String GetBusUserPass = SubBASEURLTest + "GdsBus/v1/GetAccountInfo";
    public final static String BusPayment = SubBASEURLTest + "GdsBus/v1/BusPayment";

    public final static String GetCardList = SubBASEURL + "v1/card";

    public final static String DECRYPTQRCODE = "api/v1/payment/decrypt_qr_code/";



}
