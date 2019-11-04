package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.GlobalResponse;
import ir.traap.tractor.android.apiServices.model.GlobalResponse2;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;

/**
 * Created by JavadAbadi on 2/23/2019.
 */
public class HotelService extends BasePart
{
    public HotelService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void hotelPayment(GdsHotelPaymentRequest request, OnServiceStatus<WebServiceClass<GlobalResponse2>> listener)
    {
        start(getServiceGenerator().createService().doHotelPayment(request), listener);
    }
    public void sendHotelMessage(OnServiceStatus<WebServiceClass<GlobalResponse>> listener, HotelSendMessageRequest request) {
        start(getServiceGenerator().createService().hotelSendMessage(request), listener);
    }
    public void hotelUserPass(OnServiceStatus<WebServiceClass<GetUserPassResponse>> listener) {
        start(getServiceGenerator().createService().getHotelUserPass(), listener);
    }
}
