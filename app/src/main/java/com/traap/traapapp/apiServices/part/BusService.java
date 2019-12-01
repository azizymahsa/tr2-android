package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.GlobalResponse3;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import com.traap.traapapp.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;

/**
 * Created by MahsaAzizi on 9/25/2019.
 */
public class BusService extends BasePart
{
    public BusService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void busBooking(OnServiceStatus<WebServiceClass<GlobalResponse3>> listener, RequestBusPayment request)
    {//prev factor
        start(getServiceGenerator().createService().busBooking(request), listener);
    }

    public void sendMessage(OnServiceStatus<WebServiceClass<GlobalResponse3>> listener, BusSendMessage request)
    {// Next factor GDS
        start(getServiceGenerator().createService().busSendMessage(request), listener);
    }

    public void userPass(OnServiceStatus<WebServiceClass<GetUserPassResponse>> listener)
    {//AccountInfo
        start(getServiceGenerator().createService().getBusUserPass(), listener);
    }
}
