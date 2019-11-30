package com.traap.traapapp.apiServices.helper;


public class Const
{
    public static final boolean MOCK = false;
    public static final boolean TEST = false;

    //-----------------------------------------------------------------------------
    //public final static String BASEURL = "https://rest.diba24.app/";
    public final static String BASEURL = "http://5.253.25.117:9000/";
//    public final static String BASEURLTest = "http://diba.eniac-service.ir:8000/";

    private static final String SubBASEURL = "api/";
//    private static final String SubBASEURLTest = "ApiDiba/";
    //-----------------------------------------------------------------------------

    //Login
    public final static String Login = SubBASEURL + "v1/user/login/";
    public final static String Verify = SubBASEURL + "v1/user/login/verify/";


    //Menu
    public final static String GetMenu = SubBASEURL + "v1/menu/get_menu/";
    public final static String GetMenuAll = SubBASEURL + "v1/menu/get_all_services/";
    public final static String GetMenuHelp = SubBASEURL + "v1/menu/help/";

    //stadium
    public final static String GetAllBoxes = SubBASEURL + "v1/match/all_boxes/";
    public final static String ReservationMatch = SubBASEURL + "v1/match/reservation/";
    public final static String GetTicketInfo = SubBASEURL + "v1/match/ticket_info/";


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
    //payment
    public final static String PaymentMatch = SubBASEURL + "v1/match/ipg/payment/";//"v1/match/payment_ipg/";
    public final static String PaymentWallet = SubBASEURL + "v1/match/wallet/payment/";

    //Bank List
    public final static String BANK_LIST = SubBASEURL + "v1/menu/bank/";


    //MatchList
    public final static String GET_Match_List = SubBASEURL + "v1/match/";


    //History
    public final static String GetHistory = SubBASEURL + "v1/club_history/get_history/";


    //Version
    public final static String GET_VERSION = SubBASEURL + "v1/version/get_version_android/";

    //Leagues
    public final static String GET_Leage = SubBASEURL + "v1/livescore/get_leagues/";
    public final static String Get_Past_result = SubBASEURL + "v1/livescore/get_past_result/";

    //Predict
    public final static String GET_PREDICT = SubBASEURL + "v1/predict/";
    public final static String SEND_PREDICT = SubBASEURL + "v1/predict/";

    //Rule
    public final static String GET_RULES = SubBASEURL + "v1/match/";

    //Profile
    public final static String GET_PROFILE = SubBASEURL + "v1/user/profile/";
    public final static String PUT_PROFILE = SubBASEURL + "v1/user/profile/";

    //Transaction
    public final static String GET_Transaction_List = SubBASEURL + "v1/user/transactions/";

    //video
    public final static String Get_Main_Video = SubBASEURL + "v1/video/video_main/";
    public final static String Get_Category_By_Id_Video = SubBASEURL + "v1/video/{id}/recent_by_category/";
    public final static String Like_Video=SubBASEURL+"v1/video/{id}/like/";
    public final static String Archive_Video=SubBASEURL+"v1/video/";
    //photos
    public final static String Get_Main_Photo = SubBASEURL + "v1/photo/photo_main/";
    public final static String Get_Category_By_Id_Photo = SubBASEURL + "v1/photo/{id}/recent_by_category/";
    public final static String Get_Photos_By_Id = SubBASEURL + "v1/photo/{id}/";
    public final static String Archive_Photo=SubBASEURL+"v1/photo/";
    public final static String Like_Photo=SubBASEURL+"v1/photo/{id}/like/";

    //News
    public final static String NEWS_MAIN = SubBASEURL + "v1/news/home/";
    public final static String Get_NEWS_ARCHIVE_BY_ID = SubBASEURL + "v1/news/";
    public final static String Get_NEWS_ARCHIVE_CATEGORY = SubBASEURL + "v1/news/category/";
    public final static String Get_NEWS_DETAILS = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_LIKE = SubBASEURL + "v1/news/";


}