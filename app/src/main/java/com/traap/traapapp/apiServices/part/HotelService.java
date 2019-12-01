package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.GlobalResponse;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import com.traap.traapapp.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;

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
