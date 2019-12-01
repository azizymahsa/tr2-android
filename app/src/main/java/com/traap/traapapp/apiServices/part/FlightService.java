package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;

/**
 * Created by Javad.Abadi on 2/23/2019.
 */
public class FlightService extends BasePart
{
    public FlightService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void flightPayment(OnServiceStatus<WebServiceClass<GlobalResponse2>> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightPayment(request), listener);
    }

    public void sendMessage(OnServiceStatus<WebServiceClass<GlobalResponse2>> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightSendMessage(request), listener);
    }

    public void userPass(OnServiceStatus<WebServiceClass<GetUserPassResponse>> listener)
    {
        start(getServiceGenerator().createService().getFlightUserPass(), listener);
    }
}
