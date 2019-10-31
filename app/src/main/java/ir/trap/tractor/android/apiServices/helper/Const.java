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

    //Login
    public final static String Login = SubBASEURL + "v1/user/login/";
    public final static String Verify = SubBASEURL + "v1/user/login/verify/";


    //Menu
    public final static String GetMenu = SubBASEURL + "v1/menu/get_menu/";
    public final static String GetMenuAll = SubBASEURL + "v1/menu/get_all_services/";


    public final static String GetAllBoxes = SubBASEURL + "v1/match/all_boxes/";

    //Mobile
    public final static String BUY_MOBILE_CHARGE = SubBASEURL + "v1/topupcharge/buy_charge/";
    public final static String BUY_MOBILE_PACKAGE = SubBASEURL + "InternetPackage/v1/PackageBuy";
    public final static String GET_PACKAGE_RIGHTEL = SubBASEURL + "v1/internetpackage/get_package_rightel/";
    public final static String GET_PACKAGE_MCI = SubBASEURL + "v1/internetpackage/get_package_mci/";
    public final static String GET_PACKAGE_IRANCELL = SubBASEURL + "v1/internetpackage/get_package_irancell/";

    //Bill
    public final static String GetMyBills = SubBASEURL + "v1/bill/";
    public final static String GetBillCodePayCode = SubBASEURL + "v1/bill/get_bill_code_pay_code/";
    public final static String GetInfoPhoneBill = SubBASEURL + "Bill/v1/GetInfoPhoneBill";
    public final static String BillPayment = SubBASEURL + "Bill/v1/BillPayment";
    public final static String GetInfoBill = SubBASEURL + "Bill/v1/GetInfoBill";


    //card_Shetac (HappyCard)
    public final static String FORGOT_PASS2 = SubBASEURL + "v1/shetac/forget_password_tow/";
    public final static String CHANGE_PASS2 = SubBASEURL + "v1/shetac/change_password_tow/";
    public final static String GetHappyCardInfo = SubBASEURL + "Happy/v1/GetCardInfo";
    public final static String GetShetabCardInfo = SubBASEURL + "Shetab/v1/GetCardInfo";
    public final static String DoTransferCard = SubBASEURL + "Shetab/v1/DoTransfer";


    //card
    public final static String GetCardList = SubBASEURL + "v1/card/";
    public final static String AddCard = SubBASEURL + "v1/card/";
    public final static String DeleteCard = SubBASEURL + "v1/card/";
    public final static String EditCard = SubBASEURL + "v1/card/";


    //Hotel
    public final static String HotelSendMessage = SubBASEURL + "GdsHotel/v1/SendMessage";
    public final static String HotelGetUserPass = SubBASEURL + "GdsHotel/v1/GetAccountInfo";
    public final static String HotelPayment = SubBASEURL + "GdsHotel/v1/HotelPayment";


    //flight
    public final static String FlightPayment = SubBASEURL + "GdsFlight/v1/FlightPayment";
    public final static String FlightSendMessage = SubBASEURL + "GdsFlight/v1/SendMessage";
    public final static String FlightGetUserPass = SubBASEURL + "GdsFlight/v1/GetAccountInfo";


    //Bus
    public final static String SendBusMessage = SubBASEURL + "GdsBus/v1/SendMessage";
    public final static String GetBusUserPass = SubBASEURL + "GdsBus/v1/GetAccountInfo";
    public final static String BusPayment = SubBASEURL + "GdsBus/v1/BusPayment";


    //Pos
    public final static String DECRYPTQRCODE = SubBASEURL + "v1/payment/decrypt_qr_code/";
    public final static String PAYMENT_PRINT_pOS = SubBASEURL + "v1/payment/payment_print_pos/";


    //Bank List
    public final static String BANK_LIST = SubBASEURL + "v1/menu/bank/";


    //Ticket
    public final static String GetMatch = SubBASEURL + "v1/match/";


    //History
    public final static String GetHistory = SubBASEURL + "v1/club_history/get_history/";


    //Version
    public final static String GET_VERSION = SubBASEURL + "v1/version/get_version_android/";

}
