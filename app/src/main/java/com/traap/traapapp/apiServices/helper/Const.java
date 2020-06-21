package com.traap.traapapp.apiServices.helper;


import com.androidnetworking.common.ANRequest;

public class Const
{
    public static final boolean MOCK = false;
    public static final boolean TEST = false;

    //-----------------------------------------------------------------------------
   public final static String BASEURL = "http://5.253.25.117:9000/";     //developing
    //public final static String BASEURL = "http:/stage-rest.traap.com/";     //developing
    //public final static String BASEURL = "http://5.253.25.117:5000/";     //pre Live
    //public final static String BASEURL = "http://79.175.146.58:8000/";     //pre Live
   // public final static String BASEURL = "https://restapp.traap.com/";   //Live
//    public final static String BASEURL = "https://restapp.traap.com/";   //Live

    private static final String SubBASEURL = "api/";
    //-----------------------------------------------------------------------------

    //Login
    public final static String Login = SubBASEURL + "v1/user/login/";
    public final static String Verify = SubBASEURL + "v1/user/login/verify/";

    //contactInfo
    public final static String GetContactInfo = SubBASEURL + "v1/info/contact/";

    //Menu
    public final static String GetMenu = SubBASEURL + "v2/menu/get_menu/";
    public final static String GetMenuAll = SubBASEURL + "v2/menu/get_all_services/";
    public final static String GetMenuHelp = SubBASEURL + "v1/menu/help/";

    //stadium
    public final static String GetAllBoxes = SubBASEURL + "v1/match/all_boxes/";
    public final static String ReservationMatch = SubBASEURL + "v1/match/reservation/";
    public final static String GetTicketInfo = SubBASEURL + "v1/match/ticket_info/";
    public final static String GetTicketBuyEnable = SubBASEURL + "v1/match/buy_enable/";
    public final static String GetSpectatorInfo=SubBASEURL+"v1/match/spectator/{national_code}";
    public final static String GetSpectatorList=SubBASEURL+"v1/match/spectator";

    //Mobile
    public final static String GET_BOUGHT_FOR = SubBASEURL + "v1/topupcharge/bought_for/";
    public final static String BUY_MOBILE_CHARGE = SubBASEURL + "v1/topupcharge/buy_charge/";
    public final static String BUY_CHARGE_WALLET = SubBASEURL + "v1/topupcharge/buy_charge_wallet/";
    public final static String AvailableAmount = SubBASEURL + "v1/topupcharge/available_amount/";
    public final static String BUY_PACKAGE_WALLET = SubBASEURL + "v1/internetpackage/package_buy_wallet/";
    public final static String BUY_MOBILE_PACKAGE = SubBASEURL + "v1/internetpackage/package_buy/";
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
    //wallet
    public final static String GetInfoWallet = SubBASEURL + "v1/shetac/get_info/";
    public final static String IncreaseInventoryWallet = SubBASEURL + "v1/shetac/increase_inventory_ipg/";
    public final static String Get_Withdraw_Wallet = SubBASEURL + "v1/shetac/withdraw/";
    public final static String DoTransferWallet=SubBASEURL+"v1/shetac/do_transfer/";

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
    public final static String GetBalancePasswordLess = SubBASEURL + "v1/shetac/get_balance_password_less/";
    public final static String ForgetPasswordWallet = SubBASEURL + "v1/shetac/forget_password_tow/";
    public final static String ChangePasswordWallet = SubBASEURL + "v1/shetac/change_password_tow/";
    public final static String GetReport = SubBASEURL + "v1/shetac/get_report/";

    //Bank List
    public final static String BANK_LIST = SubBASEURL + "v1/menu/bank/";

    //MatchList
    public final static String GET_Match_List = SubBASEURL + "v1/match/";
    public final static String Get_Past_Result = SubBASEURL + "v1/livescore/get_past_result/";
    public final static String Get_Past_Result_v2 = SubBASEURL + "v2/livescore/get_past_result/";

    //History
    public final static String GetHistory = SubBASEURL + "v1/club_history/get_history/";

    //Version
    public final static String GET_VERSION = SubBASEURL + "v1/version/get_version_android/";

    //Leagues
    public final static String GET_League = SubBASEURL + "v1/livescore/get_leagues/";

    //Predict
    public final static String GET_PREDICT = SubBASEURL + "v2/predict/";
    public final static String SEND_PREDICT = SubBASEURL + "v2/predict/";
    public final static String GET_MY_PREDICTS = SubBASEURL + "v1/user/my_predicts/";
    public final static String GET_PREDICT_ENABLE = SubBASEURL + "v1/match/predict_enable/";

    //Rule
    public final static String GET_RULES = SubBASEURL + "v1/match/";

    //Profile
    public final static String GET_PROFILE = SubBASEURL + "v1/user/profile/";
    public final static String PUT_PROFILE = SubBASEURL + "v1/user/profile/";
    public final static String GET_Invite = SubBASEURL + "v1/user/share_text_invite_friend/";
    public final static String SEND_PROFILE_PHOTO = SubBASEURL + "v1/user/profile/photo/";
    public final static String DELETE_PROFILE_PHOTO = SubBASEURL + "v1/user/profile/delete_photo/";

    //DeleteProfile
    public final static String DELETE_PROFILE_Send_Code = SubBASEURL + "v1/user/deactivate/send_code/";
    public final static String DELETE_PROFILE_Verify_Code = SubBASEURL + "v1/user/deactivate/verify/";


    //Transaction
    public final static String GET_TYPE_Transaction_List = SubBASEURL + "v1/user/type_transaction/";
    public final static String GET_Transaction_List = SubBASEURL + "v1/user/transactions/";
    public final static String GET_TRANSACTION_DETAIL = SubBASEURL + "v1/user/{id}/transactions/";

