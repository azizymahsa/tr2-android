package ir.traap.tractor.android.apiServices.api;

import io.reactivex.Single;
import ir.traap.tractor.android.apiServices.helper.Const;
import ir.traap.tractor.android.apiServices.model.GlobalResponse;
import ir.traap.tractor.android.apiServices.model.GlobalResponse2;
import ir.traap.tractor.android.apiServices.model.GlobalResponse3;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoResponse;
import ir.traap.tractor.android.apiServices.model.billPayment.request.BillPaymentRequest;
import ir.traap.tractor.android.apiServices.model.billPayment.response.BillPaymentResponse;
import ir.traap.tractor.android.apiServices.model.buyPackage.request.PackageBuyRequest;
import ir.traap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.traap.tractor.android.apiServices.model.card.Result;
import ir.traap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import ir.traap.tractor.android.apiServices.model.card.editCard.request.EditCardRequest;
import ir.traap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.traap.tractor.android.apiServices.model.doTransferCard.response.DoTransferResponse;
import ir.traap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.traap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import ir.traap.tractor.android.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import ir.traap.tractor.android.apiServices.model.getBankList.response.BankListResponse;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.traap.tractor.android.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import ir.traap.tractor.android.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;
import ir.traap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.traap.tractor.android.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import ir.traap.tractor.android.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import ir.traap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import ir.traap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;
import ir.traap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.traap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.traap.tractor.android.apiServices.model.getMenuHelp.GetMenuHelpResponse;
import ir.traap.tractor.android.apiServices.model.getMyBill.GetMyBillResponse;
import ir.traap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.traap.tractor.android.apiServices.model.getTransaction.ResponseTransaction;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.response.ResponseLeage;
import ir.traap.tractor.android.apiServices.model.league.pastResult.request.RequestPastResult;
import ir.traap.tractor.android.apiServices.model.league.pastResult.response.ResponsePastResult;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideosResponse;
import ir.traap.tractor.android.apiServices.model.news.archive.response.NewsArchiveListByIdResponse;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import ir.traap.tractor.android.apiServices.model.predict.getPredict.response.GetPredictResponse;
import ir.traap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import ir.traap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.traap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;
import ir.traap.tractor.android.apiServices.model.getVersion.request.GetVersionRequest;
import ir.traap.tractor.android.apiServices.model.getVersion.response.GetVersionResponse;
import ir.traap.tractor.android.apiServices.model.login.LoginRequest;
import ir.traap.tractor.android.apiServices.model.login.LoginResponse;
import ir.traap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.traap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.traap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import ir.traap.tractor.android.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import ir.traap.tractor.android.apiServices.model.profile.getProfile.response.GetProfileResponse;
import ir.traap.tractor.android.apiServices.model.profile.putProfile.request.SendProfileRequest;
import ir.traap.tractor.android.apiServices.model.profile.putProfile.response.SendProfileResponse;
import ir.traap.tractor.android.apiServices.model.reservationmatch.ReservationRequest;
import ir.traap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;
import ir.traap.tractor.android.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import ir.traap.tractor.android.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;
import ir.traap.tractor.android.apiServices.model.stadium_rules.ResponseStadiumRules;
import ir.traap.tractor.android.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import ir.traap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;
import ir.traap.tractor.android.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;
import ir.traap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import ir.traap.tractor.android.apiServices.model.verify.VerifyRequest;
import ir.traap.tractor.android.apiServices.model.verify.VerifyResponse;
import ir.traap.tractor.android.apiServices.model.paymentWallet.ResponsePaymentWallet;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroClient
{

    @POST(Const.Login)
    Single<Response<WebServiceClass<LoginResponse>>> login(
            @Body LoginRequest request
    );


    @GET(Const.GetMyBills)
    Single<Response<WebServiceClass<GetMyBillResponse>>> getMyBills();

    @GET(Const.Get_Main_Video)
    Single<Response<WebServiceClass<MainVideosResponse>>> getMainVideos();

    @GET(Const.Get_Category_By_Id_Video)
    Single<Response<WebServiceClass<CategoryByIdVideosResponse>>> getCategoryByIdVideos(
            @Path("id") Integer categoryId
            );

    @POST(Const.Like_Video)
    Single<Response<WebServiceClass<LikeVideoResponse>>> likeVideo(
            @Path("id") Integer videoId
    );

    @GET(Const.Archive_Video)
    Single<Response<WebServiceClass<ArchiveVideoResponse>>> getArchiveVideos();

    @GET(Const.GetMenuHelp)
    Single<Response<WebServiceClass<GetMenuHelpResponse>>> getMenuHelp();

    @GET(Const.BANK_LIST)
    Single<Response<WebServiceClass<BankListResponse>>> getBankList();


    @POST(Const.GetAllBoxes)
    Single<Response<WebServiceClass<GetAllBoxesResponse>>> getAllBoxes(
            @Body GetAllBoxesRequest request
    );

    @POST(Const.ReservationMatch)
    Single<Response<WebServiceClass<ReservationResponse>>> reservTicket(
            @Body ReservationRequest request
    );

    @POST(Const.PaymentMatch)
    Single<Response<WebServiceClass<PaymentMatchResponse>>> paymentMatch(
            @Body PaymentMatchRequest request
    );

    @POST(Const.PaymentWallet)
    Single<Response<WebServiceClass<ResponsePaymentWallet>>> paymentWallet(
            @Body PaymentMatchRequest request
    );



    @POST(Const.GetBillCodePayCode)
    Single<Response<WebServiceClass<GetBillCodePayCodeResponse>>> getBillCodePayCode(
            @Body GetBillCodePayCodeRequest request
    );

    @POST(Const.GetTicketInfo)
    Single<Response<WebServiceClass<GetTicketInfoResponse>>> getTicketInfo(
            @Body GetTicketInfoRequest request
    );


    @POST(Const.Verify)
    Single<Response<WebServiceClass<VerifyResponse>>> verify(
            @Body VerifyRequest request
    );


    @POST(Const.GET_VERSION)
    Single<Response<WebServiceClass<GetVersionResponse>>> getVersion(
            @Body GetVersionRequest request
    );


    @POST(Const.BUY_MOBILE_CHARGE)
    Single<Response<WebServiceClass<MobileChargeResponse>>> getMobileCharge(
            @Body MobileChargeRequest request);

    @POST(Const.GET_Leage)
    Single<Response<WebServiceClass<ResponseLeage>>> getLeage(
            @Body GetLeagueRequest request);

    @POST(Const.Get_Past_result)
    Single<Response<WebServiceClass<ResponsePastResult>>> getPastResult(
            @Body RequestPastResult request);

    @POST(Const.GET_PACKAGE_IRANCELL)
    Single<Response<WebServiceClass<GetPackageIrancellResponse>>> getIrancellPackage(
            @Body GetPackageMciRequest request
    );


    @POST(Const.GetMenu)
    Single<Response<WebServiceClass<GetMenuResponse>>> getMenu(
            @Body GetMenuRequest request
    );


    @POST(Const.GetMenuAll)
    Single<Response<WebServiceClass<GetAllMenuResponse>>> getMenuAll(
            @Body GetMenuRequest request
    );


    @GET(Const.GetCardList)
    Single<Response<WebServiceClass<GetCardListResponse>>> getCardList();


    @POST(Const.AddCard)
    Single<Response<WebServiceClass<Result>>> addCard(
            @Body AddCardRequest request
    );


    @DELETE(Const.DeleteCard + "{id}/")
    Single<Response<WebServiceClass<Object>>> deleteCard(
            @Path("id") Integer cardId
    );


    @PUT(Const.EditCard + "{id}/")
    Single<Response<WebServiceClass<Result>>> editCard(
            @Path("id") Integer cardId,
            @Body EditCardRequest request
    );


    @POST(Const.GET_PACKAGE_RIGHTEL)
    Single<Response<WebServiceClass<GetRightelPackRespone>>> getRightelPackage(
            @Body GetPackageMciRequest friendRequest
    );


    @POST(Const.GET_PACKAGE_MCI)
    Single<Response<WebServiceClass<GetPackageMciResponse>>> getPackageMci(
            @Body GetPackageMciRequest request
    );


    @POST(Const.BUY_MOBILE_PACKAGE)
    Single<Response<WebServiceClass<PackageBuyResponse>>> buySimcardPackage(
            @Body PackageBuyRequest mciPackageBuyRequest
    );


    @POST(Const.GetInfoPhoneBill)
    Single<Response<WebServiceClass<GetInfoPhoneBillResponse>>> getInfoPhoneBill(
            @Body GetInfoPhoneBillRequest request
    );


    @POST(Const.BillPayment)
    Single<Response<WebServiceClass<BillPaymentResponse>>> billPayment(
            @Body BillPaymentRequest request
    );

    @POST(Const.GetInfoBill)
    Single<Response<WebServiceClass<GetInfoBillResponse>>> getInfoBill(
            @Body GetInfoBillRequest request
    );


    @POST(Const.GetHappyCardInfo)
    Single<Response<WebServiceClass<GetHappyCardInfoResponse>>> getHappyCardInfo(
            @Body GetHappyCardInfoRequest request
    );


    @POST(Const.FORGOT_PASS2)
    Single<Response<WebServiceClass<Object>>> doForgotPass(
            @Body ShetacForgotPass2Request request
    );


    @POST(Const.CHANGE_PASS2)
    Single<Response<WebServiceClass<Object>>> doChangePass(
            @Body ShetacChangePass2Request request
    );


    @POST(Const.GetShetabCardInfo)
    Single<Response<WebServiceClass<ShetabCardInfoResponse>>> getShetabCardInfo(
            @Body ShetabCardInfoRequest request
    );


    @POST(Const.DoTransferCard)
    Single<Response<WebServiceClass<DoTransferResponse>>> doTransferCard(
            @Body DoTransferRequest request
    );


    @POST(Const.BusPayment)
    Single<Response<WebServiceClass<GlobalResponse3>>> busBooking(
            @Body RequestBusPayment request
    );


    @POST(Const.GetBusUserPass)
    Single<Response<WebServiceClass<GetUserPassResponse>>> getBusUserPass();


    @POST(Const.SendBusMessage)
    Single<Response<WebServiceClass<GlobalResponse3>>> busSendMessage(
            @Body BusSendMessage request
    );


    @POST(Const.FlightPayment)
    Single<Response<WebServiceClass<GlobalResponse2>>> flightPayment(
            @Body FlightPaymentRequest request
    );


    @POST(Const.FlightSendMessage)
    Single<Response<WebServiceClass<GlobalResponse2>>> flightSendMessage(
            @Body FlightPaymentRequest request
    );


    @POST(Const.FlightGetUserPass)
    Single<Response<WebServiceClass<GetUserPassResponse>>> getFlightUserPass();


    @POST(Const.HotelSendMessage)
    Single<Response<WebServiceClass<GlobalResponse>>> hotelSendMessage(
            @Body HotelSendMessageRequest request
    );

    @POST(Const.HotelGetUserPass)
    Single<Response<WebServiceClass<GetUserPassResponse>>> getHotelUserPass();


    @POST(Const.HotelPayment)
    Single<Response<WebServiceClass<GlobalResponse2>>> doHotelPayment(
            @Body GdsHotelPaymentRequest gdsHotelPaymentRequest
    );


    @POST(Const.DECRYPTQRCODE)
    Single<Response<WebServiceClass<DecryptQrResponse>>> decryptQr(
            @Body DecryptQrRequest request
    );


    @POST(Const.PAYMENT_PRINT_pOS)
    Single<Response<WebServiceClass<PaymentPrintPosResponse>>> getPayment(
            @Body PaymentPrintPosRequest request
    );


    @GET(Const.GET_Match_List)
    Single<Response<WebServiceClass<MachListResponse>>> getMatchList();

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionList();


    @GET(Const.GetHistory)
    Single<Response<WebServiceClass<ResponseHistory>>> getHistory();


    @GET(Const.GET_PREDICT + "{matchId}/")
    Single<Response<WebServiceClass<GetPredictResponse>>> getPredict(
            @Path("matchId") Integer matchId
    );

    @POST(Const.SEND_PREDICT)
    Single<Response<WebServiceClass<Object>>> sendPredict(
            @Body SendPredictRequest request
    );

    @GET(Const.GET_RULES +"{id}/stadium_rules/")
    Single<Response<WebServiceClass<ResponseStadiumRules>>> getRulsStadium(
            @Path("id") Integer id
    );

    @GET(Const.GET_PROFILE)
    Single<Response<WebServiceClass<GetProfileResponse>>> getProfile();

    @PUT(Const.PUT_PROFILE)
    Single<Response<WebServiceClass<SendProfileResponse>>> sendProfile(
            @Body SendProfileRequest request
    );

    @GET(Const.Get_NEWS_ARCHIVE_CATEGORY)
    Single<Response<WebServiceClass<NewsArchiveCategoryResponse>>> getNewsArchiveCategory();

    @GET(Const.Get_NEWS_ARCHIVE_BY_ID)
    Single<Response<WebServiceClass<NewsArchiveListByIdResponse>>> getNewsArchiveCategoryById(
            @Query("category") String categoryId
    );


}
