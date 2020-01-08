package com.traap.traapapp.apiServices.api;

import java.util.ArrayList;

import io.reactivex.Single;

import com.traap.traapapp.apiServices.helper.Const;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.GlobalResponse3;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.billPayment.request.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.response.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoResponse;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.request.PackageBuyRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.apiServices.model.card.editCard.request.EditCardRequest;
import com.traap.traapapp.apiServices.model.card.getCardList.GetCardListResponse;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoResponse;
import com.traap.traapapp.apiServices.model.doTransferCard.request.DoTransferRequest;
import com.traap.traapapp.apiServices.model.doTransferCard.response.DoTransferResponse;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesResponse;
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
import com.traap.traapapp.apiServices.model.getMenu.request.GetMenuRequest;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuResponse;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpResponse;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillResponse;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;
import com.traap.traapapp.apiServices.model.invite.InviteResponse;
import com.traap.traapapp.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import com.traap.traapapp.apiServices.model.league.getLeagues.response.ResponseLeage;
import com.traap.traapapp.apiServices.model.league.pastResult.request.RequestPastResult;
import com.traap.traapapp.apiServices.model.league.pastResult.response.ResponsePastResult;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.mainPhotos.MainPhotoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.news.archive.NewsArchiveListByIdResponse;
import com.traap.traapapp.apiServices.model.media.category.MediaArchiveCategoryResponse;
import com.traap.traapapp.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.apiServices.model.news.details.putBookmark.response.NewsBookmarkResponse;
import com.traap.traapapp.apiServices.model.news.details.sendComment.request.SendCommentNewsRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.apiServices.model.photo.archive.PhotoArchiveResponse;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.apiServices.model.photo.response.PhotosByIdResponse;
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
import com.traap.traapapp.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import com.traap.traapapp.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;
import com.traap.traapapp.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import com.traap.traapapp.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;
import com.traap.traapapp.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import com.traap.traapapp.apiServices.model.verify.VerifyRequest;
import com.traap.traapapp.apiServices.model.verify.VerifyResponse;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletRequest;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;

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
            @Path("id") Integer categoryId
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
    @POST(Const.Get_Withdraw_Wallet)
    Single<Response<WebServiceClass<WithdrawWalletResponse>>> withdrawWallet(
            @Body WithdrawWalletRequest request);

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

    @GET(Const.GET_Transaction_List)
    Single<Response<WebServiceClass<ResponseTransaction>>> getTransactionList(

            @Query("amount__range") Integer amountRange,
            @Query("status") Boolean status,
            @Query("type_transaction_id") Integer typeTransactionId,
            @Query("create_date__range") String createDateRange
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


}