    //video
    public final static String Get_Main_Video = SubBASEURL + "v1/video/video_main/";
    public final static String Get_VIDEOS_ARCHIVE_CATEGORY = SubBASEURL + "v1/video/category/";
    public final static String Get_VIDEOS_ARCHIVE_BY_IDs = SubBASEURL + "v1/video/";
  //  public final static String Get_Category_By_Id_Video = SubBASEURL + "v1/video/{id}/recent_by_category/";
    public final static String Get_Category_By_Id_Video = SubBASEURL + "v1/video/";



    public final static String Get_Category_By_Id_Video2 = SubBASEURL + "v1/video/{id}/related/";
    public final static String Like_Video = SubBASEURL + "v1/video/{id}/like/";
    public final static String bookMark_Video = SubBASEURL + "v1/video/{id}/bookmark/";
    public final static String List_Bookmark_Video = SubBASEURL + "v1/video/bookmarks/";

    //photos
    public final static String Get_Main_Photo = SubBASEURL + "v1/photo/photo_main/";
    public final static String Get_Category_By_Id_Photo = SubBASEURL + "v1/photo/{id}/recent_by_category/";
    public final static String Get_Category_By_Id_Photo2 = SubBASEURL + "v1/photo/{id}/related/";
    public final static String Get_Photos_By_Id = SubBASEURL + "v1/photo/{id}/";
    public final static String Archive_Photo = SubBASEURL + "v1/photo/";
    public final static String GET_CATEGORY_ARCHIVE_PHOTO = SubBASEURL + "v1/photo/category/";
    public final static String Like_Photo = SubBASEURL + "v1/photo/{id}/like/";
    public final static String bookMark_Photo = SubBASEURL + "v1/photo/{id}/bookmark/";
    public final static String List_Bookmark_Photo = SubBASEURL + "v1/photo/bookmarks/";
    public final static String Get_Photo_Detail=SubBASEURL+"v1/photo/{id}/photo_detail/";

    //News
    public final static String NEWS_MAIN = SubBASEURL + "v1/news/home/";
    public final static String Get_NEWS_ARCHIVE_BY_IDs = SubBASEURL + "v1/news/";
    public final static String Get_NEWS_ARCHIVE_CATEGORY = SubBASEURL + "v1/news/category/";
    public final static String Get_NEWS_DETAILS = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_LIKE = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_SEND_COMMENT = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_GET_COMMENT = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_LIKE_COMMENT = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_SET_BOOKMARK = SubBASEURL + "v1/news/";
    public final static String NEWS_DETAILS_GET_BOOKMARK = SubBASEURL + "v1/news/bookmarks/";

    //Points
    public final static String GET_POINTS_RECORD = SubBASEURL + "v1/point/";
    public final static String GET_POINTS_GUIDE = SubBASEURL + "v1/point/info/";
    public final static String GET_POINTS_GROUP_BY = SubBASEURL + "v1/point/group_by/";

//    //Setting
//    public final static String SETTING = SubBASEURL + "v1/setting/";

    //moneyTransfer
    public final static String mainpage = SubBASEURL + "v1/shetab/main_page/";

    //inviteFriend
    public final static String Get_Invite_Friend=SubBASEURL+"v1/invite_friend/";

    //Lottery for Predict
    public final static String GET_LOTTERY_WINNER_LIST = SubBASEURL + "v1/lottery/{matchId}";

    //editUser
    public final static String Get_verify_change_user = SubBASEURL + "v1/user/change_username/verify/";
    public final static String Get_send_code_change_user = SubBASEURL + "v1/user/change_username/send_code/";
    public final static String traktor = SubBASEURL + "v1/team/traktor/";

    //survey
    public final static String Get_DETAIL_SURVEY = SubBASEURL + "v1/survey/{id}/";
    public final static String Get_LIST_SURVEY = SubBASEURL + "v1/survey/";

    //headTechs
    public final static String TECHS = SubBASEURL + "v1/team/techs/";
    public final static String CUPS = SubBASEURL + "v1/team/cups/";

    public final static String TECHS_ID=SubBASEURL+"v1/team/techs/{id}/";
    public final static String TECHS_HISTORY=SubBASEURL+"v1/team/techs/{id}/history/";
    public final static String Get_Tech_News=SubBASEURL+"v1/news/{id}/tech_staff/";

    //compation
    public final static String Get_All_Compations=SubBASEURL+"v1/competition/";
    public final static String Get_All_Questions=SubBASEURL+"v1/competition/{id}/";
    public final static String Post_Answer=SubBASEURL+"v1/competition/{id}/answers/";
    //favorite player interview
    public final static String Post_Favorite=SubBASEURL+"v1/team/techs/fan_page/";
    //MySupportProfile
    public final static String GET_MY_Supportes=SubBASEURL+"v1/team/techs/fan_page/";
    //Suggestions
    public final static String POST_Suggestions=SubBASEURL+"v1/feedback/";
    //bil car & motor
    public final static String POST_BillCar=SubBASEURL+"v1/bill/car/";
    public final static String POST_BillMotorcycle=SubBASEURL+"v1/bill/motorcycle/";
    public final static String POST_BillPayment=SubBASEURL+"v1/bill/traffic_payment/";

    public final static String BILL_PHONE=SubBASEURL+"v1/bill/phone/";
    public final static String BILL_PAYMENT=SubBASEURL+"v1/bill/bill_payment/";
    public final static String BILL_ELECTRICITY=SubBASEURL+"v1/bill/electricity/";
    public final static String BILL_GAZ=SubBASEURL+"v1/bill/gas/";
    public final static String BILL_MCI=SubBASEURL+"v1/bill/mci/";
    public final static String BILL_WATER=SubBASEURL+"v1/bill/water/";


}
