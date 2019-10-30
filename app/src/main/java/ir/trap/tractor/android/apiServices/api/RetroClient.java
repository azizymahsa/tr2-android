package ir.trap.tractor.android.apiServices.api;

import java.util.ArrayList;

import io.reactivex.Single;
import ir.trap.tractor.android.apiServices.helper.Const;
import ir.trap.tractor.android.apiServices.model.GlobalResponse;
import ir.trap.tractor.android.apiServices.model.GlobalResponse2;
import ir.trap.tractor.android.apiServices.model.GlobalResponse3;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.billPayment.request.BillPaymentRequest;
import ir.trap.tractor.android.apiServices.model.billPayment.response.BillPaymentResponse;
import ir.trap.tractor.android.apiServices.model.buyPackage.request.PackageBuyRequest;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.trap.tractor.android.apiServices.model.card.Result;
import ir.trap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import ir.trap.tractor.android.apiServices.model.card.editCard.request.EditCardRequest;
import ir.trap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import ir.trap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.trap.tractor.android.apiServices.model.doTransferCard.response.DoTransferResponse;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import ir.trap.tractor.android.apiServices.model.getBankList.response.BankListResponse;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.trap.tractor.android.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import ir.trap.tractor.android.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.trap.tractor.android.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.trap.tractor.android.apiServices.model.getMyBill.GetMyBillResponse;
import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;
import ir.trap.tractor.android.apiServices.model.login.LoginRequest;
import ir.trap.tractor.android.apiServices.model.login.LoginResponse;
import ir.trap.tractor.android.apiServices.model.match.ResponseMatch;
import ir.trap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.trap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import ir.trap.tractor.android.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import ir.trap.tractor.android.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;
import ir.trap.tractor.android.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import ir.trap.tractor.android.apiServices.model.verify.VerifyRequest;
import ir.trap.tractor.android.apiServices.model.verify.VerifyResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetroClient
{

    @POST(Const.Login)
    Single<Response<WebServiceClass<LoginResponse>>> login(
            @Body LoginRequest request
    );

    @GET(Const.GetMyBills)
    Single<Response<WebServiceClass<GetMyBillResponse>>> getMyBills();

    @GET(Const.BANK_LIST)
    Single<Response<WebServiceClass<BankListResponse>>> getBankList();

    @POST(Const.GetAllBoxes)
    Single<Response<WebServiceClass<GetAllBoxesResponse>>> getAllBoxes(
            @Body GetAllBoxesRequest request
            );

    @POST(Const.GetBillCodePayCode)
    Single<Response<WebServiceClass<GetBillCodePayCodeResponse>>> getBillCodePayCode(
            @Body GetBillCodePayCodeRequest request
    );

    @POST(Const.Verify)
    Single<Response<WebServiceClass<VerifyResponse>>> verify(
            @Body VerifyRequest request
    );

    @POST(Const.BUY_MOBILE_CHARGE)
    Single<Response<WebServiceClass<MobileChargeResponse>>> getMobileCharge(
            @Body MobileChargeRequest request);

    @POST(Const.GET_PACKAGE_IRANCELL)
    Single<Response<WebServiceClass<GetPackageIrancellResponse>>> getIrancellPackage(
            @Body GetPackageMciRequest request
    );

    @POST(Const.GetMenu)
    Single<Response<WebServiceClass<GetMenuResponse>>> getMenu(
            @Body GetMenuRequest request
    );

    @POST(Const.GetMenuAll)
    Single<Response<WebServiceClass<ArrayList<GetMenuItemResponse>>>> getMenuAll(
            @Body GetMenuRequest request
    );

    @GET(Const.GetCardList)
    Single<Response<WebServiceClass<GetCardListResponse>>> getCardList();

    @POST(Const.AddCard)
    Single<Response<WebServiceClass<Result>>> addCard(
            @Body AddCardRequest request);

    @DELETE(Const.DeleteCard + "{id}/")
    Single<Response<WebServiceClass<Object>>> deleteCard(
            @Path("id") Integer cardId);

    @PUT(Const.EditCard + "{id}/")
    Single<Response<WebServiceClass<Result>>> editCard(
            @Path("id") Integer cardId,
            @Body EditCardRequest request);


/*    @POST(Const.MOBILE_CHARGE)
    Single<Response<WebServiceClass<MobileChargeResponse>>> getMobileCharge(
            @Body MobileChargeRequest request
    );*/


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
            @Body GdsHotelPaymentRequest gdsHotelPaymentRequest);

    @POST(Const.DECRYPTQRCODE)
    Single<Response<WebServiceClass<DecryptQrResponse>>> decryptQr(
            @Body DecryptQrRequest request);

    @POST(Const.PAYMENT_PRINT_pOS)
    Single<Response<WebServiceClass<PaymentPrintPosResponse>>> getPayment(
            @Body PaymentPrintPosRequest request);

    @GET(Const.GetMatch)
    Single<Response<WebServiceClass<ResponseMatch>>> getMatch();

    @GET(Const.GetHistory)
    Single<Response<WebServiceClass<ResponseHistory>>> getHistory();

}
