/*
package ir.trap.tractor.android.apiServices.part;


import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.GlobalResponse;
import ir.trap.tractor.android.apiServices.model.GlobalResponse2;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;

public class MerchantService
        extends BasePart
{
    public MerchantService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void decryptQrService(OnServiceStatus<WebServiceClass<DecryptQrResponse>> listener, DecryptQrRequest req)
    {
        start(getServiceGenerator().createService().decryptQr(req), listener);
    }

}
*/
package ir.trap.tractor.android.apiServices.part;


import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.trap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.trap.tractor.android.apiServices.model.match.ResponseMatch;
import ir.trap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.trap.tractor.android.ui.fragments.paymentWithoutCard.PaymentFragment;


public class TicketService extends BasePart
{

    public TicketService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }



    public void getMatch(OnServiceStatus<WebServiceClass<ResponseMatch>> listener)
    {
        start(getServiceGenerator().createService().getMatch(), listener);
    }
}