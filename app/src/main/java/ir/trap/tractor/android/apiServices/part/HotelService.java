package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.GlobalResponse;
import ir.trap.tractor.android.apiServices.model.GlobalResponse2;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.trap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;

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

    public void hotelPayment(GdsHotelPaymentRequest request, OnServiceStatus<GlobalResponse2> listener)
    {
        start(getServiceGenerator().createService().doHotelPayment(request), listener);
    }
    public void sendHotelMessage(OnServiceStatus<GlobalResponse> listener, HotelSendMessageRequest request) {
        start(getServiceGenerator().createService().hotelSendMessage(request), listener);
    }
    public void hotelUserPass(OnServiceStatus<GetUserPassResponse> listener) {
        start(getServiceGenerator().createService().getHotelUserPass(), listener);
    }
}
