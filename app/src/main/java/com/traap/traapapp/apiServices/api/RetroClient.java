package com.traap.traapapp.apiServices.api;

import java.util.ArrayList;

import io.reactivex.Single;

import com.traap.traapapp.apiServices.helper.Const;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.GlobalResponse3;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.availableAmount.AvailableAmounResponse;
import com.traap.traapapp.apiServices.model.billCar.RequestBillCar;
import com.traap.traapapp.apiServices.model.billCar.ResponseBillCar;
import com.traap.traapapp.apiServices.model.billCode.BillCodeResponse;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityRequest;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityResponse;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneRequest;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneResponse;

import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoResponse;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.request.BuyPackageWalletRequest;
import com.traap.traapapp.apiServices.model.buyPackage.request.PackageBuyRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.apiServices.model.card.editCard.request.EditCardRequest;
import com.traap.traapapp.apiServices.model.card.getCardList.GetCardListResponse;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoResponse;
import com.traap.traapapp.apiServices.model.cup.CupResponse;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletRequest;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletResponse;
import com.traap.traapapp.apiServices.model.doTransferCard.request.DoTransferRequest;
import com.traap.traapapp.apiServices.model.doTransferCard.response.DoTransferResponse;
import com.traap.traapapp.apiServices.model.editUser.sendCodeReq.SendCodeReq;
import com.traap.traapapp.apiServices.model.editUser.sendCodeRes.SendCodeRes;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import com.traap.traapapp.apiServices.model.getAllComments.ResponseComments;
import com.traap.traapapp.apiServices.model.getAllCompations.ResponseAllCompations;
import com.traap.traapapp.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.ForgetPasswordWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.getBankList.response.BankListResponse;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import com.traap.traapapp.apiServices.model.getBoughtFor.GetBoughtForResponse;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;
import com.traap.traapapp.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import com.traap.traapapp.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;
import com.traap.traapapp.apiServices.model.getHistory.ResponseHistory;
import com.traap.traapapp.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import com.traap.traapapp.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.request.Last5PastMatchRequest;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchResponse;
import com.traap.traapapp.apiServices.model.getMenu.request.GetMenuRequest;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuResponse;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpResponse;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillResponse;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import com.traap.traapapp.apiServices.model.getQuestionCompat.ResponseGetQuestions;
import com.traap.traapapp.apiServices.model.getReport.request.GetReportRequest;
import com.traap.traapapp.apiServices.model.getReport.response.GetReportResponse;
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.apiServices.model.invite.InviteResponse;
import com.traap.traapapp.apiServices.model.inviteFriend.InviteFriendResponse;
import com.traap.traapapp.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import com.traap.traapapp.apiServices.model.league.getLeagues.response.ResponseLeage;
import com.traap.traapapp.apiServices.model.league.pastResult.request.RequestPastResult;
import com.traap.traapapp.apiServices.model.league.pastResult.response.ResponsePastResult;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.lottery.GetLotteryWinnerListResponse;
import com.traap.traapapp.apiServices.model.mainPage.MainPageResponse;
import com.traap.traapapp.apiServices.model.mainPhotos.MainPhotoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.apiServices.model.mySupportProfile.ResponseMySupport;
import com.traap.traapapp.apiServices.model.news.archive.NewsArchiveListByIdResponse;
import com.traap.traapapp.apiServices.model.media.category.MediaArchiveCategoryResponse;
import com.traap.traapapp.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.apiServices.model.news.details.putBookmark.response.NewsBookmarkResponse;
import com.traap.traapapp.apiServices.model.news.details.sendComment.request.SendCommentNewsRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.apiServices.model.payBillCar.RequestPayBillCar;
import com.traap.traapapp.apiServices.model.photo.archive.PhotoArchiveResponse;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.apiServices.model.photo.response.PhotosByIdResponse;
import com.traap.traapapp.apiServices.model.points.groupBy.PointsGroupByResponse;
import com.traap.traapapp.apiServices.model.points.guide.PointsGuideResponse;
import com.traap.traapapp.apiServices.model.points.records.PointsRecordResponse;
import com.traap.traapapp.apiServices.model.predict.getMyPredict.MyPredictResponse;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.GetPredictResponse;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import com.traap.traapapp.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import com.traap.traapapp.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;
import com.traap.traapapp.apiServices.model.getVersion.request.GetVersionRequest;
import com.traap.traapapp.apiServices.model.getVersion.response.GetVersionResponse;
import com.traap.traapapp.apiServices.model.login.LoginRequest;
import com.traap.traapapp.apiServices.model.login.LoginResponse;
import com.traap.traapapp.apiServices.model.matchList.MachListResponse;
import com.traap.traapapp.apiServices.model.mobileCharge.request.MobileChargeRequest;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchResponse;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import com.traap.traapapp.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import com.traap.traapapp.apiServices.model.profile.deleteProfile.DeleteProfileResponse;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.apiServices.model.profile.putProfile.request.SendProfileRequest;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationRequest;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationResponse;
import com.traap.traapapp.apiServices.model.sendComment.RequestSendComment;
import com.traap.traapapp.apiServices.model.setAnswerQuestions.RequestSetAnswer;
import com.traap.traapapp.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import com.traap.traapapp.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;
import com.traap.traapapp.apiServices.model.spectatorInfo.GetSpectatorListResponse;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;
import com.traap.traapapp.apiServices.model.suggestions.RequestSuggestions;
import com.traap.traapapp.apiServices.model.suggestions.ResponseSuggestions;
import com.traap.traapapp.apiServices.model.survey.SurveyDetailResponse;
import com.traap.traapapp.apiServices.model.techHistory.GetTechsHistoryResponse;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.apiServices.model.techs.RequestSetFavoritePlayer;
import com.traap.traapapp.apiServices.model.techs.news.GetTechNewsResponse;
import com.traap.traapapp.apiServices.model.topPlayers.TopPlayerResponse;
import com.traap.traapapp.apiServices.model.survey.listSurvey.SurveyListResponse;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyRequest;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyResponse;
import com.traap.traapapp.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import com.traap.traapapp.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;
import com.traap.traapapp.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import com.traap.traapapp.apiServices.model.tractorTeam.TractorTeamResponse;
import com.traap.traapapp.apiServices.model.verify.VerifyRequest;
import com.traap.traapapp.apiServices.model.verify.VerifyResponse;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletRequest;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;

