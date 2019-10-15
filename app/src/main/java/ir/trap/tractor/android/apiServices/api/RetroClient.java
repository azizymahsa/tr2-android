package ir.trap.tractor.android.apiServices.api;

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
import ir.trap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.trap.tractor.android.apiServices.model.doTransferCard.response.DoTransferResponse;
import ir.trap.tractor.android.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import ir.trap.tractor.android.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;
import ir.trap.tractor.android.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroClient
{



    @POST(Const.GetMenu)
    Single<Response<WebServiceClass<GetMenuResponse>>> getMenu(
            @Body GetMenuRequest request
    );





    @POST(Const.MOBILE_CHARGE)
    Single<Response<WebServiceClass<MobileChargeResponse>>> getMobileCharge(
            @Body MobileChargeRequest request
    );


    @POST(Const.GET_PACKAGE_RIGHTEL)
    Single<Response<WebServiceClass<GetRightelPackRespone>>> getRightelPackage(
            @Body GetPackageMciRequest friendRequest
    );

    @POST(Const.GET_PACKAGE_MCI)
    Single<Response<WebServiceClass<GetPackageMciResponse>>> getPackageMci(
            @Body GetPackageMciRequest request
    );

    @POST(Const.GET_PACKAGE_IRANCELL)
    Single<Response<WebServiceClass<GetPackageIrancellResponse>>> getIrancellPackage(
            @Body GetPackageMciRequest request
    );

    @POST(Const.PACKAGE_BUY)
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


}
