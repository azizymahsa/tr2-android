package ir.trap.tractor.android.apiServices.api;

import io.reactivex.Single;
import ir.trap.tractor.android.apiServices.helper.Const;
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
import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroClient
{

    @POST(Const.MOBILE_CHARGE)
    Single<Response<MobileChargeResponse>> getMobileCharge(
            @Body MobileChargeRequest request
    );


    @POST(Const.GET_PACKAGE_RIGHTEL)
    Single<Response<GetRightelPackRespone>> getRightelPackage(
            @Body GetPackageMciRequest friendRequest
    );

    @POST(Const.GET_PACKAGE_MCI)
    Single<Response<GetPackageMciResponse>> getPackageMci(
            @Body GetPackageMciRequest request
    );

    @POST(Const.GET_PACKAGE_IRANCELL)
    Single<Response<GetPackageIrancellResponse>> getIrancellPackage(
            @Body GetPackageMciRequest request
    );

    @POST(Const.PACKAGE_BUY)
    Single<Response<PackageBuyResponse>> buySimcardPackage(
            @Body PackageBuyRequest mciPackageBuyRequest
    );

    @POST(Const.GetInfoPhoneBill)
    Single<Response<GetInfoPhoneBillResponse>> getInfoPhoneBill(
            @Body GetInfoPhoneBillRequest request
    );

    @POST(Const.BillPayment)
    Single<Response<BillPaymentResponse>> billPayment(
            @Body BillPaymentRequest request
    );

    @POST(Const.GetInfoBill)
    Single<Response<GetInfoBillResponse>> getInfoBill(
            @Body GetInfoBillRequest request
    );


    @POST(Const.GetHappyCardInfo)
    Single<Response<GetHappyCardInfoResponse>> getHappyCardInfo(
            @Body GetHappyCardInfoRequest request
    );

    @POST(Const.GetShetabCardInfo)
    Single<Response<ShetabCardInfoResponse>> getShetabCardInfo(
            @Body ShetabCardInfoRequest request
    );

    @POST(Const.DoTransferCard)
    Single<Response<DoTransferResponse>> doTransferCard(
            @Body DoTransferRequest request
    );

}