import lombok.Generated;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET(Const.GetContactInfo)
    Single<Response<WebServiceClass<GetContactInfoResponse>>> getContactInfo();

    /*videos*/
    @GET(Const.Get_Main_Video)
    Single<Response<WebServiceClass<MainVideosResponse>>> getMainVideos();

    @GET(Const.Get_VIDEOS_ARCHIVE_CATEGORY)
    Single<Response<WebServiceClass<MediaArchiveCategoryResponse>>> getVideosArchiveCategory();

    @GET(Const.Get_Category_By_Id_Video)
    Single<Response<WebServiceClass<CategoryByIdVideosResponse>>> getCategoryByIdVideos(
            //  @Path("id") Integer categoryId
            @Query("page") Integer page,
            @Query("page_size") Integer page_size,
            @Query("category_id") Integer category_id
    );

    @GET(Const.Get_Category_By_Id_Video2)
    Single<Response<WebServiceClass<CategoryByIdVideosResponse>>> getCategoryByIdVideos2(
            @Path("id") Integer categoryId
    );

    @POST(Const.Like_Video)
    Single<Response<WebServiceClass<LikeVideoResponse>>> likeVideo(
            @Path("id") Integer videoId
    );


    @GET(Const.Get_VIDEOS_ARCHIVE_BY_IDs)
    Single<Response<WebServiceClass<ArchiveVideoResponse>>> getVideosArchive(
            @Query("category_id") String categoryId
    );

    @GET(Const.Get_VIDEOS_ARCHIVE_BY_IDs)
    Single<Response<WebServiceClass<ArchiveVideoResponse>>> getVideosArchiveCategoryByIds(
            @Query("category_id__in") String categoryIds,          //example 1,2,3,4,
            @Query("create_date__gte") String dateFrom,           //example 2019-01-01 and ""
            @Query("create_date__lte") String dateTo,             //example 2019-12-01 and ""
            @Query("search") String searchText
    );


    /*survey*/
    @GET(Const.Get_DETAIL_SURVEY)
    Single<Response<WebServiceClass<SurveyDetailResponse>>> getSurveyDetail(
            @Path("id") Integer surveyId);

    @GET(Const.TECHS)
    Single<Response<WebServiceClass<TopPlayerResponse>>> getTech(
            @Query("role__code_name") String role__code_name,
            @Query("is_present") Boolean is_present,
            @Query("tech_staff__is_featured") Boolean tech_staff__is_featured
    );

    @GET(Const.TECHS)
    Single<Response<WebServiceClass<TopPlayerResponse>>> getSearchTech(
            @Query("search") String role__code_name);

    @PUT(Const.Get_DETAIL_SURVEY)
    Single<Response<WebServiceClass<PutSurveyResponse>>> putSurvey(
            @Path("id") Integer surveyId,
            @Body PutSurveyRequest request);

    @GET(Const.Get_LIST_SURVEY)
    Single<Response<WebServiceClass<SurveyListResponse>>> getSurveyList(
    );

    /*Compations*/
    @GET(Const.Get_All_Compations)
    Single<Response<WebServiceClass<ResponseAllCompations>>> getAllCompations();

    /*photos*/
    @GET(Const.Get_Main_Photo)
    Single<Response<WebServiceClass<MainPhotoResponse>>> getMainPhotos();

    @GET(Const.Get_Category_By_Id_Photo)
    Single<Response<WebServiceClass<CategoryByIdVideosResponse>>> getCategoryByIdPhotos(
            @Path("id") Integer categoryId
    );

    @GET(Const.Get_Category_By_Id_Photo2)
    Single<Response<WebServiceClass<CategoryByIdVideosResponse>>> getCategoryByIdPhotos2(
            @Path("id") Integer categoryId
    );

    @GET(Const.Get_Photos_By_Id)
    Single<Response<WebServiceClass<PhotosByIdResponse>>> getPhotosById(
            @Path("id") Integer categoryId
    );

    @GET(Const.List_Bookmark_Photo)
    Single<Response<WebServiceClass<PhotoArchiveResponse>>> getListBookmarkPhotos();

    @GET(Const.List_Bookmark_Video)
    Single<Response<WebServiceClass<ArchiveVideoResponse>>> getListBookmarkVideos();

    @POST(Const.Like_Photo)
    Single<Response<WebServiceClass<LikeVideoResponse>>> likePhoto(
            @Path("id") Integer videoId
    );

    @POST(Const.bookMark_Photo)
    Single<Response<WebServiceClass<BookMarkPhotoResponse>>> bookMarkPhoto(
            @Path("id") Integer photoId
    );

    @GET(Const.Get_Photo_Detail)
    Single<Response<WebServiceClass<Content>>> getPhotoDetail(
            @Path("id") Integer photoId
    );

    @POST(Const.bookMark_Video)
    Single<Response<WebServiceClass<BookMarkPhotoResponse>>> bookMarkVideo(
            @Path("id") Integer videoId
    );

    @GET(Const.GetMenuHelp)
    Single<Response<WebServiceClass<GetMenuHelpResponse>>> getMenuHelp();

    @GET(Const.BANK_LIST)
    Single<Response<WebServiceClass<BankListResponse>>> getBankList();


    @POST(Const.GetAllBoxes)
    Single<Response<WebServiceClass<GetAllBoxesResponse>>> getAllBoxes(
            @Body GetAllBoxesRequest request
    );
     @POST(Const.POST_BillCar)
    Single<Response<WebServiceClass<ResponseBillCar>>> getAllBillCar(
            @Body RequestBillCar request
    );

    @POST(Const.POST_BillMotorcycle)
    Single<Response<WebServiceClass<ResponseBillCar>>> getAllBillMotor(
            @Body RequestBillCar request
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

    @GET(Const.GetTicketBuyEnable)
    Single<Response<WebServiceClass<MatchItem>>> getTicketBuyEnable();


    @POST(Const.Verify)
    Single<Response<WebServiceClass<VerifyResponse>>> verify(
            @Body VerifyRequest request
    );


    @POST(Const.GET_VERSION)
    Single<Response<WebServiceClass<GetVersionResponse>>> getVersion(
            @Body GetVersionRequest request
    );


    @GET(Const.GET_BOUGHT_FOR)
    Single<Response<WebServiceClass<GetBoughtForResponse>>> getBoughtFor();

    @POST(Const.BUY_MOBILE_CHARGE)
    Single<Response<WebServiceClass<MobileChargeResponse>>> getMobileCharge(
            @Body MobileChargeRequest request);

    @POST(Const.BUY_CHARGE_WALLET)
    Single<Response<WebServiceClass<BuyChargeWalletResponse>>> buyChargeWallet(
            @Body BuyChargeWalletRequest request);

    @POST(Const.BUY_PACKAGE_WALLET)
    Single<Response<WebServiceClass<BuyPackageWalletResponse>>> buyPackageWallet(
            @Body BuyPackageWalletRequest request);

    @POST(Const.Get_Withdraw_Wallet)
    Single<Response<WebServiceClass<WithdrawWalletResponse>>> withdrawWallet(
            @Body WithdrawWalletRequest request);

    @POST(Const.GET_League)
    Single<Response<WebServiceClass<ResponseLeage>>> getLeage(
            @Body GetLeagueRequest request);

    @POST(Const.Get_Past_Result)
    Single<Response<WebServiceClass<ResponsePastResult>>> getPastResult(
            @Body RequestPastResult request);


    @POST(Const.Get_Past_Result_v2)
    Single<Response<WebServiceClass<Last5PastMatchResponse>>> getPastResult_v2(
            @Body Last5PastMatchRequest request);


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


//    @POST(Const.HotelSendMessage)
//    Single<Response<WebServiceClass<GlobalResponse>>> hotelSendMessage(
//            @Body HotelSendMessageRequest request
//    );

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

    @POST(Const.GetBalancePasswordLess)
    Single<Response<WebServiceClass<GetBalancePasswordLessResponse>>> getBalancePasswordLess(
            @Body GetBalancePasswordLessRequest request
    );

    @POST(Const.GetInfoWallet)
    Single<Response<WebServiceClass<GetInfoWalletResponse>>> getInfoWallet(
            @Body GetBalancePasswordLessRequest request
    );

    @POST(Const.IncreaseInventoryWallet)
    Single<Response<WebServiceClass<ResponseIncreaseWallet>>> getIncInvWallet(
            @Body RequestIncreaseWallet request
    );

    @POST(Const.DoTransferWallet)
    Single<Response<WebServiceClass<DoTransferWalletResponse>>> doTransferWallet(
            @Body DoTransferWalletRequest request
    );

    @POST(Const.ForgetPasswordWallet)
    Single<Response<WebServiceClass<ForgetPasswordWalletResponse>>> forgetPasswordWallet(
            @Body GetBalancePasswordLessRequest request
    );

    @POST(Const.ChangePasswordWallet)
    Single<Response<WebServiceClass<ForgetPasswordWalletResponse>>> changePasswordWallet(
            @Body GetBalancePasswordLessRequest request
    );

    @POST(Const.PAYMENT_PRINT_pOS)
    Single<Response<WebServiceClass<PaymentPrintPosResponse>>> getPayment(
            @Body PaymentPrintPosRequest request
    );


    @GET(Const.GET_Match_List)
    Single<Response<WebServiceClass<MachListResponse>>> getMatchList();

    @GET(Const.GET_TYPE_Transaction_List)
    Single<Response<WebServiceClass<ArrayList<TypeCategory>>>> getTypeTransactionList();

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionList();

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionListByFilter(
            @Query("type_transaction__id__in") String typeTransactionIds,          //example 1,2,3,4,
            @Query("amount__gte") Integer priceFrom,                               //example 1000 and ""
            @Query("amount__lte") Integer priceTo,                                 //example 1000 and ""
            @Query("create_date__gte") String dateFrom,                            //example 2019-01-01 and ""
            @Query("create_date__lte") String dateTo,                              //example 2019-12-01 and ""
            @Query("status") Boolean status                                       //example 0=All, 1=Success, 2=Failure
//            @Query("id__contains") String searchText
    );

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionListByFilterForAllStatus(
            @Query("type_transaction__id__in") String typeTransactionIds,          //example 1,2,3,4,
            @Query("amount__gte") Integer priceFrom,                               //example 1000 and ""
            @Query("amount__lte") Integer priceTo,                                 //example 1000 and ""
            @Query("create_date__gte") String dateFrom,                            //example 2019-01-01 and ""
            @Query("create_date__lte") String dateTo                              //example 2019-12-01 and ""
//            @Query("id__contains") String searchText
    );

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionListBySearch(
            @Query("type_transaction__title__contains") String searchText
    );


    @GET(Const.GET_TRANSACTION_DETAIL)
    Single<Response<WebServiceClass<TransactionDetailResponse>>> getTransactionDetail(
            @Path("id") Long transactionId
    );


    @GET(Const.Archive_Photo)
    Single<Response<WebServiceClass<PhotoArchiveResponse>>> getArchivePhotos(
            @Query("category_id") String category_id
    );

    @GET(Const.Archive_Photo)
    Single<Response<WebServiceClass<PhotoArchiveResponse>>> getArchivePhotosByIds(
            @Query("category_id__in") String categoryIds,          //example 1,2,3,4,
            @Query("publish_date__gte") String dateFrom,           //example 2019-01-01 and ""
            @Query("publish_date__lte") String dateTo,             //example 2019-12-01 and ""
            @Query("search") String searchText
    );


    @GET(Const.GET_CATEGORY_ARCHIVE_PHOTO)
    Single<Response<WebServiceClass<MediaArchiveCategoryResponse>>> getPhotosArchiveCategory();


    @GET(Const.GetHistory)
    Single<Response<WebServiceClass<ResponseHistory>>> getHistory();


    @GET(Const.GET_PREDICT + "{matchId}/")
    Single<Response<WebServiceClass<GetPredictResponse>>> getPredict(
            @Path("matchId") Integer matchId
    );


    @GET(Const.GET_MY_PREDICTS)
    Single<Response<WebServiceClass<MyPredictResponse>>> getMyPredict();

    @GET(Const.GET_MY_Supportes)
    Single<Response<WebServiceClass<ResponseMySupport>>> getMySupports();


    @GET(Const.GET_PREDICT_ENABLE)
    Single<Response<WebServiceClass<MatchItem>>> getPredictEnable();


    @POST(Const.SEND_PREDICT)
    Single<Response<WebServiceClass<Object>>> sendPredict(
            @Body SendPredictRequest request
    );

    @GET(Const.GET_RULES + "{id}/stadium_rules/")
    Single<Response<WebServiceClass<ResponseStadiumRules>>> getRulsStadium(
            @Path("id") Integer id
    );


    @GET(Const.GetSpectatorInfo)
    Single<Response<WebServiceClass<SpectatorInfoResponse>>> getSpectatorInfo(
            @Path("national_code") String nationalCode
    );

    @GET(Const.GetSpectatorList)
    Single<Response<WebServiceClass<GetSpectatorListResponse>>> getSpectatorList();

    @GET(Const.GET_PROFILE)
    Single<Response<WebServiceClass<GetProfileResponse>>> getProfile();

    @GET(Const.GET_Invite)
    Single<Response<WebServiceClass<InviteResponse>>> getInvite();

    //    @Multipart
//    @FormUrlEncoded
    @PUT(Const.PUT_PROFILE)
    Single<Response<WebServiceClass<SendProfileResponse>>> sendProfile(
            @Body SendProfileRequest request
//            @Part MultipartBody.Part ImageFile
    );

    @Multipart
    @POST(Const.SEND_PROFILE_PHOTO)
    Single<Response<WebServiceClass<Object>>> sendProfilePhoto(
            @Part MultipartBody.Part ImageFile
    );

    @DELETE(Const.DELETE_PROFILE_PHOTO)
    Single<Response<WebServiceClass<DeleteProfileResponse>>> deleteProfilePhoto();

    @DELETE(Const.DELETE_PROFILE_Send_Code)
    Single<Response<WebServiceClass<DeleteProfileResponse>>> deleteProfileSendCode();

    @GET(Const.Get_NEWS_ARCHIVE_CATEGORY)
    Single<Response<WebServiceClass<MediaArchiveCategoryResponse>>> getNewsArchiveCategory();

    @GET(Const.NEWS_MAIN)
    Single<Response<WebServiceClass<NewsMainResponse>>> getNewsMain();

    @GET(Const.Get_NEWS_ARCHIVE_BY_IDs)
    Single<Response<WebServiceClass<NewsArchiveListByIdResponse>>> getNewsArchiveCategoryById(
            @Query("category") String categoryId           //example 1
    );

    @GET(Const.Get_NEWS_ARCHIVE_BY_IDs)
    Single<Response<WebServiceClass<NewsArchiveListByIdResponse>>> getNewsArchiveCategoryByIds(
            @Query("category_id__in") String categoryIds,          //example 1,2,3,4,
            @Query("publish_date__gte") String dateFrom,           //example 2019-01-01 and ""
            @Query("publish_date__lte") String dateTo,             //example 2019-12-01 and ""
            @Query("search") String searchText
    );

    //    @GET(Const.Get_NEWS_ARCHIVE_BY_IDs_AND_DATES)
//    Single<Response<WebServiceClass<NewsArchiveListByIdResponse>>> getNewsArchiveCategoryByIdsAndRangeDate(
//            @Query("category_id__in") String categoryIds,          //example 1,2,3,4
//            @Query("create_date__range") String createDateRanges   //example 2019-01-01,2019-12-01
//    );
    @POST(Const.Get_verify_change_user)
    Single<Response<WebServiceClass<com.traap.traapapp.apiServices.model.editUser.verifyRes.VerifyResponse>>> editUserVerify(
            @Body com.traap.traapapp.apiServices.model.editUser.verifyReq.VerifyRequest request
    );

    @POST(Const.DELETE_PROFILE_Verify_Code)
    Single<Response<WebServiceClass<com.traap.traapapp.apiServices.model.editUser.verifyRes.VerifyResponse>>> deleteUserVerifyCode(
            @Body com.traap.traapapp.apiServices.model.editUser.verifyReq.VerifyRequest request
    );

    @POST(Const.Get_send_code_change_user)
    Single<Response<WebServiceClass<SendCodeRes>>> sendCodeEditUser(
            @Body SendCodeReq request
    );

    @GET(Const.Get_NEWS_DETAILS + "{id}/")
    Single<Response<WebServiceClass<GetNewsDetailsResponse>>> getNewsDetails(
            @Path("id") Integer id
    );

    @POST(Const.NEWS_DETAILS_LIKE + "{id}/like/")
    Single<Response<WebServiceClass<LikeNewsDetailResponse>>> likeNews(
            @Path("id") Integer id,
            @Body LikeNewsDetailRequest request
    );

    @POST(Const.NEWS_DETAILS_SEND_COMMENT + "{id}/comments/")
    Single<Response<WebServiceClass<Object>>> sendNewsComment(
            @Path("id") Integer id,
            @Body SendCommentNewsRequest request
    );

    @GET(Const.NEWS_DETAILS_GET_COMMENT + "{id}/comments/")
    Single<Response<WebServiceClass<ArrayList<GetNewsCommentResponse>>>> getNewsComment(
            @Path("id") Integer id
    );

    @POST(Const.NEWS_DETAILS_LIKE_COMMENT + "comments/{id}/rate/")
    Single<Response<WebServiceClass<LikeNewsDetailResponse>>> likeNewsComment(
            @Path("id") Integer id,
            @Body LikeNewsDetailRequest request
    );

    @POST(Const.NEWS_DETAILS_SET_BOOKMARK + "{id}/bookmark/")
    Single<Response<WebServiceClass<NewsBookmarkResponse>>> bookmarkNews(
            @Path("id") Integer id
    );

    @GET(Const.NEWS_DETAILS_GET_BOOKMARK)
    Single<Response<WebServiceClass<NewsArchiveListByIdResponse>>> getNewsBookmarks();

    @GET(Const.AvailableAmount)
    Single<Response<WebServiceClass<AvailableAmounResponse>>> getAvailableAmount();

    @GET(Const.GET_POINTS_RECORD)
    Single<Response<WebServiceClass<PointsRecordResponse>>> getPointRecord();

    @GET(Const.GET_POINTS_GUIDE)
    Single<Response<WebServiceClass<PointsGuideResponse>>> getPointGuide();

    @GET(Const.GET_POINTS_GROUP_BY)
    Single<Response<WebServiceClass<PointsGroupByResponse>>> getPointGroupBy();

//    @GET(Const.SETTING)
//    Single<Response<WebServiceClass<SettingResponse>>> getSetting();

    @GET(Const.mainpage)
    Single<Response<WebServiceClass<MainPageResponse>>> mainpage();


    @POST(Const.GetReport)
    Single<Response<WebServiceClass<GetReportResponse>>> getReport(
            @Body GetReportRequest request
    );


    @GET(Const.Get_Invite_Friend)
    Single<Response<WebServiceClass<InviteFriendResponse>>> getInviteFriend();

    @GET(Const.GET_LOTTERY_WINNER_LIST)
    Single<Response<WebServiceClass<GetLotteryWinnerListResponse>>> getLotteryWinnerList(
            @Path("matchId") Integer id
    );

    @GET(Const.traktor)
    Single<Response<WebServiceClass<TractorTeamResponse>>> traktor();

    @GET(Const.CUPS)
    Single<Response<WebServiceClass<CupResponse>>> cup();

    @GET(Const.TECHS_ID)
    Single<Response<WebServiceClass<GetTechsIdResponse>>> getTechsId(
            @Path("id") Integer id);

    @GET(Const.Get_Comments)
    Single<Response<WebServiceClass<ResponseComments>>> getCommentsTechsId(
            @Path("id") Integer id);


    @POST(Const.post_Comment)
    Single<Response<WebServiceClass<ResponseComments>>> postCommentTechsId(
            @Path("id") Integer techId,
            @Body RequestSendComment request);

    @POST(Const.post_Reply)
    Single<Response<WebServiceClass<ResponseComments>>> postReplyId(
            @Path("id") Integer techId,
            @Body RequestSendComment request);



    @GET(Const.TECHS_HISTORY)
    Single<Response<WebServiceClass<GetTechsHistoryResponse>>> getTechsHistory(
            @Path("id") Integer id);

    @POST(Const.Post_Favorite)
    Single<Response<WebServiceClass<GetTechsIdResponse>>> potFavoritPlayer(
            @Body RequestSetFavoritePlayer request);

    @POST(Const.Post_Answer)
    Single<Response<WebServiceClass<PutSurveyResponse>>> putAnswerQu(
            @Path("id") Integer surveyId,
            @Body RequestSetAnswer request);


    @GET(Const.Get_All_Questions)
    Single<Response<WebServiceClass<ResponseGetQuestions>>> getAllQuestions(
            @Path("id") Integer id);

    @GET(Const.Get_Tech_News)
    Single<Response<WebServiceClass<GetTechNewsResponse>>> getTechNews(
            @Path("id") Integer id);
    @POST(Const.POST_Suggestions)
    Single<Response<WebServiceClass<ResponseSuggestions>>> postSuggestions(
            @Body RequestSuggestions request
    );

    @POST(Const.BILL_PHONE)
    Single<Response<WebServiceClass<BillPhoneResponse>>> postBillPhone(
            @Body BillPhoneRequest request
    );

    @POST(Const.BILL_PAYMENT)
    Single<Response<WebServiceClass<BillPaymentResponse>>> postBillPayment(
            @Body BillPaymentRequest request
    );

    @POST(Const.POST_BillPayment)
    Single<Response<WebServiceClass<BillPaymentResponse>>> postBillCarPayment(
            @Body RequestPayBillCar request
    );

    @POST(Const.BILL_ELECTRICITY)
    Single<Response<WebServiceClass<BillElectricityResponse>>> postBillElectricity(
            @Body BillElectricityRequest request
    );

    @POST(Const.BILL_GAZ)
    Single<Response<WebServiceClass<BillCodeResponse>>> postBillGaz(
            @Body BillPhoneRequest request
    );
    @POST(Const.BILL_MCI)
Single<Response<WebServiceClass<BillPhoneResponse>>> postBillMci(
                @Body BillPhoneRequest request
        );

    @POST(Const.BILL_WATER)
Single<Response<WebServiceClass<BillCodeResponse>>> postBillWater(
                @Body BillPhoneRequest request
        );

}
